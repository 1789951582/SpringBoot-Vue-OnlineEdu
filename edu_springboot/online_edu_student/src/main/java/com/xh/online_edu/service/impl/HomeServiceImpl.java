package com.xh.online_edu.service.impl;

import com.xh.online_edu.mapper.vo.StuCouresVoMapper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.res.PageRes;
import com.xh.online_edu.pojo.vo.StuCourseVo;
import com.xh.online_edu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    StuCouresVoMapper stuCouresViewMapper;

    @Override
    public PageRes<StuCourseVo> getCourseList(Long uid, int pageNum, int pageSize,short status) throws StatusFailException {
        int offset =(pageNum-1)*pageSize;
        List<StuCourseVo> records=stuCouresViewMapper.selectByPage(uid,status,offset,pageSize);
        int total = stuCouresViewMapper.countByStudent(uid,status);
        return new PageRes<StuCourseVo>(records,total,pageNum,pageSize);
    }
}
