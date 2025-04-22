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

 Date: 06/04/2025 13:03:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aichat_chat
-- ----------------------------
DROP TABLE IF EXISTS `aichat_chat`;
CREATE TABLE `aichat_chat`  (
  `cid` bigint UNSIGNED NOT NULL COMMENT '对话id',
  `chat_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '对话名',
  `uid` bigint NULL DEFAULT NULL COMMENT '所属用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aichat_chat
-- ----------------------------
INSERT INTO `aichat_chat` VALUES (662094719733829, 'new Chat', 1, '2025-04-04 23:30:21');
INSERT INTO `aichat_chat` VALUES (662426184978501, 'new Chat', 100000009, '2025-04-05 21:59:05');

SET FOREIGN_KEY_CHECKS = 1;
