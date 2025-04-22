package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author XiaoHao
 * @since 2025-01-29
 */
@Data
@TableName("stu_info")
@ApiModel(value="StudentInfo对象", description="")
//@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
public class StuInfoPo {

//    private static final long serialVersionID = 1L;

    @TableId(value = "s_uid",type = IdType.AUTO)
    private Long sUid;

    @ApiModelProperty(value = "用户名")
    private String sNickname;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String sPwd;

    @ApiModelProperty(value = "真实姓名")
    private String sRealname;

    @ApiModelProperty(value = "手机号")
    private String sPhone;

    @ApiModelProperty(value = "邮箱")
    private String sEmail;

    @ApiModelProperty(value = "学校")
    private String sSchool;

    @ApiModelProperty(value = "专业")
    private String sCourse;

    @ApiModelProperty(value = "学号")
    private String sNum;

    @ApiModelProperty(value = "性别")
    private short sSex;

    @ApiModelProperty(value = "年龄")
    private Short sAge;

    @ApiModelProperty(value = "头像url")
    private String sAvatar;

    @ApiModelProperty(value = "注册日期")
    @JsonIgnore
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新日期")
    @JsonIgnore
    private LocalDateTime updateTime;

    // 自定义脱敏后的邮箱获取方法
    public String getSEmail() {
        if (sEmail == null || sEmail.isEmpty()) {
            return sEmail;
        }
        try {
            String[] parts = sEmail.split("@");
            if (parts.length != 2) return sEmail;

            String prefix = parts[0];
            String domain = parts[1];

            // 根据前缀长度动态处理脱敏
            if (prefix.length() >= 4) {
                // 保留前4位，隐藏后续内容（至少保留4位时显示4个星号）
                return prefix.substring(0, 4) + "****@" + domain;
            } else if (prefix.length() > 2) {
                // 保留前2位，隐藏后续
                return prefix.substring(0, 2) + "****@" + domain;
            } else {
                // 过短时直接显示星号
                return "****@" + domain;
            }
        } catch (Exception e) {
            return sEmail; // 异常情况返回原始值
        }
    }
}
