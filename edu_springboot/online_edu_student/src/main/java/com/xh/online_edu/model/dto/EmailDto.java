package com.xh.online_edu.model.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

/**
 * @Author: 小皓
 * @Date: 2025/2/10 16:43
 * @Description: 邮箱人机验证数据实体类
 */

@Data
public class EmailDto {
    private String emailAddr;
    private String codeId;
    private ImageCaptchaTrack datas;
}
