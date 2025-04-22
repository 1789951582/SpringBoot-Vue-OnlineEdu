package com.xh.online_edu.service;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.res.PageRes;
import com.xh.online_edu.pojo.vo.StuCourseVo;

import java.util.List;

public interface HomeService {

    PageRes<StuCourseVo> getCourseList(Long uid, int pageNum, int pageSize,short status) throws StatusFailException;
}
