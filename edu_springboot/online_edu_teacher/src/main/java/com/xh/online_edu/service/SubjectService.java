package com.xh.online_edu.service;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.*;
import com.xh.online_edu.model.resData.SubjectBaseData;
//import com.xh.online_edu_teacher.model.resData.TaskData;
import com.xh.online_edu.pojo.po.EduChapter;
//import com.xh.online_edu_teacher.pojo.po.EduChapterItem;
import com.xh.online_edu.pojo.po.EduChapterItem;
import com.xh.online_edu.pojo.po.EduResource;
import com.xh.online_edu.pojo.vo.TchItme;
import com.xh.online_edu.pojo.vo.TchSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubjectService {
    List<TchSubject> getCourseList(Long uid) throws StatusFailException;

    SubjectBaseData getSubject(Long subjectId, Long uid)throws StatusFailException;

//    TaskData getTask(Long courseId);

    EduChapter addChapter(Long subjectId, NewChapterDto dto) throws StatusFailException;

    boolean delchapter(Long subjectId, Long chapterId, BaseDto dto) throws StatusFailException;

    List<EduChapterItem> getChapterItem(Long chapterId, Long uid);

    boolean releaseTask(Long courseId, ReleaseItemDto itemDto);

    boolean delTask(Long courseId, String type, Long id, Long uid) throws StatusFailException;

    boolean updateChapter(Long chapterId, UpChapterDto dto)throws StatusFailException;

    List<TchItme> getItemList(Long subjectId, Long uid)throws StatusFailException;

    boolean resetItemChapter(Long itemId, Long newChapterId) throws StatusFailException;

    boolean resetItemPublic(Long itemId, Integer isPublic) throws StatusFailException;

    EduChapterItem newItem(String itemTitle, Long chapterId, Long teacherId, int isPublic) throws StatusFailException;

    Long uploadResource(MultipartFile file,Long itemId) throws StatusFailException;

    Result uploadStatus(Long resourceId);

    EduResource uploadMarkdown(Long itemId, MarkDownDto dto)throws StatusFailException;

    Object delResource(Long resourceId, BaseDto dto) throws StatusFailException;
}
