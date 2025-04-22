package com.xh.online_edu.service.impl;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.*;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.dto.CommitTestReq;
import com.xh.online_edu.model.res.*;
import com.xh.online_edu.pojo.po.*;
import com.xh.online_edu.pojo.vo.StuAnswer;
import com.xh.online_edu.service.TestService;
import com.xh.online_edu.strategy.QuestionStrategy;
import com.xh.online_edu.strategy.factory.QuestionStrategyFactory;
import com.xh.online_edu.manager.TestManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestManager testManager;
    @Autowired
    private QuestionStrategyFactory questionStrategyFactory;
    @Autowired
    EduTestCommitPoMapper testCommitPoMapper;
//    @Autowired
//    RedisUtils redisUtils;

    @Override
    public TestPaperRes getPaper(Long testId) {
        return testManager.getPaper(testId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseQuestionRes getQuestion(Integer questionType, Long questionId) throws StatusFailException {
        QuestionType type = QuestionType.of(questionType);
        QuestionStrategy strategy = questionStrategyFactory.getStrategy(type);
        // 查询数据
        BaseQuestionRes res = strategy.getQuestion(questionId);
        if (res == null) throw new StatusFailException("题目不存在");
        return res;
    }

    @Override
    public boolean commitTest(Long testId, CommitTestReq req) throws StatusFailException {
        //创建提交记录到数据库
        EduTestCommit testCommit=new EduTestCommit();
        testCommit.setTestId(testId);
        testCommit.setSUid(req.getUid());
        int sqlRes= testCommitPoMapper.insert(testCommit);
        if (sqlRes==1){
            testManager.createJudgeTask(testId,req, testCommit);
            return true;
        }
        throw new StatusFailException("创建提交记录失败");
//        return false;
    }

    @Override
    public CommitListRes getCommitList(Long testId, Long uid) throws StatusFailException {
        CommitListRes commitListRes= testCommitPoMapper.selectCommit(testId);
        commitListRes.setTestCommitList(testCommitPoMapper.selectListByUidAndTid(uid,testId));
        return commitListRes;
    }

    @Override
    public ArrayList<StuAnswer> getAnswerInfo(Integer type, Long questionId, Long uid) throws StatusFailException {
        if (questionId==null||type==null){
            throw new StatusFailException("参数不能为空");
        }
//        Object tempObj=redisUtils.get("AnswerInfo:"+questionId);
//        if (tempObj!=null){
//            return (ArrayList<StuAnswer>) tempObj;
//        }
        QuestionStrategy strategy = questionStrategyFactory.getStrategy(QuestionType.of(type));
        ArrayList<StuAnswer> stuAnswers=strategy.getStuAnswerList(questionId,uid);
//        redisUtils.set("questionAnswerInfo:"+questionId,stuAnswers,3600);
        return stuAnswers;
    }

}
