//package com.xh.online_edu.common;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//
//import java.io.Serializable;
//
//@Data
//public class Result<T> implements Serializable {
//    private final int status;// 状态码
//    private final T data;   // 返回的数据
//    private final String msg;    // 自定义信息
//
//    @Getter
//    @AllArgsConstructor
//    private enum ResultStatus{
//        SUCCESS(200,"成功"),
//        FAIL(400,"失败"),
//        ACCESS_DENIED(401,"访问受限"),
//        FORBIDDEN(403,"拒绝访问"),
//        NOT_FOUND(404,"数据不存在"),
//        SYSTEM_ERROR(500,"系统错误");
//        private int status;
//        private String description;
//    }
//
////    public ResultVo(){
////
////    }
//
//    /**
//     * 成功的结果
//     *
//     * @param data 返回结果
//     * @param msg  返回信息
//     */
//    public static <T> Result<T> successResponse(T data, String msg){
//        return new Result<T>(ResultStatus.SUCCESS.getStatus(), data,msg);
//    }
//
//    /**
//     * 成功的结果
//     *
//     * @param data 返回结果
//     */
//    public static <T> Result<T> successResponse(T data){
//        return new Result<T>(ResultStatus.SUCCESS.getStatus(), data, ResultStatus.SUCCESS.getDescription());
//    }
//
//    /**
//     * 失败的结果
//     *
//     * @param resultStatus 返回信息
//     */
//    public static <T> Result<T> errorResponse(ResultStatus resultStatus) {
//        return new Result<T>(resultStatus.getStatus(), null, resultStatus.getDescription());
//    }
//
//    public static <T> Result<T> FailResponse(){
//        return new Result<>(ResultStatus.FAIL.getStatus(), null, ResultStatus.FAIL.getDescription());
//    }
//
//    public static <T> Result<T> FailResponse(String msg){
//        return new Result<>(ResultStatus.FAIL.getStatus(), null,msg);
//    }
//    public static <T> Result<T> AccessDeniedResponse(){
//        return new Result<>(ResultStatus.ACCESS_DENIED.getStatus(), null, ResultStatus.ACCESS_DENIED.getDescription());
//    }
//
//    public static <T> Result<T> AccessDeniedResponse(String msg){
//        return new Result<>(ResultStatus.ACCESS_DENIED.getStatus(), null,msg);
//    }
//
//    public static <T> Result<T> SystemErrorResponse(String msg){
//        return new Result<>(ResultStatus.SYSTEM_ERROR.getStatus(), null,msg);
//    }
//
//    public static <T> Result<T> ForbiddenResponse(String msg){
//        return new Result<>(ResultStatus.FORBIDDEN.getStatus(), null,msg);
//    }
//    public static <T> Result<T> NotFound(String msg){
//        return new Result<>(ResultStatus.NOT_FOUND.getStatus(), null,msg);
//    }
//}
