package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("edu_task")
public class EduTask implements Serializable { // 必须实现Serializable

    public EduTask(Long courseId,Long chapterId,int chapterIdx,Long itemId,int itemIdx){
        this.id.setCourseId(courseId);
        this.chapterId=chapterId;
        this.chapterIdx=chapterIdx;
        this.id.setItemId(itemId);
        this.itemIdx=itemIdx;
    }

    // 复合主键对象
    @TableId(type = IdType.INPUT)
    private TaskKey id=new TaskKey();

    // 其他字段
    private Long chapterId;
    private int chapterIdx;
    private int itemIdx;

    // 快速访问方法（可选）
    public Long getCourseId() {
        return id.getCourseId();
    }

    public Long getItemId() {
        return id.getItemId();
    }

    // 静态复合主键类
    @Data
    @NoArgsConstructor
    private static class TaskKey implements Serializable {
        @TableField("course_id")
        private Long courseId;

        @TableField("item_id")
        private Long itemId;
    }
}
