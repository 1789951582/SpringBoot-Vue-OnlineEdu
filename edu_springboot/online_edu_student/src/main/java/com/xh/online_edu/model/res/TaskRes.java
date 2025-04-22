package com.xh.online_edu.model.res;

import cloud.tianai.captcha.common.util.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.xh.online_edu.pojo.vo.StuTaskVo;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
//@JsonTypeName("SharedTask") // 统一类型标识
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public class TaskRes implements Serializable {
    private Long courseId;
//    private String courseName;
    private List<TaskChapterRes> Chapters=new ArrayList<>();

    @Data
//    @JsonTypeName("SharedChapter")
    public static class TaskChapterRes implements Serializable{
        private Long chapterId;
        private Integer chapterIdx;
        private String chapterTitle;
        private List<TaskItemRes> items=new ArrayList<>();
    }

    @Data
//    @JsonTypeName("SharedItem")
    public static class TaskItemRes implements Serializable{
        private Long itemId;
        private Integer itemIdx;
        private String itemTitle;

    }

    public static TaskRes TaskResBuilder(List<StuTaskVo> stuTaskVoList) {
        TaskRes taskRes = new TaskRes();
        if (CollectionUtils.isEmpty(stuTaskVoList)) {
            return taskRes;
        }
        taskRes.setCourseId(stuTaskVoList.get(0).getCourseId());

        Map<Integer, TaskChapterRes> chapterMap = new LinkedHashMap<>();

        stuTaskVoList.forEach(vo -> {
            TaskChapterRes chapter = chapterMap.get(vo.getChapterIdx());
            if (chapter == null) {
                chapter = new TaskChapterRes();
                chapter.setChapterId(vo.getChapterId());
                chapter.setChapterIdx(vo.getChapterIdx());
                chapter.setChapterTitle(vo.getChapterTitle());
                chapterMap.put(vo.getChapterIdx(), chapter);
            }
            TaskItemRes item = new TaskItemRes();
            item.setItemId(vo.getItemId());
            item.setItemIdx(vo.getItemIdx());
            item.setItemTitle(vo.getItemTitle());
            chapter.getItems().add(item);
        });

        taskRes.setChapters(new ArrayList<>(chapterMap.values()));
        return taskRes;
    }

//    public static TaskRes TaskResBuilder(List<StuTaskVo>  stuTaskVoList){
//        TaskRes taskRes=new TaskRes();
//        if (CollectionUtils.isEmpty(stuTaskVoList)){
//            return taskRes;
//        }
//        taskRes.setCourseId(stuTaskVoList.get(0).getCourseId());
//
//        Map<Integer, TaskChapterRes> chapterMap = new LinkedHashMap<>();
//
//        stuTaskVoList.forEach(vo -> {
//            chapterMap.computeIfAbsent(vo.getChapterIdx(), k ->
//                    new TaskChapterRes() {{
//                        setChapterId(vo.getChapterId());
//                        setChapterIdx(vo.getChapterIdx());
//                        setChapterTitle(vo.getChapterTitle());
//                    }}
//            ).getItems().add(
//                    new TaskItemRes() {{
//                        setItemId(vo.getItemId());
//                        setItemIdx(vo.getItemIdx());
//                        setItemTitle(vo.getItemTitle());
//                    }}
//            );
//        });
//
//        taskRes.setChapters(new ArrayList<>(chapterMap.values()));
//
//        return taskRes;
//    }
}
