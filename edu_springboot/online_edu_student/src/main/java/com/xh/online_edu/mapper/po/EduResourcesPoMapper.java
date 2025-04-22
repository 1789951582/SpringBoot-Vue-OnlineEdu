package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.po.EduResourcesPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EduResourcesPoMapper extends BaseMapper<EduResourcesPo> {
//    @Select("SELECT item_id,item_title " +
//            "FROM edu_item " +
//            "WHERE item_id=#{itemId}")
//    @Results({
//            @Result(property = "itemId",column = "item_id"),
//            @Result(property = "itemTitle",column = "item_title"),
//            @Result(property = "resourcePoList",column = "item_id",many = @Many(select = "getResourceByItemId"))
//    })
//    ResourceRes getResourceResByItemId(Long itemId);
//
    @Select("SELECT resource_id,resource_url,resource_title,type_id " +
            "FROM edu_resources " +
            "WHERE item_id=#{itemId}")
    List<EduResourcesPo> getResourceByItemId(Long itemId);
}
