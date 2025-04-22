package com.xh.online_edu.service.impl;

import cloud.tianai.captcha.common.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.EduReleaseTestMapper;
import com.xh.online_edu.mapper.po.EduTestMapper;
import com.xh.online_edu.mapper.po.EduTestPaperMapper;
import com.xh.online_edu.mapper.vo.StuTestMapper;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.dto.NewTestDto;
import com.xh.online_edu.model.dto.ReleaseTestDto;
import com.xh.online_edu.model.dto.TestPaperDto;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.EduReleaseTest;
import com.xh.online_edu.pojo.po.EduTest;
import com.xh.online_edu.pojo.po.EduTestPaper;
import com.xh.online_edu.pojo.vo.StuTest;
import com.xh.online_edu.service.TestSerice;
import com.xh.online_edu.strategy.QuestionStrategy;
import com.xh.online_edu.strategy.factory.QuestionStrategyFactory;
import com.xh.online_edu.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TestServiceImpl implements TestSerice {

    @Autowired
    StuTestMapper stuTestMapper;
    @Autowired
    EduReleaseTestMapper releaseTestMapper;
    @Autowired
    EduTestMapper eduTestMapper;
    @Autowired
    QuestionStrategyFactory questionStrategyFactory;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    EduTestPaperMapper testPaperMapper;

    @Override
    public List<StuTest> getReleaseTest(Long courseId) throws StatusFailException {
        if (courseId==null) throw new StatusFailException("courseId参数不能为空");
        QueryWrapper<StuTest> testQueryWrapper=new QueryWrapper<>();
        testQueryWrapper.eq("course_id",courseId);
        List<StuTest> stuTestList=stuTestMapper.selectList(testQueryWrapper);
        return stuTestList;
    }

    @Override
    public boolean releaseTest(ReleaseTestDto dto) throws StatusFailException {
        EduReleaseTest releaseTest=new EduReleaseTest();
        releaseTest.setTestId(dto.getTestId());
        releaseTest.setCourseId(dto.getCourseId());
        releaseTest.setEndTime(dto.getEndTime());
        int isOk = releaseTestMapper.insert(releaseTest);
        if (isOk<0){
            throw new StatusFailException("发布失败");
        }
        return true;
    }

    @Override
    public boolean setPublic(Long testId, Integer val) throws StatusFailException {
        if (val==null || testId==null){
            throw new StatusFailException();
        }
        eduTestMapper.setPublic(testId,val);
        return false;
    }

    @Override
    public EduTest newTest(Long subjectId, NewTestDto dto) throws StatusFailException {
        if (subjectId==null) throw new StatusFailException("subjectId参数不能为空");
        EduTest test=new EduTest();
        test.setTestTitle(dto.getTestTitle());
        test.setTestDescription(dto.getTestDescription());
        test.setSubjectId(subjectId);
        test.setTeacherId(dto.getUid());
        test.setIsPublic(dto.getIsPublic());
        int isOk=eduTestMapper.insert(test);
        if (isOk<0){
            throw new StatusFailException("新建失败");
        }
        return test;
    }

    @Override
    public boolean rewriteTest(NewTestDto dto) throws StatusFailException {
        int isOk=eduTestMapper.rewriteTest(dto.getTestId(), dto.getTestTitle(), dto.getTestDescription());
        if (isOk<0){
            throw new StatusFailException("更新失败");
        }
        return true;
    }

    @Override
    public List getQuestionList(Long subjectId, Integer type) throws StatusFailException {
        if (type==null || subjectId==null) throw new StatusFailException("参数错误");
        QuestionType questionType=QuestionType.of(type.intValue());
        QuestionStrategy strategy =questionStrategyFactory.getStrategy(questionType);
        return strategy.getQuestionList(subjectId);
    }

    @Override
    public BaseQuestion newQuestion(Integer type, NewQuestionDto dto) throws StatusFailException {
        if (type==null) throw new StatusFailException("参数错误");
        QuestionType questionType=QuestionType.of(type.intValue());
        QuestionStrategy strategy =questionStrategyFactory.getStrategy(questionType);
        BaseQuestion question = strategy.insertNewQuestion(dto);
        if (question.getQuestionId()==null) throw new StatusFailException("插入失败");
        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateQuestion(Integer type, NewQuestionDto dto) throws StatusFailException {
        if (type==null) throw new StatusFailException("参数错误");
        QuestionType questionType=QuestionType.of(type.intValue());
        QuestionStrategy strategy =questionStrategyFactory.getStrategy(questionType);
        int isOk=strategy.updateQuestion(dto);
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        redisUtils.del(String.format("question:type:%s:id:%d", questionType.getDesc(), dto.getQuestionId()));
                    }
                }
        );
        if (isOk<0) throw new StatusFailException("修改失败");
        return true;
    }

    @Override
    public boolean delQuestion(Integer type,Long questionId) throws StatusFailException {
        if (type==null || questionId==null) throw new StatusFailException("参数错误");
        QuestionType questionType=QuestionType.of(type.intValue());
        QuestionStrategy strategy =questionStrategyFactory.getStrategy(questionType);
        int isOk=strategy.delQuestion(questionId);
        if (isOk<0) throw new StatusFailException("删除失败");
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        redisUtils.del(String.format("question:type:%s:id:%d", questionType.getDesc(),questionId));
                    }
                }
        );
        return true;
    }

    @Override
    public BaseQuestion getQuestion(Integer type, Long questionId) throws StatusFailException {
        if (type==null || questionId==null) throw new StatusFailException("参数错误");
        QuestionType questionType=QuestionType.of(type.intValue());
        QuestionStrategy strategy =questionStrategyFactory.getStrategy(questionType);
        return strategy.getRawQuestion(questionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object commitTestPaper(TestPaperDto dto) throws StatusFailException{
//        先删除后更新插入
        int isOk=0;
        if (dto.getDelQueIdList().size()>0) {
            isOk = testPaperMapper.delQueObj(dto.getDelQueIdList(), dto.getTestId());
            if (isOk<0){
                throw new StatusFailException("移除题目失败，请稍后重试");
            }
        }

        List<EduTestPaper> allQuestions = dto.getQuestionMap().values().stream()
                .flatMap(Collection::stream)
//                .peek(paper -> {
//                    // 2. 填充复合主键中的testId
//                    EduTestPaper.PaperId paperId = new EduTestPaper.PaperId();
//                    paperId.setTestId(testId);
//                    paperId.setQuestionId(paper.getPaperId().getQuestionId());
//                    paper.setPaperId(paperId);
//                })
                .collect(Collectors.toList());

        // 3. 批量执行 Upsert
        if (!CollectionUtils.isEmpty(allQuestions)) {
            isOk=testPaperMapper.batchUpsert(allQuestions, dto.getTestId());
            if (isOk<0){
                throw new StatusFailException("更新数据库失败,请稍后重试");
            }
        }
        //删除缓存
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        redisUtils.del("testInfo"+ dto.getTestId());
                    }
                }
        );
        return true;
    }

