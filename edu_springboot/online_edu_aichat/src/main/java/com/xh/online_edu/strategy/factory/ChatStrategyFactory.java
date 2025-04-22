package com.xh.online_edu.strategy.factory;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.strategy.ChatStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ChatStrategyFactory {
    private final Map<ChatModel, ChatStrategy> strategyMap;

    @Autowired
    public ChatStrategyFactory(List<ChatStrategy> strategies) {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        ChatStrategy::getChatModel,
                        Function.identity()
                ));
    }

    public ChatStrategy getStrategy(ChatModel type) throws StatusFailException {
        return Optional.ofNullable(strategyMap.get(type))
                .orElseThrow(() -> new StatusFailException("不支持的model"+type.getModelName()));
    }
}
