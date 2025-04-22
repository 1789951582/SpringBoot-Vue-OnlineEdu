package com.xh.online_edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.PageReq;
import com.xh.online_edu.model.res.StuTestRes;
import com.xh.online_edu.model.res.TaskRes;
import com.xh.online_edu.pojo.po.EduMarkdownPo;
import com.xh.online_edu.pojo.po.EduResourcesPo;
import com.xh.online_edu.pojo.vo.StuCourseVo;

import java.util.List;

public interface CourseServer {
    TaskRes getTask(Long courseId)throws StatusFailException;

    EduMarkdownPo getMarkdown(Long markdownId)throws StatusFailException;

    List<EduResourcesPo> getResources(Long itemId)throws StatusFailException;

    IPage<StuTestRes> getTest(PageReq pageReq, Long courseId)throws StatusFailException;


    StuCourseVo getCourseInfo(Long courseId, Long uid) throws StatusFailException;
}
