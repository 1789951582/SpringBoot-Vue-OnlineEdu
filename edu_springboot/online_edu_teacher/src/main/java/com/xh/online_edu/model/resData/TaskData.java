package com.xh.online_edu.model.resData;

//@Data
//@JsonTypeName("SharedTask") // 统一类型标识
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@type"
//)
//public class TaskData implements Serializable {
//    private Long courseId;
////    private String courseName;
//    private List<TaskChapterRes> Chapters=new ArrayList<>();
//
//    @Data
//    @JsonTypeName("SharedChapter")
//    public static class TaskChapterRes implements Serializable{
//        private Long chapterId;
//        private Integer chapterIdx;
//        private String chapterTitle;
//        private List<TaskItemRes> items=new ArrayList<>();
//    }
//
//    @Data
//    @JsonTypeName("SharedItem")
//    public static class TaskItemRes implements Serializable{
//        private Long itemId;
//        private Integer itemIdx;
//        private String itemTitle;
//
//    }
//
//    public static TaskData TaskResBuilder(List<StuTask> stuTaskVoList) {
//        TaskData taskRes = new TaskData();
//        if (CollectionUtils.isEmpty(stuTaskVoList)) {
//            return taskRes;
//        }
//        taskRes.setCourseId(stuTaskVoList.get(0).getCourseId());
//
//        Map<Integer, TaskChapterRes> chapterMap = new LinkedHashMap<>();
//
//        stuTaskVoList.forEach(vo -> {
//            TaskChapterRes chapter = chapterMap.get(vo.getChapterIdx());
//            if (chapter == null) {
//                chapter = new TaskChapterRes();
//                chapter.setChapterId(vo.getChapterId());
//                chapter.setChapterIdx(vo.getChapterIdx());
//                chapter.setChapterTitle(vo.getChapterTitle());
//                chapterMap.put(vo.getChapterIdx(), chapter);
//            }
//            TaskItemRes item = new TaskItemRes();
//            item.setItemId(vo.getItemId());
//            item.setItemIdx(vo.getItemIdx());
//            item.setItemTitle(vo.getItemTitle());
//            chapter.getItems().add(item);
//        });
//
//        taskRes.setChapters(new ArrayList<>(chapterMap.values()));
//        return taskRes;
//    }
//
//}
