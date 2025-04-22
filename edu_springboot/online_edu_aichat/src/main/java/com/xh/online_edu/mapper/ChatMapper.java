package com.xh.online_edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.model.res.ChatRes;
import com.xh.online_edu.pojo.Chat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMapper extends BaseMapper<Chat> {

//    @Select("SELECT * FROM aichat_chat WHERE cid=#{cid} AND uid=#{uid};")
//    @Results({
//            @Result(property = "cid",column = "cid"),
//            @Result(property = "uid",column = "uid"),
//            @Result(property = "chatName",column = "chat_name"),
//            @Result(property = "createTime",column = "create_time"),
//            @Result(property = "msgList",column = "cid",many = @Many(select = "com.xh.online_edu.mapper.MessageMapper.selectByCid"))
//    })
//    ChatRes selectByCidAndUid(@Param("cid") long cid, @Param("uid") Long uid);

    @Update("UPDATE aichat_chat SET chat_name = #{newName} WHERE cid=#{cid} AND uid = #{uid};")
    int rename(Long cid, Long uid, String newName);

    @Select("SELECT * FROM aichat_chat WHERE uid=#{uid} ORDER BY create_time;")
    List<Chat> selectByUid(Long uid);

    @Delete("DELETE FROM aichat_chat WHERE cid = #{cid} AND uid=#{uid};")
    int deleteByCIdAndUid(@Param("cid") Long cid,@Param("uid") Long uid);



    @Select("SELECT link.cid,chat.* " +
            "FROM edu_question_link_aichat link " +
            "JOIN aichat_chat chat " +
            "ON link.cid=chat.cid " +
            "WHERE link.question_id=#{qid} AND link.uid=#{uid}")
    Chat getQuestionAichat(Long qid,Long uid);

    @Insert("Insert INTO edu_question_link_aichat (question_id,uid,cid) VALUE (#{qid},#{uid},#{cid})")
    int insertLinkChat(Long qid,Long uid,Long cid);
}
