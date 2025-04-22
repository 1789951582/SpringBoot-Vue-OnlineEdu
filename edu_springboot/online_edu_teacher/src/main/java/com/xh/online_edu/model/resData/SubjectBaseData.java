package com.xh.online_edu.model.resData;

import com.xh.online_edu.pojo.po.EduChapter;
import com.xh.online_edu.pojo.po.EduTest;
import com.xh.online_edu.pojo.vo.TchCourse;
import lombok.Data;

import java.util.List;

@Data
public class SubjectBaseData {
    private Long subjectId;
    private String subjectTitle;
    private List<TchCourse> courseList;
    private List<EduChapter> chapterList;
    private List<EduTest> testList;
}
