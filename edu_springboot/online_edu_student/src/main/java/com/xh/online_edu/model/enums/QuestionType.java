package com.xh.online_edu.model.enums;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.res.FillQuestionRes;
import com.xh.online_edu.model.res.JudgeQuestionRes;
import com.xh.online_edu.model.res.MultiQuestionRes;
import com.xh.online_edu.model.res.SubjectiveQuestionRes;
import com.xh.online_edu.pojo.po.question.EduFillQuestion;
import com.xh.online_edu.pojo.po.question.EduJudgeQuestion;
import com.xh.online_edu.pojo.po.question.EduMultiQuestion;
import com.xh.online_edu.pojo.po.question.EduSubjectiveQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

//@Getter
//@AllArgsConstructor
//public enum QuestionType {
//    MULTI(1, "multi", EduMultiQuestion.class, MultiQuestionRes.class),
//    JUDGE(2, "judge", EduJudgeQuestion.class, JudgeQuestionRes.class),
//    FILL(3, "fill", EduFillQuestion.class, FillQuestionRes.class),
//    SUBJECTIVE(4,"subjective", EduSubjectiveQuestion.class, SubjectiveQuestionRes.class);
//
//    private final int code;
//    private final String desc;
//    private final Class<?> poClass;
//    private final Class<?> resClass;
//
//    public static QuestionType of(int code) throws StatusFailException {
//        return Arrays.stream(values())
//                .filter(t -> t.code == code)
//                .findFirst()
//                .orElseThrow(() -> new StatusFailException("无效的题目类型"));
//    }
//
////    public static QuestionType of(String desc) throws StatusFailException {
////        return Arrays.stream(values())
////                .filter(t -> t.desc == desc)
////                .findFirst()
////                .orElseThrow(() -> new StatusFailException("无效的题目类型"));
////    }
//}
