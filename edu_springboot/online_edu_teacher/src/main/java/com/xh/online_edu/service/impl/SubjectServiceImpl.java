package com.xh.online_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.manager.UploadManager;
import com.xh.online_edu.mapper.po.*;
import com.xh.online_edu.mapper.vo.StuTaskMapper;
import com.xh.online_edu.mapper.vo.TchCourseMapper;
import com.xh.online_edu.mapper.vo.TchItemMapper;
import com.xh.online_edu.mapper.vo.TchSubjectMapper;
import com.xh.online_edu.model.dto.*;
import com.xh.online_edu.model.resData.SubjectBaseData;
//import com.xh.online_edu_teacher.model.resData.TaskData;
import com.xh.online_edu.pojo.po.*;
import com.xh.online_edu.pojo.vo.TchCourse;
import com.xh.online_edu.pojo.vo.TchItme;
import com.xh.online_edu.pojo.vo.TchSubject;
import com.xh.online_edu.service.SubjectService;
import com.xh.online_edu.utils.RedisUtils;
import com.xh.online_edu.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    TchSubjectMapper subjectMapper;
    @Autowired
    TchCourseMapper courseMapper;
    @Autowired
    EduChapterMapper chapterMapper;
    @Autowired
    EduTestMapper testMapper;
    @Autowired
    StuTaskMapper taskMapper;
    @Autowired
    EduChapterItemMapper chapterItemMapper;
    @Autowired
    EduTaskMapper eduTaskMapper;
    @Autowired
    TchItemMapper tchItemMapper;
    @Autowired
    UploadManager uploadManager;
    @Autowired
    EduMarkdownMapper markdownMapper;
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    FileUtils saveFileUtils;


    @Override
    public List<TchSubject> getCourseList(Long uid) {
        QueryWrapper<TchSubject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("t_uid",uid);
        return subjectMapper.selectList(queryWrapper);
    }

    @Override
    public SubjectBaseData getSubject(Long subjectId, Long uid) throws StatusFailException {
        QueryWrapper<TchSubject> subjectQueryWrapper=new QueryWrapper<>();
        subjectQueryWrapper
                .eq("t_uid",uid)
                .eq("subject_id",subjectId);
        TchSubject tchSubject=subjectMapper.selectOne(subjectQueryWrapper);
        SubjectBaseData baseData=new SubjectBaseData();
        baseData.setSubjectId(tchSubject.getSubjectId());
        baseData.setSubjectTitle(tchSubject.getSubjectTitle());
        QueryWrapper<TchCourse> courseQueryWrapper=new QueryWrapper<>();
        QueryWrapper<EduChapter> chapterQueryWrapper=new QueryWrapper<>();
        QueryWrapper<EduTest> testQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper
                .eq("subject_id",subjectId)
                .eq("course_status",1);
        chapterQueryWrapper.eq("subject_id",subjectId);
        testQueryWrapper.eq("subject_id",subjectId);
        if (tchSubject.getDirectorId()!=subjectId){
            courseQueryWrapper.eq("teacher_id",uid);
            chapterQueryWrapper
                    .and(wrapper -> wrapper
                            .eq("is_public",1)
                            .or()
                            .eq("teacher_id",uid)
                    );
            testQueryWrapper
                    .and(wrapper->wrapper
                            .eq("is_public",1)
                            .or()
                            .eq("teacher_id",uid)
                    );
        }
        baseData.setCourseList(courseMapper.selectList(courseQueryWrapper));
        baseData.setChapterList(chapterMapper.selectList(chapterQueryWrapper));
        baseData.setTestList(testMapper.selectList(testQueryWrapper));
        return baseData;
    }

