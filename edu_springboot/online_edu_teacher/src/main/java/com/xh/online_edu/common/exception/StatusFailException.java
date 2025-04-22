//package com.xh.online_edu.common.exception;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @Author: XiaoHao
// * @Date: 2025/01/31 17:10
// * @Description:
// */
//@Slf4j
//public class StatusFailException extends Exception{
//
////    @Autowired
////    RedisUtils redisUtils;
//
//    public StatusFailException() {
//    }
//
//    public StatusFailException(String message) {
//        super(message);
//    }
//
//    public StatusFailException(String message,String ip) {
//        super(message);
//        log.warn(message+":"+ip);//输出可疑异常
//    }
//
//    public StatusFailException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public StatusFailException(Throwable cause) {
//        super(cause);
//    }
//
//    public StatusFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
//}