//    private void processDeletions(List<EduTestPaper> delList) throws StatusFailException {
//        if (CollectionUtils.isEmpty(delList)) {
//            return;
//        }
//
//        // 2. 构建批量删除条件
//        List<Map<String, Object>> deleteConditions = delList.stream()
//                .map(paper -> {
//                    Map<String, Object> condition = new HashMap<>(2);
//                    condition.put("test_id", paper.getTestId());
//                    condition.put("question_id", paper.getQuestionId());
//                    return condition;
//                })
//                .collect(Collectors.toList());
//
//        try {
//            // 3. 使用MyBatis-Plus的deleteByMap批量删除
//            int deletedRows = testPaperMapper.deleteBatchMaps(deleteConditions);
//
//            // 4. 验证删除结果（可选）
//            if (deletedRows != deleteConditions.size()) {
//                log.warn("部分删除失败，预期删除{}条，实际删除{}条",
//                        deleteConditions.size(), deletedRows);
//            }
//        } catch (Exception e) {
//            log.error("删除试题失败", e);
//            throw new StatusFailException("试题删除失败: " + e.getMessage());
//        }
//    }

//    @Override
//    public BaseQuestion getQuestion(Integer type, Long questionId) throws StatusFailException {
//        if (type==null) throw new StatusFailException("参数错误");
//        QuestionType questionType=QuestionType.of(type.intValue());
//        QuestionStrategy strategy =questionStrategyFactory.getStrategy(questionType);
//        BaseQuestion question=strategy.selectQuestion(questionId);
//        return question;
//    }


}
