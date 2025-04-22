package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.*;
import com.xh.online_edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/teacher/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("get_subject_list")
    public Result getSubjectList(@RequestBody PageDto baseData){
        try{
            return Result.successResponse(subjectService.getCourseList(baseData.getUid()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("get_subject/{subjectId}")
    public Result getSubject(@PathVariable Long subjectId,@RequestBody BaseDto baseDto){
        try{
            return Result.successResponse(subjectService.getSubject(subjectId,baseDto.getUid()));
        }catch (StatusFailException e){
            return Result.FailResponse();
        }
    }

//    @PostMapping("get_task/{courseId}")
//    public Result getTask(@PathVariable Long courseId){
//        return Result.successResponse(subjectService.getTask(courseId));
//    }

    @PostMapping("{subjectId}/add_chapter")
    public Result addChapter(@PathVariable Long subjectId ,@RequestBody NewChapterDto dto){
        try{
            return Result.successResponse(subjectService.addChapter(subjectId,dto));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("{subjectId}/del_chapter/{chapterId}")
    public Result delchapter(@PathVariable Long subjectId,@PathVariable Long chapterId,@RequestBody BaseDto dto){
        try{
            return Result.successResponse(subjectService.delchapter(subjectId,chapterId,dto));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("get_chapter_item/{chapterId}")
    public Result getChapterItem(@PathVariable Long chapterId,@RequestBody BaseDto dto){
        return Result.successResponse(subjectService.getChapterItem(chapterId,dto.getUid()));
    }

    @PostMapping("release_task/{courseId}")
    public Result releaseTask(@PathVariable Long courseId, @RequestBody ReleaseItemDto itemDto){
        return Result.successResponse(subjectService.releaseTask(courseId,itemDto));
    }

    @PostMapping("{courseId}/del_task/{type}/{id}")
    public Result delTask(@PathVariable Long courseId,@PathVariable String type,@PathVariable Long id,@RequestBody BaseDto baseDto){
        try {
            return Result.successResponse(subjectService.delTask(courseId,type,id,baseDto.getUid()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("update_chapter/{chapterId}")
    public Result updateChapter(@PathVariable Long chapterId, @RequestBody UpChapterDto dto){
        try {
            return Result.successResponse(subjectService.updateChapter(chapterId,dto));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("{subjectId}/get_item_list")
    public Result getItemList(@PathVariable Long subjectId,@RequestBody BaseDto dto){
        try {
            return Result.successResponse(subjectService.getItemList(subjectId,dto.getUid()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }
    @PostMapping("reset_item_chapter")
    public Result resetItemChapter(@RequestBody ResetItemDto dto){
        try {
            return Result.successResponse(subjectService.resetItemChapter(dto.getItemId(),dto.getNewChapterId()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }
    @PostMapping("reset_item_public")
    public Result resetItemPublic(@RequestBody ResetItemDto dto){
        try {
            return Result.successResponse(subjectService.resetItemPublic(dto.getItemId(),dto.getIsPublic()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("new_Item")
    public Result newItem(@RequestBody NewItemDto dto) {
        try {
            return Result.successResponse(subjectService.newItem(dto.getItemTitle(), dto.getChapterId(), dto.getUid(), dto.getIsPublic()));
        } catch (StatusFailException e) {
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("upload_resource/{itemId}")
    public Result uploadResource(@RequestParam("raw") MultipartFile raw,@PathVariable Long itemId) {
        try {
            return Result.successResponse(subjectService.uploadResource(raw,itemId));
        } catch (StatusFailException e) {
            return Result.FailResponse(e.getMessage());
        }
    }

    @GetMapping("/upload_status/{resourceId}")
    public Result uploadStatus(@PathVariable Long resourceId){
        return subjectService.uploadStatus(resourceId);
    }

    @PostMapping("upload_markdown/{itemId}")
    public Result uploadMarkdown(@PathVariable Long itemId,@RequestBody MarkDownDto dto){
        try {
            return Result.successResponse(subjectService.uploadMarkdown(itemId,dto));
        } catch (StatusFailException e) {
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("del_resource/{resourceId}")
    public Result delResource(@PathVariable Long resourceId,@RequestBody BaseDto dto){
        try {
            return Result.successResponse(subjectService.delResource(resourceId,dto));
        } catch (StatusFailException e) {
            return Result.FailResponse(e.getMessage());
        }
    }

}
