package com.xh.online_edu;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.EduAijudgeMsgPoMapper;
import com.xh.online_edu.mapper.vo.StuTaskVoMapper;
import com.xh.online_edu.model.res.TaskRes;
import com.xh.online_edu.model.res.TestPaperRes;
import com.xh.online_edu.pojo.po.EduAijudgeMsgPo;
import com.xh.online_edu.pojo.po.EduTestPaperPo;
import com.xh.online_edu.pojo.vo.StuTaskVo;
import com.xh.online_edu.service.TestService;
import com.xh.online_edu.utils.BailianAiJudgeUtils;
import com.xh.online_edu.utils.BailianAiResParserUtils;
import com.xh.online_edu.utils.EmailUtils;
import com.xh.online_edu.utils.RedisUtils;
import io.jsonwebtoken.security.Keys;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class OnlineEduApplicationTests {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    EmailUtils emailUtils;
    @Autowired
    StuTaskVoMapper stuTaskVoMapper;
    @Autowired
    TestService testService;
    @Autowired
    BailianAiJudgeUtils aiJudgeUtils;

    @Test
    void contextLoads() {
//        Date nowDate=new Date();
//        redisUtils.set("test",111, 60);
//        Long j = redisUtils.getExpire("test3");
//        Object i = redisUtils.get("test");
//        System.out.println(i);
//        System.out.println(j);
    }

//    @Test
//    void emailTest(){
//        DateTime expireTime = DateUtil.offsetMinute(new Date(), 10);
//        redisUtils.set("EmailCord"+"111","1234",expireTime.getTime());
//        emailUtils.sendRegisterCode("l1789951582@gmail.com","1234",expireTime);
//    }
//
//    @Test
//    void test(){
//        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // 生成安全的 HS256 密钥
//        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
//        System.out.println("安全的 Base64 密钥: " + base64Key);
//    }
//
//    @Test
//    void testTaskMapper(){
//        List<StuTaskVo> stuTaskVoList =stuTaskVoMapper.selectTaskListByCourseId(1005L);
//        TaskRes taskRes=new TaskRes();
//        taskRes=taskRes.TaskResBuilder(stuTaskVoList);
//    }
//
//    @Test
//    void testcommit() throws StatusFailException {
//        TestPaperRes paperRes=testService.getPaper(1L);
//        List<EduTestPaperPo> allQuestions = paperRes.getQuestionMap().values().stream()
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
//        System.out.println(allQuestions);
//    }
//
//    @Test
//    void testreqai() throws IOException, InterruptedException {
//        Response response = aiJudgeUtils.reqJudge("多路复用技术有哪些？请例举出来，并简单描述其中一种","时分波分码分");
//        if (response.isSuccessful() && response.body()!=null) {
//            String responseBody  = response.body().string();
//            System.out.println(responseBody);
//        }else {
//            System.out.println("aaa");
//        }
//    }

}