//    @Override
//    public TaskData getTask(Long courseId){
//        Object tempOj=redisUtils.get("courseTask"+courseId);
//        if (tempOj!=null){
//            return (TaskData) tempOj;
//        }
//        List<StuTask> stuTaskVoList =taskMapper.selectTaskListByCourseId(courseId);
//        TaskData data= TaskData.TaskResBuilder(stuTaskVoList);
//        redisUtils.set("courseTask"+courseId,data);
//        return data;
//    }

    @Override
    public EduChapter addChapter(Long subjectId, NewChapterDto dto) throws StatusFailException{
        EduChapter chapter=new EduChapter();
        chapter.setChapterTitle(dto.getChapterTitle());
        chapter.setSubjectId(subjectId);
        chapter.setTeacherId(dto.getUid());
        chapter.setIsPublic(dto.getIsPublic());
        int isOk=chapterMapper.insert(chapter);
        if (isOk<=0){
            throw new StatusFailException("新增失败");
        }
        return chapter;
    }

    @Override
    public boolean delchapter(Long subjectId, Long chapterId, BaseDto dto) throws StatusFailException {
        QueryWrapper<EduChapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper
                .eq("chapter_id",chapterId)
                .eq("subject_id",subjectId)
                .eq("teacher_id",dto.getUid());
        int isOk=chapterMapper.delete(chapterQueryWrapper);
        if (isOk<=0){
            throw new StatusFailException("删除失败");
        }
        return true;
    }

    @Override
    public List<EduChapterItem> getChapterItem(Long chapterId , Long uid) {
        QueryWrapper<EduChapterItem> chapterItemQueryWrapper=new QueryWrapper<>();
        chapterItemQueryWrapper
                .eq("chapter_id",chapterId)
                .and(wrapper->wrapper
                        .eq("is_public",1)
                        .or()
                        .eq("teacher_id",uid)
                );
        return  chapterItemMapper.selectList(chapterItemQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseTask(Long courseId, ReleaseItemDto itemDto) {
        Long chapterId=itemDto.getChapterId();
        Map<String,Integer> map=eduTaskMapper.selectItemIdx(courseId,chapterId);
        List<EduTask> taskList;
        if (map==null || map.isEmpty()){
//            首次发布章节
            Integer chapterIdx=eduTaskMapper.selectChapterIdx(courseId);
             taskList=itemDto.getTaskList(courseId,(chapterIdx==null ? 0:chapterIdx)+1,0);
        }else {
            taskList=itemDto.getTaskList(courseId,map.get("chapter_idx"),map.get("max_item_idx")+1);
        }
        eduTaskMapper.batchUpsert(taskList);
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        redisUtils.del("courseTask"+courseId);
                    }
                }
        );
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delTask(Long courseId, String type, Long id, Long uid) throws StatusFailException {
        QueryWrapper<EduTask> taskQueryWrapper=new QueryWrapper<>();
        taskQueryWrapper
                .eq("course_id",courseId);
        if (type.equals("chapter")){
            taskQueryWrapper.eq("chapter_id",id);
        }else{
            taskQueryWrapper.eq("item_id",id);
        }
        int isok= eduTaskMapper.delete(taskQueryWrapper);
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        redisUtils.del("courseTask"+courseId);
                    }
                }
        );
        if (isok<0){
            throw new StatusFailException("删除失败");
        }
        return true;
    }

    @Override
    public boolean updateChapter(Long chapterId, UpChapterDto dto) throws StatusFailException {
        int isOk= chapterMapper.myUpdate(chapterId,dto.getChapterTitle(),dto.getIsPublic());
        if (isOk<=0){
            throw new StatusFailException("更新失败");
        }
        return true;
    }

    @Override
    public List<TchItme> getItemList(Long subjectId, Long uid) throws StatusFailException {
        QueryWrapper<TchSubject> subjectQueryWrapper=new QueryWrapper<>();
        subjectQueryWrapper
                .eq("t_uid",uid)
                .eq("subject_id",subjectId);
        TchSubject tchSubject=subjectMapper.selectOne(subjectQueryWrapper);
        QueryWrapper<TchItme> itmeQueryWrapper=new QueryWrapper<>();
        itmeQueryWrapper
                .eq("subject_id",subjectId);
        if (tchSubject.getDirectorId()!=uid){
            itmeQueryWrapper.and(
                    wrapper->wrapper.eq("is_public",1).or().eq("teacher_id",uid)
            );
        }
        return tchItemMapper.selectList(itmeQueryWrapper);
    }

    @Override
    public boolean resetItemChapter(Long itemId, Long newChapterId) throws StatusFailException {
        int isOk=chapterItemMapper.resetItemChapter(itemId,newChapterId);
        if (isOk<0){
            throw new StatusFailException("修改失败");
        }
        return true;
    }
    @Override
    public boolean resetItemPublic(Long itemId, Integer isPublic) throws StatusFailException {
        int isOk=chapterItemMapper.resetItemPublic(itemId,isPublic);
        if (isOk<0){
            throw new StatusFailException("修改失败");
        }
        return true;
    }

    @Override
    public EduChapterItem newItem(String itemTitle,Long chapterId,Long teacherId,int isPublic) throws StatusFailException {
        EduChapterItem item=new EduChapterItem(itemTitle,chapterId,teacherId,isPublic);
        int isOk = chapterItemMapper.insert(item);
        if (isOk<0){
            throw new StatusFailException("新建失败");
        }
        return item;
    }

    @Override
    public Long uploadResource(MultipartFile raw,Long itemId) throws StatusFailException {

        String originalFilename = raw.getOriginalFilename();
        if (originalFilename == null || originalFilename.indexOf('.') == -1) {
            throw new StatusFailException("无效的文件名格式");
        }
        // 解析文件名和扩展名
        int dotIndex = originalFilename.lastIndexOf('.');
//        获取文件名部分（不包括扩展名）
        String fileName = originalFilename.substring(0, dotIndex);
//        获取文件扩展名
        String fileExt = originalFilename.substring(dotIndex).toLowerCase(Locale.ROOT);
//        生成文件唯一id
        Long resourceId=YitIdHelper.nextId();
        //存储文件
        File tempFile=saveFileUtils.saveFile(raw,resourceId+fileExt);

        EduResource resource=new EduResource();
        resource.setResourceId(resourceId);
        resource.setResourceTitle(fileName);
        resource.setItemId(itemId);

        uploadManager.UploadHandle(tempFile,resource,fileExt);
        return resourceId;
    }

    @Override
    public Result uploadStatus(Long resourceId) {
        Result tempObj= redisUtils.get("saveIsOk:"+resourceId,Result.class);
        if (tempObj==null){
            return Result.NotFound("热数据未找到,也许还在运行?");
        }
        return tempObj;
    }

    @Override
    public EduResource uploadMarkdown(Long itemId, MarkDownDto dto) throws StatusFailException {
        EduMarkdown markdown=new EduMarkdown();
        markdown.setMarkdownContent(dto.getMarkdownText());
        int isOk=markdownMapper.insert(markdown);
        if (isOk<0){
            throw new StatusFailException("插入数据库失败");
        }
        EduResource resource=new EduResource();
        resource.setResourceId(YitIdHelper.nextId());
        resource.setResourceUrl(markdown.getMarkdownId().toString());
        resource.setTypeId(3);
        resource.setItemId(itemId);
        isOk=resourceMapper.insert(resource);
        if (isOk<0){
            throw new StatusFailException("插入数据库失败");
        }
        return resource;
    }

    @Override
    public Object delResource(Long resourceId, BaseDto dto) throws StatusFailException {
        if (resourceId == null) {
            throw new StatusFailException("文件ID不能为空");
        }
        saveFileUtils.deleteFile(resourceId);
        int isOk=resourceMapper.deleteById(resourceId);
        if (isOk<0){
            throw new StatusFailException("删除失败");
        }
        return true;
    }
}
