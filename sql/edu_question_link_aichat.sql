/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : online_edu_dev

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 07/04/2025 18:10:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for edu_question_link_aichat
-- ----------------------------
DROP TABLE IF EXISTS `edu_question_link_aichat`;
CREATE TABLE `edu_question_link_aichat`  (
  `question_id` bigint UNSIGNED NOT NULL COMMENT '题目id',
  `uid` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `cid` bigint NULL DEFAULT NULL COMMENT '对话id',
  PRIMARY KEY (`question_id`, `uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
