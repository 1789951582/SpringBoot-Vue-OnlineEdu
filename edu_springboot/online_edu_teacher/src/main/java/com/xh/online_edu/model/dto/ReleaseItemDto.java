package com.xh.online_edu.model.dto;

import com.xh.online_edu.pojo.po.EduChapterItem;
import com.xh.online_edu.pojo.po.EduTask;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReleaseItemDto extends BaseDto{
    private List<Item> itemList;

    public List<EduTask> getTaskList(Long courseId,int chapterIdx,int itemIdx){
        List<EduTask> taskList=new ArrayList<>();
        for (Item item:this.itemList){
            EduTask task=new EduTask(courseId,item.getChapterId(),chapterIdx, item.getItemId(), itemIdx+item.getIdx());
            taskList.add(task);
        }
        return taskList;
    }
    public Long getChapterId(){
        return this.itemList.get(0).getChapterId();
    }

    @Data
    private static class Item extends EduChapterItem{
        private int Idx;
    }
}
