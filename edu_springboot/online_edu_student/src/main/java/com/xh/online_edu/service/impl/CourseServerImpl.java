package com.xh.online_edu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.EduMarkdownPoMapper;
import com.xh.online_edu.mapper.po.EduResourcesPoMapper;
import com.xh.online_edu.mapper.vo.StuCouresVoMapper;
import com.xh.online_edu.mapper.vo.StuTaskVoMapper;
import com.xh.online_edu.mapper.vo.StuTestVoMapper;
import com.xh.online_edu.model.dto.PageReq;
import com.xh.online_edu.model.res.StuTestRes;
import com.xh.online_edu.model.res.TaskRes;
import com.xh.online_edu.pojo.po.EduMarkdownPo;
import com.xh.online_edu.pojo.po.EduResourcesPo;
import com.xh.online_edu.pojo.vo.StuCourseVo;
import com.xh.online_edu.pojo.vo.StuTaskVo;
import com.xh.online_edu.service.CourseServer;
import com.xh.online_edu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServerImpl implements CourseServer {
    @Autowired
    StuTaskVoMapper stuTaskVoMapper;
    @Autowired
    EduMarkdownPoMapper markdownMapper;
    @Autowired
    EduResourcesPoMapper resourcesPoMapper;
    @Autowired
    StuTestVoMapper stuTestVoMapper;
    @Autowired
    StuCouresVoMapper stuCouresVoMapper;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public StuCourseVo getCourseInfo(Long uid,Long courseId) throws StatusFailException{
        StuCourseVo courseVo=stuCouresVoMapper.getCourseInfo(uid,courseId);
        if (courseVo!=null){
            return courseVo;
        }
        throw new StatusFailException("你没有该门课程的权限");
    }

    @Override
    public TaskRes getTask(Long courseId) throws StatusFailException {
        TaskRes tempOj=redisUtils.get("courseTask"+courseId,TaskRes.class);
        if (tempOj!=null){
            return tempOj;
        }
        List<StuTaskVo> stuTaskVoList =stuTaskVoMapper.selectTaskListByCourseId(courseId);
        TaskRes taskRes=TaskRes.TaskResBuilder(stuTaskVoList);
        redisUtils.set("courseTask"+courseId,taskRes);
        return taskRes;
    }

    @Override
    public EduMarkdownPo getMarkdown(Long markdownId) throws StatusFailException {
        return markdownMapper.selectById(markdownId);
    }

    @Override
    public List<EduResourcesPo> getResources(Long itemId) throws StatusFailException {
        return resourcesPoMapper.getResourceByItemId(itemId);
    }

    @Override
    public IPage<StuTestRes> getTest(PageReq pageReq, Long courseId) throws StatusFailException {
        Page<StuTestRes> page=new Page<>(pageReq.getPageNum(),pageReq.getPageSize());
        return stuTestVoMapper.selectTestWithCommitStatus(page,pageReq.getUid(),courseId);
    }
}
