package com.xh.online_edu.strategy.factory;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.strategy.QuestionCoreStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class QuestionStrategyFactory {
    private final Map<QuestionType, QuestionCoreStrategy> strategyMap;

    @Autowired
    public QuestionStrategyFactory(List<QuestionCoreStrategy> strategies) {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        QuestionCoreStrategy::getQuestionType,
                        Function.identity()
                ));
    }

    public QuestionCoreStrategy getStrategy(QuestionType type) throws StatusFailException {
        return Optional.ofNullable(strategyMap.get(type))
                .orElseThrow(() -> new StatusFailException("不支持的题目类型"));
    }
}
