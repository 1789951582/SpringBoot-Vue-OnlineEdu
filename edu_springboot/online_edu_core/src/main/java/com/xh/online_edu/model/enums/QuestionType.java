package com.xh.online_edu.model.enums;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.pojo.po.question.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum QuestionType {
    MULTI(1, "multi", EduMultiQuestion.class),
    JUDGE(2, "judge", EduJudgeQuestion.class),
    FILL(3, "fill", EduFillQuestion.class),
    SUBJECTIVE(4,"subjective", EduSubjectiveQuestion.class);

    private final int code;
    private final String desc;
    private final Class<? extends BaseQuestion> poClass;

    private static final Map<Integer, QuestionType> CODE_MAP = new HashMap<>();

    static {
        // 类加载时建立双索引
        for (QuestionType type : values()) {
            CODE_MAP.put(type.code, type);
        }
    }

    public static QuestionType of(int code) throws StatusFailException {
        QuestionType type = CODE_MAP.get(code);
        if (type == null) {
            throw new StatusFailException("无效的题目类型编码: " + code);
        }
        return type;
    }
}
