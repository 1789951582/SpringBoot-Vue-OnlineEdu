package com.xh.online_edu.model.enums;

import com.xh.online_edu.common.exception.StatusFailException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ChatModel {

    BAILIAN(0,"Bailian"),
    VQWEN2(1,"VQwen2.5"),
    LLAMA3(2,"Llama3"),
    QIANFAN(3,"Qianfan"),
    HUNYUAN(4,"Hunyuan"),
    GLM(5,"Glm"),
    MISTRAL(6,"Mistral"),
    GEMMA(7,"Gemma"),
    YI(8,"Yi"),
    SPARK(9,"Spark");

    private final int key;
    private final String modelName;

    private static final Map<String, ChatModel> KEY_MAP = new HashMap<>();

    static {
        for (ChatModel model : values()){
            KEY_MAP.put(model.modelName,model);
        }
    }

    public static ChatModel of(String name) throws StatusFailException {
        ChatModel type = KEY_MAP.get(name);
        if (type == null) {
            throw new StatusFailException("无效的model: " + name);
        }
        return type;
    }
}
