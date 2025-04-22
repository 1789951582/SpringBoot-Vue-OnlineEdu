package com.xh.online_edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedList;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT * FROM aichat_msg WHERE cid=#{cid} ORDER BY create_time;")
    LinkedList<Message> selectByCid(@Param("cid") long cid);
}
