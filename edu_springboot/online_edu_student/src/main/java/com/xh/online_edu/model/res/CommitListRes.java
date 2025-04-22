package com.xh.online_edu.model.res;

import com.xh.online_edu.pojo.po.EduTestCommit;
import lombok.Data;
import java.util.List;

@Data
public class CommitListRes {
    private Long testId;
    private String testTitle;
    private String testDescription;
    private List<EduTestCommit> testCommitList;
}
