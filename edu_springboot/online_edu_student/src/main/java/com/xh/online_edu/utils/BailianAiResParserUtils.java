package com.xh.online_edu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BailianAiResParserUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static class ParseResult {
        private final double actualScore;
        private final String feedback;

        public ParseResult(double actualScore, String feedback) {
            this.actualScore = actualScore;
            this.feedback = feedback;
        }

        // Getters
        public double getActualScore() { return actualScore; }
        public String getFeedback() { return feedback; }
    }

    public static ParseResult parseScore(String jsonResponse, double totalPoints) throws JsonProcessingException {
        // 解析JSON结构
        JsonNode root = objectMapper.readTree(jsonResponse);
        String text = root.path("output").path("text").asText();

        // 使用预编译正则表达式提升性能
        Pattern scorePattern = Pattern.compile("得分:\\s*(\\d+)\\s*/\\s*(\\d+)");
        Matcher scoreMatcher = scorePattern.matcher(text);

        if (!scoreMatcher.find()) {
            System.out.println(jsonResponse);
            throw new IllegalArgumentException("无效的评分格式");
        }

        int rawScore = Integer.parseInt(scoreMatcher.group(1));
        int fullScore = Integer.parseInt(scoreMatcher.group(2));

        if (fullScore == 0) {
            throw new ArithmeticException("满分分值不能为0");
        }

        // 计算实际得分（保留两位小数）
        double actualScore = Math.round((rawScore * totalPoints / fullScore) * 100.0) / 100.0;

        // 提取点评内容（兼容多种换行格式）
        Pattern feedbackPattern = Pattern.compile("点评和建议:([\\s\\S]*)");
        Matcher feedbackMatcher = feedbackPattern.matcher(text);
        String feedback = feedbackMatcher.find() ? feedbackMatcher.group(1).trim() : "";

        return new ParseResult(actualScore, feedback);
    }
}
