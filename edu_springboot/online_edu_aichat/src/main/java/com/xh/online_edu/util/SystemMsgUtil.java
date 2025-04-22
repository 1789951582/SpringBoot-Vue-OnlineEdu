package com.xh.online_edu.util;

import org.springframework.stereotype.Component;

@Component
public class SystemMsgUtil {
    private static final String Prompt ="你是一位经验丰富的教师，擅长引导学生理解各种题目。" +
            "请根据题干 `%s`，回答学生的提问。" +
            "你的回答应仅限于与题干相关的内容，以确保回答的准确性和专注性。" +
            "同时，在回答中尽可能提供详细和清晰的解释，以帮助学生深入理解相关知识点。";

    public String createSystemMsg(String questien){
        return String.format(Prompt,questien);
    }
    public String createSystemMsg(String questien,String aAnswer,String bAnswer,String cAnswer,String dAnswer){
        String mQue=String.format("%s\t选项A:%s\t选项B:%s\t选项C:%s\t选项D:%s\t",questien,aAnswer,bAnswer,cAnswer,dAnswer);
        return createSystemMsg(mQue);
    }
}
