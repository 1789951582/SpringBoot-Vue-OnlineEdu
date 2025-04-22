package com.xh.online_edu.pojo.po.question;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("edu_multi_question")
public class EduMultiQuestion extends BaseQuestion {
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
}
