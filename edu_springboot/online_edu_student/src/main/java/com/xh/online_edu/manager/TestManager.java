package com.xh.online_edu.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.EduTestCommitPoMapper;
import com.xh.online_edu.mapper.po.EduTestPaperPoMapper;
import com.xh.online_edu.mapper.po.StuTestAnswerPoMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.dto.CommitTestReq;
import com.xh.online_edu.model.res.TestPaperRes;
import com.xh.online_edu.pojo.po.EduTestCommit;
import com.xh.online_edu.pojo.po.EduTestPaperPo;
import com.xh.online_edu.pojo.po.StuTestAnswer;
import com.xh.online_edu.strategy.ScoreCalculateStrategy;
import com.xh.online_edu.strategy.factory.ScoreStrategyFactory;
import com.xh.online_edu.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestManager {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    EduTestPaperPoMapper eduTestPaperMapper;
    @Autowired
    StuTestAnswerPoMapper answerPoMapper;
    @Autowired
    EduTestCommitPoMapper testCommitPoMapper;
    @Autowired
    private ScoreStrategyFactory scoreStrategyFactory;

    public TestPaperRes getPaper(Long testId){
        TestPaperRes testObject=redisUtils.get("testInfo:"+testId,TestPaperRes.class);
        if (testObject!=null){
            redisUtils.expire("testInfo:",3600*24);
            return testObject;
        }
        QueryWrapper<EduTestPaperPo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("test_id",testId)
                .orderByAsc("question_type","question_idx");
        List<EduTestPaperPo> testPaperList =eduTestPaperMapper.selectList(queryWrapper);
        Map<Integer,List<EduTestPaperPo>> grouped=testPaperList.stream()
                .collect(Collectors.groupingBy(EduTestPaperPo::getQuestionType));
        List<EduTestPaperPo> multiQuestionList=grouped.get(1);
        List<EduTestPaperPo> judgeQuestionList=grouped.get(2);
        List<EduTestPaperPo> fillQuestionList=grouped.get(3);
        List<EduTestPaperPo> subjectiveQuestionList=grouped.get(4);
        TestPaperRes testPaperRes=new TestPaperRes();
        testPaperRes.putQuestionMap(QuestionType.MULTI,multiQuestionList);
        testPaperRes.putQuestionMap(QuestionType.JUDGE,judgeQuestionList);
        testPaperRes.putQuestionMap(QuestionType.FILL, fillQuestionList);
        testPaperRes.putQuestionMap(QuestionType.SUBJECTIVE,subjectiveQuestionList);
        testPaperRes.setTestId(testId);
        redisUtils.set("testInfo:"+testId,testPaperRes,3600*24);
        return testPaperRes;
    }

    @Async
    public void createJudgeTask(Long testId, CommitTestReq req, EduTestCommit testCommit){
//        1.获取试卷
        TestPaperRes paperRes=this.getPaper(testId);
        // 2. 合并所有题目
        List<EduTestPaperPo> allQuestions = paperRes.getQuestionMap().values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        //学生提交答案
        Map<Long, String> unifiedAnswers=req.getAnswerMap();

        //创建answerPo模板
        StuTestAnswer template=new StuTestAnswer();
//        template.setTestId(testId);
        template.setCommitId(testCommit.getCommitId());

        //判题得到分数
        double totalScore = calculateTotalScore(template,allQuestions, unifiedAnswers);

        testCommit.setScore(totalScore);

        testCommitPoMapper.updateById(testCommit);

    }

    private double calculateTotalScore(StuTestAnswer template ,List<EduTestPaperPo> questions, Map<Long, String> answers) {
        return questions.parallelStream().mapToDouble(q->{
            String answer=answers.get(q.getQuestionId());
            if (answer==null||answer.isBlank())
                return 0;
            try{
                //找到对应的判题对象
                QuestionType questionType=QuestionType.of(q.getQuestionType());
                ScoreCalculateStrategy scoreCalculateStrategy= scoreStrategyFactory.getStrategy(questionType);
                StuTestAnswer answerPo=new StuTestAnswer();
                Long answerId = YitIdHelper.nextId();
                //判题得分
                double score=scoreCalculateStrategy.calculateScore(q,answer,answerId);
                //保存到数据库
                BeanUtils.copyProperties(template,answerPo);
                answerPo.setAnswerId(answerId);
                answerPo.setQuestionId(q.getQuestionId());
                answerPo.setAnswer(answer);
                answerPo.setScore(score);
                answerPoMapper.insert(answerPo);
                return score;
            }catch (StatusFailException e){
                log.error("在判questionId:"+q.getQuestionId()+"时遇到"+e);
                return 0;
            }
        }).sum();
    }
}
