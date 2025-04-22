package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.model.dto.UserInformationDto;
import com.xh.online_edu.pojo.po.StuInfoPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StuInfoPoMapper extends BaseMapper<StuInfoPo> {
    @Select("SELECT * FROM stu_info " +
            "WHERE s_nickname=#{account} " +
            "OR s_uid=#{account} " +
            "OR s_email=#{account}" )
    StuInfoPo getStu(String account);

    @Update("UPDATE stu_info " +
            "SET " +
                "s_realname=#{srealname}, " +
                "s_phone=#{sphone}, " +
                "s_school=#{sschool}, " +
                "s_course=#{scourse}, " +
                "s_num=#{snum}, " +
                "s_sex=#{ssex}, " +
                "s_age=#{sage} " +
            "WHERE s_uid=#{uid}")
    int modifyInformation(UserInformationDto dto);

    @Select("SELECT s_email FROM stu_info WHERE s_uid=#{uid}")
    String getUserEmail(Long uid);

    @Update("UPDATE stu_info SET s_email=#{email} WHERE s_uid=#{uid}")
    int updateUserEmail(Long uid,String email);

    @Update("UPDATE stu_info SET s_pwd=#{pwd} WHERE s_uid=#{uid}")
    int updateUserPwd(Long uid,String pwd);

//    弃用
//    @Select("SELECT ${itemName} " +
//            "FROM stu_info " +
//            "WHERE ${itemName}=#{value}" )
//    StuInfoPo eq(@Param("itemName") String itemName,@Param("value") String value);
}
