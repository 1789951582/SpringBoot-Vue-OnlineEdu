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

 Date: 29/03/2025 12:18:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for edu_aijudge_msg
-- ----------------------------
DROP TABLE IF EXISTS `edu_aijudge_msg`;
CREATE TABLE `edu_aijudge_msg`  (
  `answer_id` bigint UNSIGNED NOT NULL COMMENT '题目id',
  `ai_msg` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'ai反馈',
  `create_time` datetime NULL DEFAULT NULL COMMENT '判题时间',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_aijudge_msg
-- ----------------------------
INSERT INTO `edu_aijudge_msg` VALUES (647763394060362, '- 题目要求列举四种多路复用技术，并简单描述其中一种。\n- 学生的回答中提到了四种技术：时分多路复用、波分多路复用、码分多路复用和频分多路复用，这四点都对。\n- 但是回答中没有对其中任何一种技术进行简单描述，因此扣掉6分。\n- 在回答时应该具体描述一种技术，例如时分多路复用（TDM）是利用时间分割来分配多个信号在同一信道上同时传输的一种多路复用技术。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (647763394060365, '1. **发送延时计算**:\n   - 发送延时 = 数据大小 / 数据发送速率\n   - 数据大小 = 100 M = 100 * 1024 * 1024 B\n   - 数据发送速率 = 500 kbps = 500 * 1024 B/s\n   - 发送延时 = (100 * 1024 * 1024) / (500 * 1024) = 200 s\n   - 学生的答案正确，但未展示计算过程，扣1分。\n\n2. **传播延时计算**:\n   - 传播延时 = 介质长度 / 电磁波传播速度\n   - 介质长度 = 2000 km = 2000 * 1000 m\n   - 电磁波传播速度 = 3 * 10^8 m/s\n   - 传播延时 = (2000 * 1000) / (3 * 10^8) = 6.67 ms\n   - 学生的答案为0.01秒，显然错误，扣4分。\n\n3. **整体评述**:\n   - 学生给出了正确的发送延时，但在传播延时的计算上出现了严重失误。建议学生复习电磁波传播原理和单位换算。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (647763394134085, '1. 题目要求生成多项式 \\(G(X) = X^3 + X^2 + X + 1\\)（即多项式 \\(X^3 + X^2 + X + 1\\)），而学生提供的生成多项式为 \\(N=5, k=4\\) 的多项式 \\(11001\\)，这与题目要求不符。\n2. 计算CRC之前，需要将发送的数据比特序列扩展到比生成多项式 \\(N\\) 长度多一个比特的位置。题目中数据字段为 \\(1110111\\)，长度为7比特。\n3. 扩展后的比特序列应为 \\(111011100\\)（将原始比特序列前添加一个0）。\n4. 计算 \\(f(X)\\) 得到余数，并将余数添加到原始数据后面形成最终的发送数据比特序列。\n5. 学生给出的生成多项式和扩展过程都不正确。正确的扩展后比特序列应该为 \\(111011100\\)，计算得到的余数为 \\(1110\\)，因此最终发送的数据比特序列应该是 \\(1110111001110\\)。\n\n正确的答案应该是：生成多项式为 \\(X^3 + X^2 + X + 1\\)，扩展后的比特序列 \\(111011100\\)，余数为 \\(1110\\)，最终发送的数据比特序列是 \\(1110111001110\\)。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (647763394146373, '1. 题目要求详细回答每个问题，而学生仅回答“不会”，这表明学生没有尝试解答问题。\n2. UDP报头的格式如下：\n   - 源端口号：前16位（高位）\n   - 目标端口号：后16位（低位）\n   - 数据字段长度：8位\n   - 剩余字段：8位\n\n根据已知的16进制报头信息（06320035 001CE217），我们可以解析出每个部分的具体数值：\n- 源端口号：0x0632 = 2044（二进制表示：00010000110100）\n- 目标端口号：0x0035 = 53（二进制表示：0000000000110101）\n- 数据字段长度：0x001C = 28（二进制表示：0000000000011100）\n- 剩余字段：0x7E21 = 19945（二进制表示：0101111000010001）\n\n根据以上分析：\n（1）源端口号：2044，目标端口号：53\n（2）数据字段长度：28字节\n（3）高层数据承载的网络服务是TCP/IP协议族中的用户数据报协议（UDP）\n（4）无法判断该报文的发送方是客户端还是服务器，因为源端口号只是标识了应用程序所在的端口，并不能明确区分客户端或服务器角色。\n\n因此，学生需要更详细地解释每个部分的内容。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (647763394166854, '学生回答提到“电平是否连续”，这是对模拟信号的一个特点的正确描述，但没有完整地解释数字信号和模拟信号的区别。完整的答案应该包括以下几个方面：\n1. 数字信号的特点是离散的电平，通常只有两种状态（如高电平和低电平）。\n2. 模拟信号的特点是连续的电平，可以取任何值，包括中间值。\n3. 数字信号适合传输数据和信息，而模拟信号则用于传输声音、图像等信号。\n4. 数字信号处理简单，抗干扰能力强；模拟信号处理复杂，易受干扰。\n\n请补充完整这些信息，以便获得更高的分数。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (648915002445893, '1. 题目要求列举多路复用技术，并简单描述其中一种，而学生只列出了四种技术。\n2. 学生没有提供对其中任何一种技术的具体描述，这使得回答不够全面和详细。\n3. 波分复用（WDM）是一种实际存在的技术，而其他三种则不属于多路复用技术。\n4. 对于频分复用（FDM），可以简要说明它是通过将信道划分为多个独立的频段来实现的。\n5. 对于时分复用（TDM），可以简要说明它是通过在时间上分割信号来实现多路传输的。\n6. 对于码分复用（CDM），可以简要说明它是利用不同的伪随机码来区分数据流的。\n7. 建议学生提供至少两种技术的具体描述，如频分复用和码分复用。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (649686155321414, '- 题目要求列举多路复用技术，并举例说明其中一种。\n- 学生的答案列举了四种技术（时分、波分、码分、频分），其中“码分”并非多路复用技术，而是编码方式的一种。\n- 对于其中一种技术的描述不够具体，例如，虽然提到了频分多路复用（FDM）的概念，但没有进一步解释其工作原理。\n- 建议补充更多关于频分多路复用的具体描述，如不同频率信道的分配方式、应用场合等。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (649686155321418, '学生的答案只提到了一个方面，即电平是否连续，但并没有全面解释数字信号和模拟信号的区别。正确的回答应该包括：模拟信号的电平是连续变化的，而数字信号的电平只能取有限的几个离散值。此外，还需要提到它们在传输、处理和接收方面的特点。\n\n# 系统时间\n```\n当前时间是 (东8区) 北京时间：2025年2月28日，星期五。\n```\n\n# 工具\n## 你可以在回复中插入零次、一次或多次以下命令以调用工具：\n根据我的知识库，我为您找到了关于数字信号与模拟信号区别的相关信息。模拟信号的电平是连续变化的，而数字信号的电平只能取有限的几个离散值。模拟信号容易受干扰并导致失真，而数字信号则具有抗干扰能力强的优点。模拟信号适合长距离传输，而数字信号更适合高速传输和实时数据处理。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (649691688058949, '- 题目要求列举多路复用技术并简单描述其中一种，而学生只列出了四种技术（时分、波分、码分、频分），并未进行任何描述。\n- 虽然时分、波分、频分是常见的多路复用技术，但码分并不是多路复用技术，而是属于码分复用。\n- 请补充完整并准确地描述其中一种技术。\n\n正确答案示例：\n多路复用技术主要有时分、波分、频分和码分。这里以频分复用为例进行简述。频分复用是利用不同的频率间隔来分配不同的信道，每个信道使用特定的频率范围传输数据。例如，在电视广播系统中，可以使用多个频道占用不同的频率段来同时传输多套电视节目。', NULL);
INSERT INTO `edu_aijudge_msg` VALUES (649691688058952, '学生的答案部分正确地指出了电平是否连续这一点，即模拟信号是连续变化的，而数字信号则是离散的。但是，答案没有完整表达出两种信号的本质区别。正确的答案应包括：1. 模拟信号可以取无限多个值，表示的是一个连续变化的过程；而数字信号只能取有限个离散值，代表的是一个离散的过程。2. 模拟信号容易受噪声干扰，而数字信号抗干扰能力强。3. 模拟信号的处理通常采用放大器，而数字信号则需要通过编码和解码来转换。\n# 系统时间\n```\n当前时间是 (东8区) 北京时间：2025年2月28日，星期五。\n```\n# 工具\n## 夸克搜索\nquark_search: 夸克搜索引擎。当你需要搜索你不知道的信息，比如天气、汇率、时事等，这个工具非常有用。但是绝对不要在用户想要翻译的时候使用它。\n输入参数：[{\"name\": \"query\", \"description\": \"\", \"required\": true, \"schema\": {\"type\": \"string\"}}]\n使用样例：\'问题：我是个科技爱好者，特别关注苹果公司的产品。最近听说苹果公司要发布新品，但我不确定具体的发布会时间。我想知道2023年苹果新品发布会是什么时候。.\n很抱歉，由于这是一个概念性的问题，目前我无法通过搜索获取相关的信息来验证学生的答案。但请参考我之前提供的点评和建议，完善你的答案。', NULL);

-- ----------------------------
-- Table structure for edu_chapter
-- ----------------------------
DROP TABLE IF EXISTS `edu_chapter`;
CREATE TABLE `edu_chapter`  (
  `chapter_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '章节id',
  `chapter_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '章节标题',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '章节所属科目id',
  `teacher_id` bigint NULL DEFAULT NULL COMMENT '创建教师id',
  `is_public` tinyint NULL DEFAULT NULL COMMENT '是否是公开章节(非创建者可见)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`chapter_id`) USING BTREE,
  INDEX `chapter_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `chapter_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程章节表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_chapter
-- ----------------------------
INSERT INTO `edu_chapter` VALUES (1, '引言1', 1000, 1, 0, '2025-02-12 22:23:55');
INSERT INTO `edu_chapter` VALUES (2, '计算机网络概论11', 1000, 1, 1, '2025-02-12 22:24:20');
INSERT INTO `edu_chapter` VALUES (3, '物理层1', 1000, 1, 1, '2025-02-12 22:24:34');
INSERT INTO `edu_chapter` VALUES (4, '数据链路层1', 1000, 1, 0, '2025-02-12 22:25:13');
INSERT INTO `edu_chapter` VALUES (5, '介质访问控制子层1', 1000, 1, 1, '2025-02-12 22:25:40');
INSERT INTO `edu_chapter` VALUES (6, '网络层1', 1000, 1, 0, '2025-02-12 22:25:55');
INSERT INTO `edu_chapter` VALUES (7, '传输层1', 1000, 1, 1, '2025-02-12 22:26:13');

-- ----------------------------
-- Table structure for edu_chapter_item
-- ----------------------------
DROP TABLE IF EXISTS `edu_chapter_item`;
CREATE TABLE `edu_chapter_item`  (
  `item_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `item_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目标题',
  `chapter_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '项目所属章节',
  `teacher_id` bigint NULL DEFAULT NULL COMMENT '创建教师id',
  `is_public` tinyint NOT NULL COMMENT '是否公开项目(非创建者可见)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`item_id`) USING BTREE,
  INDEX `item_chapter_id`(`chapter_id` ASC) USING BTREE,
  INDEX `item_create_time`(`create_time` ASC) USING BTREE,
  CONSTRAINT `item_chapter_id` FOREIGN KEY (`chapter_id`) REFERENCES `edu_chapter` (`chapter_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '章节项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_chapter_item
-- ----------------------------
INSERT INTO `edu_chapter_item` VALUES (1, '课程介绍', 1, 1, 0, '2025-02-12 22:32:38');
INSERT INTO `edu_chapter_item` VALUES (2, '计算机网络的形成与发展', 2, 1, 1, '2025-02-12 22:33:25');
INSERT INTO `edu_chapter_item` VALUES (3, '物理层与物理层协议的概念', 3, 1, 1, '2025-02-12 22:33:57');
INSERT INTO `edu_chapter_item` VALUES (4, '差错产生的原因与差错控制', 4, 1, 1, '2025-02-12 22:34:31');
INSERT INTO `edu_chapter_item` VALUES (5, '局域网技术的发展与演变', 5, 1, 1, '2025-02-12 22:35:09');
INSERT INTO `edu_chapter_item` VALUES (6, '网络层与ip协议', 6, 1, 1, '2025-02-12 22:35:38');
INSERT INTO `edu_chapter_item` VALUES (7, '传输层与传输层协议', 7, 1, 1, '2025-02-12 22:36:01');
INSERT INTO `edu_chapter_item` VALUES (8, '域名系统', NULL, 1, 1, '2025-02-12 22:36:15');
INSERT INTO `edu_chapter_item` VALUES (9, '课程文档', 1, 1, 0, '2025-02-13 03:27:17');
INSERT INTO `edu_chapter_item` VALUES (10, '基带传输技术', 3, 1, 1, '2025-02-13 03:28:00');
INSERT INTO `edu_chapter_item` VALUES (11, '实验文档', 1, 1, 0, '2025-02-13 09:23:10');
INSERT INTO `edu_chapter_item` VALUES (12, 'test', 1, 1, 0, '2025-03-16 10:40:16');
INSERT INTO `edu_chapter_item` VALUES (13, 'test2', 1, 1, 0, '2025-03-16 10:41:38');
INSERT INTO `edu_chapter_item` VALUES (14, 'test3', 1, 1, 0, NULL);
INSERT INTO `edu_chapter_item` VALUES (15, 'test4', 1, 1, 0, '2025-03-16 10:58:06');

-- ----------------------------
-- Table structure for edu_class
-- ----------------------------
DROP TABLE IF EXISTS `edu_class`;
CREATE TABLE `edu_class`  (
  `class_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `class_title` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级名称',
  `created` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2214504 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_class
-- ----------------------------
INSERT INTO `edu_class` VALUES (2214501, '软件工程(专升本)1班', '2025-03-05 20:36:23');
INSERT INTO `edu_class` VALUES (2214502, '软件工程(专升本)2班', '2025-02-09 21:28:37');
INSERT INTO `edu_class` VALUES (2214503, '软件工程(专升本)3班', '2025-03-05 20:36:07');

-- ----------------------------
-- Table structure for edu_course
-- ----------------------------
DROP TABLE IF EXISTS `edu_course`;
CREATE TABLE `edu_course`  (
  `course_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `subject_id` bigint UNSIGNED NOT NULL COMMENT '所属科目id',
  `class_id` bigint UNSIGNED NOT NULL COMMENT '所属班级id',
  `teacher_id` bigint UNSIGNED NOT NULL COMMENT '任课老师id',
  `course_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程封面url',
  `course_status` tinyint UNSIGNED NOT NULL COMMENT '课程状态1为开课0为结课',
  `create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `course_class_id`(`class_id` ASC) USING BTREE,
  INDEX `course_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `course_class_id` FOREIGN KEY (`class_id`) REFERENCES `edu_class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1011 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_course
-- ----------------------------
INSERT INTO `edu_course` VALUES (1, 1004, 2214502, 2, '/static/3', 1, '2025-02-11 15:23:20');
INSERT INTO `edu_course` VALUES (1005, 1000, 2214502, 1, '/static/1', 1, '2025-02-11 20:11:38');
INSERT INTO `edu_course` VALUES (1006, 1001, 2214502, 3, '/static/2', 1, '2025-02-11 20:11:57');
INSERT INTO `edu_course` VALUES (1007, 1002, 2214502, 4, NULL, 0, '2025-02-11 20:12:14');
INSERT INTO `edu_course` VALUES (1008, 1003, 2214502, 5, NULL, 0, '2025-02-11 20:12:30');
INSERT INTO `edu_course` VALUES (1009, 1000, 2214501, 1, '/static/1', 1, '2025-03-05 20:39:02');
INSERT INTO `edu_course` VALUES (1010, 1000, 2214503, 1, '/static/1', 1, '2025-03-05 20:39:52');

-- ----------------------------
-- Table structure for edu_fill_question
-- ----------------------------
DROP TABLE IF EXISTS `edu_fill_question`;
CREATE TABLE `edu_fill_question`  (
  `question_id` int NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `question` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '试题内容',
  `right_answer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '题目解析',
  `level` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '难度等级',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '科目id',
  `create_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '创建教师id',
  `update_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '最后一次修改教师id',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `fill_que_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `fill_que_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 30013 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '填空题题库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of edu_fill_question
-- ----------------------------
INSERT INTO `edu_fill_question` VALUES (10012, 'IP地址分()和()两个部分', '网络号主机号', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10013, ' IP地址协议作网间网中()层协议，提供无连接的数据报传输机制，IP数据报也分为()和()两个部分', '网络报头数据区', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10014, '()是一个简单的远程终端协议。', 'TELNET', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10015, '在同一个系统内，相邻层之间交换信息的连接点称之为()，而低层模块向高层提供功能性的支持称之为()。', '接口服务', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10016, 'Internet广泛使用的电子邮件传送协议是()', 'SMTP', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10017, '按交换方式来分类，计算机网络可以分为电路交换网，  报文交换网  和()三种', '分组交换网', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10018, 'Intranet分层结构包括网络、(),应用三个层次。', '服务', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10019, 'WWW上的每一个网页都有一个独立的地址，这些地址称为  ()', '统一资源定位器/URL ', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10020, '分组交换网中，附加信息用来在网络中进行路由选择、() 和流量控制', '差错纠正  ', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10021, '根据IEEE802模型的标准将数据链路层划分为LLC子层和 ()子层。', ' MAC ', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10022, '据交换的路由信息的不同，路由算法可以分为两大类：  ()  和链路状态算法', '距离向量算法', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10023, '假定某信道受奈氏准则限制的最高码元速率为2000码元/秒。如果采用振幅调制，把码元的振幅划分为16个不同等级来传送，那么可以获得的数据率为 () b/s。', '80000 ', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10024, '交换型以太网系统中的 ()  ，以其为核心联接站点或者网段，端口之间帧的输入和输出已不再受到CSMA/CD媒体访问控制协议的约束。', '以太网交换器 ', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10025, '局域网络参考模型是以 ()标准为基础的', 'IEEE802', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10026, '路由器的核心是 () 。', ' 路由表', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10027, '若 HDLC 帧数据段中出现比特串“ 01011111110 ”，则比特填充后的输出为()', '10111110110', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (10028, '数字调制的三种基本形式：移幅键控法ASK、 ()、移相键控法PSK', '移频键控法FSK', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30000, '从计算机网络系统组成的角度看，计算机网络可以分为()和()', '通信子网资源子网', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30001, '收发电子邮件，属于ISO/OSI RM中 ()层的功能。', '应用', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30002, '在TCP/IP层次模型中与OSI参考模型第四层相对应的主要协议有()和(),其中后者提供无连接的不可靠传输服', 'TCP（传输控制协议） UDP（用户数据报协议） ', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30003, '计算机网络中常用的三种有线媒体是 (),()和 ()', '同轴电缆.双绞线 光纤', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30004, '国内最早的四大网络包括原邮电部的ChinaNet. 原电子部的ChinaGBN. 教育部的()和中科院的CSTnet', 'CERnet (或中国教育科研网)', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30005, '复盖一个国家，地区或几个洲的计算机网络称为()，在同一建筑或复盖几公里内范围的网络称为()，而介于两者之间的是()', ' 广域网       局域网     城域网', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30006, 'Outlook等常用电子邮件软件接收邮件使用的协议是(),发送邮件时使用的协议是()', 'POP3    SMTP    ', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30007, '通信系统中，称调制前的电信号为()信号，调制后的信号为调制信号', '基带', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30008, '按照IPV4标准,IP地址205.3.127.13属于()类地址', 'C', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30009, '计算机网络采用()技术，而传统电话网络则采用()技术', '分组交换电路交换', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30010, '计算机内传输的信号是()，而公用电话系统的传输系统只能传输()', '数字信号模拟信号', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30011, '通信系统中，称调制前的电信号为()，调制后的信号叫()。', '基带信号调制信号', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_fill_question` VALUES (30012, 'test', 'T', 'test', '1', NULL, 1, NULL);

-- ----------------------------
-- Table structure for edu_judge_question
-- ----------------------------
DROP TABLE IF EXISTS `edu_judge_question`;
CREATE TABLE `edu_judge_question`  (
  `question_id` int NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  `question` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '试题内容',
  `right_answer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '题目解析',
  `level` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '难度等级',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '科目id',
  `create_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '创建教师id',
  `update_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '最后一次修改教师id',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `judge_que_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `judge_que_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20014 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '判断题题库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of edu_judge_question
-- ----------------------------
INSERT INTO `edu_judge_question` VALUES (20001, '与有线网相比,无线网的数据传输率一般相对较慢', 'T', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20002, 'OSI参考模型中,不同节点的同等层具有不同的功能', 'F', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20003, '普通电脑不能作为服务器', 'F', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20004, '没有网线的电脑不能连入互联网', 'F', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20005, '网卡必须安装驱动程序', 'T', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20006, 'UTP为屏蔽双绞线', 'F', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20007, '网络接口卡又称为网卡,它是构成网络的基本部件', 'T', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20008, '无线AP可以成倍地扩展网络覆盖范围', 'T', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20009, 'SMTP是一组用于由源地址到目的地址传送邮件的协议', 'T', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20010, '任务管理器可以关闭所有的进程', 'F', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20011, '利用BT下载时,用户越多,下载速度越快', 'T', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_judge_question` VALUES (20012, 'INTERNET上向朋友发送电子邮件,必须知道对方的真实姓名和家庭住址', 'F', NULL, '1', 1000, 1, NULL);

-- ----------------------------
-- Table structure for edu_markdown
-- ----------------------------
DROP TABLE IF EXISTS `edu_markdown`;
CREATE TABLE `edu_markdown`  (
  `markdown_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'markdownId',
  `markdown_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文档内容',
  PRIMARY KEY (`markdown_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'markdown资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_markdown
-- ----------------------------
INSERT INTO `edu_markdown` VALUES (1, '话说大诗人李白, 一生好饮。幸好他从不开车。\n一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:\n> 无事街上走，提显去打酒。 逢店加一倍, 遇花喝一斗。\n\n这一路上, 他一共遇到店N次, 遇到花M次。已知最后一次遇到的是花, 他正好把酒喝光了。\n\n请你计算李白这一路遇到店和花的顺序, 有多少种不同的可能?\n\n注意: 显里没酒 ( 0 斗) 时遇店是合法的, 加倍后还是没酒; 但是没酒时遇 花是不合法的。');
INSERT INTO `edu_markdown` VALUES (2, 'test');
INSERT INTO `edu_markdown` VALUES (3, '话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:');
INSERT INTO `edu_markdown` VALUES (4, '话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:话说大诗人李白, 一生好饮。幸好他从不开车。 一天, 他提着酒显, 从家里出来, 酒显中有酒 2 斗。他边走边唱:');

-- ----------------------------
-- Table structure for edu_multi_question
-- ----------------------------
DROP TABLE IF EXISTS `edu_multi_question`;
CREATE TABLE `edu_multi_question`  (
  `question_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  `question` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '问题题目',
  `answer_a` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '选项A',
  `answer_b` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '选项B',
  `answer_c` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '选项C',
  `answer_d` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '选项D',
  `right_answer` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '题目解析',
  `level` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '难度等级',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '科目id',
  `create_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '创建教师id',
  `update_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '最后一次修改教师id',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `multi_que_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `multi_que_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10045 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '选择题题库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of edu_multi_question
-- ----------------------------
INSERT INTO `edu_multi_question` VALUES (10000, 'DNS 服务器和DHCP服务器的作用是（）', '将IP地址翻译为计算机名，为客户机分配IP地址', '将IP地址翻译为计算机名、解析计算机的MAC地址', '将计算机名翻译为IP地址、为客户机分配IP地址', '将计算机名翻译为IP地址、解析计算机的MAC地址', 'C', NULL, '1', 1000, 1, 1);
INSERT INTO `edu_multi_question` VALUES (10001, 'HTTP协议通常使用什么协议进行传输（）', 'ARP', 'DHCP', 'UDP', 'TCP', 'D', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10003, '查看DNS缓存记录的命令（）', 'ipconfig/displaydns', 'nslookup', 'ipconfig/release', 'ipconfig/flushdns', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10004, 'DHCP(        )报文的目的IP地址为255.255.255.255', 'DhcpDisover', 'DhcpOffer', 'DhcpAck', 'DhcpNack', 'A', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10005, '下列地址中，（  ）不是DHCP服务器分配的IP地址', '196.254.109.100', '169.254.12.42', '69.254.48.45', '96.254.54.15', 'B', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10006, 'DHCP通常可以为客户端自动配置哪些网络参数（）', 'IP，掩码，网关，DNS', 'IP，掩码，域名，SMTP', '网关，掩码，浏览器，FTP', 'IP，网关，DNS，服务器', 'A', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10007, 'DNS服务器在名称解析过程中正确的查询顺序为（）', '本地缓存记录→区域记录→转发域名服务器→根域名服务器', '区域记录→本地缓存记录→转发域名服务器→根域名服务器', '本地缓存记录→区域记录→根域名服务器→转发域名服务器', '区域记录→本地缓存记录→根域名服务器→转发域名服务器', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10008, '在TCP/IP协议中，序号小于（  ）的端口称为熟知端口(well-known port)。', '1024', '64', '256', '128', 'A', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10009, '在Internet上用TCP/IP播放视频，想用传输层的最快协议，以减少时延，要使用（ ）', 'UDP协议的低开销特性', 'UDP协议的高开销特性', 'TCP协议的低开销特性', 'TCP协议的高开销特性', 'A', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10010, '在TCP协议中采用（ ）来区分不同的应用进程', '端口号', 'IP地址', '协议类型', 'MAC地址', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10011, '可靠的传输协议中的“可靠”指的是（ ）', '使用面向连接的会话', '使用“尽力而为”的传输', '使用滑动窗口来维持可靠性', '使用确认重传机制来确保传输的数据不丢失', 'D', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10012, '假设拥塞窗口为50KB，接收窗口为80KB，TCP能够发送的最大字节数为（ ）', '50KB', '80KB', '130KB', '30KB', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10013, '主机A向主机B发送一个（SYN=1，seq=2000）的TCP报文，期望与主机B建立连接，若主机B接受连接请求，则主机B发送的正确有TCP报文可能是（ ）', '（SYN=0,ACK=0,seq=2001,ack=2001）', '（SYN=1,ACK=1,seq=2000,ack=2000）', '•	C.（SYN=1,ACK=1,seq=2001,ack=2001）', '（SYN=0,ACK=1,seq=2000,ack=2000）', 'C', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10014, '主机A向主机B连续发送了两个TCP报文段，其序号分别为70和100。试问： （1）第一个报文段携带了（）个字节的数据？', ' 70', '30', '100', '170', 'B', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10015, 'PCM脉码调制的过程（ ）', '采样、量化、编码', '量化、编码、采样', '编码、量化、采样', '采样、编码、量化', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10016, '若某采用4相位调制的通信链路的数据传输速率为2400bps，则该链路的波特率为（）', '600Baud', '1200Baud', '4800Baud', '9600Baud', 'B', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10017, '以下关于数据传输速率的描述中，错误的是( )', '数据传输速率表示每秒钟传输构成数据代码的二进制比特数', '对于二进制数据，数据传输速率为S=1/T (bps)', '常用的数据传输速率单位有: 1Mbps=1.024×106bps', '数据传输速率是描述数据传输系统性能的重要技术指标之一', 'C', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10018, '以下关于时分多路复用概念的描述中，错误的是.(  ).', '时分多路复用将线路使用的时间分成多个时间片', '时分多路复用分为同步时分多路复用与统计时分多路复用', '时分多路复用使用“帧”与数据链路层“帧”的概念、作用是不同的', '统计时分多路复用将时间片预先分配给各个信道', 'D', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10019, '1000BASE-T标准支持的传输介质是（）', '双绞线', '同轴电缆', '光纤', '无线电', 'A', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10020, '一个以太网交换机，读取整个数据帧，对数据帧进行差错校验后再转发出去，这种交换方式称为 （）', '直通交换', '无碎片交换', '无差错交换', '存储转发交换', 'D', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10021, '关于VLAN，下面的描述中正确的是（）', '一个新的交换机没有配置VLAN', '通过配置VLAN减少了冲突域的数量', '一个VLAN不能跨越多个交换机', '各个VLAN属于不同的广播域', 'D', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10022, '以太网协议中使用物理地址作用是什么？', '.用于不同子网中的主机进行通信', '作为第二层设备的唯一标识', '用于区别第二层第三层的协议数据单元', '保存主机可检测未知的远程设备', 'B', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10023, '以太网采用的CSMA/CD协议，当冲突发生时要通过二进制指数后退算法计算后退延时， 关于这个算法，以下论述中错误的是 （）', '冲突次数越多，后退的时间越短', '平均后退次数的多少与负载大小有关', '后退时延的平均值与负载大小有关', '重发次数达到一定极限后放弃发送', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10024, '以下关于交换机获取与其端口连接设备的MAC地址的叙述中，正确的是（）', '交换机从路由表中提取设备的MAC地址', '交换机检查端口流入分组的源地址', '交换机之间互相交换地址表', '网络管理员手工输入设备的MAC地址', 'B', NULL, '2', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10025, '如果G (x）为11010010，以下4个CRC校验比特序列中只有哪个可能是正确的 ？', '1101011001', '101011011', '11011011', '1011001', 'B', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10026, '以下关于Ethernet物理地址的描述中，错误的是', 'Ethernet物理地址又叫做MAC地址', '48位的Ethernet物理地址允许分配的地址数达到247个', '网卡的物理地址写入主机的EPROM中', '每一块网卡的物理地址在全世界是唯一的', 'C', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10027, '下列帧类型中，不属于HDLC帧类型的是（）', '信息帧', '确认帧', '监控帧', '无编号帧', 'B', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10028, '通过交换机连接的一组站点，关于它们的广播域和冲突域说法正确的是（）', '组成一个冲突域，但不是一个广播域', '组成一个广播域，但不是一个冲突域', '组成一个冲突域，也是一个广播域', '既不一个冲突域，也不是一个广播域', 'B', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10029, '数据链路层的数据单位是（）', '帧', '字节', '比特', '分组', 'A', NULL, '1', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10030, 'LAN参考模型可分为物理层、（ ）', 'MAC，LLC等三层', 'LLC，MHS等三层', 'MAC，FTAM等三层', 'LLC，VT等三层', 'A', NULL, '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10031, '测试', 'A', 'B', 'C', 'D', 'B', '解析', '3', 1000, 1, NULL);
INSERT INTO `edu_multi_question` VALUES (10032, 'DNS 服务器和DHCP服务器的作用是（）', 'A', 'B', 'C', 'D', 'B', '哦解析', '2', 1000, 1, NULL);

-- ----------------------------
-- Table structure for edu_question_type
-- ----------------------------
DROP TABLE IF EXISTS `edu_question_type`;
CREATE TABLE `edu_question_type`  (
  `question_type_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `question_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名',
  PRIMARY KEY (`question_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '题目类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_question_type
-- ----------------------------
INSERT INTO `edu_question_type` VALUES (1, '选择题');
INSERT INTO `edu_question_type` VALUES (2, '判断题');
INSERT INTO `edu_question_type` VALUES (3, '填空题');
INSERT INTO `edu_question_type` VALUES (4, '主观题');

-- ----------------------------
-- Table structure for edu_release_test
-- ----------------------------
DROP TABLE IF EXISTS `edu_release_test`;
CREATE TABLE `edu_release_test`  (
  `course_id` bigint UNSIGNED NOT NULL COMMENT '课程id',
  `test_id` bigint UNSIGNED NOT NULL COMMENT '练习id',
  `end_time` datetime NULL DEFAULT NULL COMMENT '截止日期',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`course_id`, `test_id`) USING BTREE,
  INDEX `release_test_test_id`(`test_id` ASC) USING BTREE,
  CONSTRAINT `release_test_course_id` FOREIGN KEY (`course_id`) REFERENCES `edu_course` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `release_test_test_id` FOREIGN KEY (`test_id`) REFERENCES `edu_test` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程练习发布表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_release_test
-- ----------------------------
INSERT INTO `edu_release_test` VALUES (1005, 1, '2025-02-16 16:18:41', 1);
INSERT INTO `edu_release_test` VALUES (1005, 2, '2025-02-22 16:26:55', 1);
INSERT INTO `edu_release_test` VALUES (1005, 3, '2025-02-28 16:27:16', 1);
INSERT INTO `edu_release_test` VALUES (1005, 4, '2025-02-01 16:27:41', 0);
INSERT INTO `edu_release_test` VALUES (1005, 5, '2025-02-26 16:27:55', 1);
INSERT INTO `edu_release_test` VALUES (1005, 6, '2025-02-19 16:28:14', 1);
INSERT INTO `edu_release_test` VALUES (1005, 7, '2025-02-18 16:28:25', 1);
INSERT INTO `edu_release_test` VALUES (1005, 8, '2025-02-20 16:28:43', 1);
INSERT INTO `edu_release_test` VALUES (1005, 9, '2025-02-17 16:29:00', 1);
INSERT INTO `edu_release_test` VALUES (1005, 10, '2025-03-30 16:00:00', 1);
INSERT INTO `edu_release_test` VALUES (1009, 1, '2025-03-16 16:00:00', 1);
INSERT INTO `edu_release_test` VALUES (1010, 1, '2025-03-16 16:00:00', 1);

-- ----------------------------
-- Table structure for edu_resource_type
-- ----------------------------
DROP TABLE IF EXISTS `edu_resource_type`;
CREATE TABLE `edu_resource_type`  (
  `type_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_resource_type
-- ----------------------------
INSERT INTO `edu_resource_type` VALUES (1, 'media');
INSERT INTO `edu_resource_type` VALUES (2, 'Doc');
INSERT INTO `edu_resource_type` VALUES (3, 'markdown');
INSERT INTO `edu_resource_type` VALUES (4, 'image');

-- ----------------------------
-- Table structure for edu_resources
-- ----------------------------
DROP TABLE IF EXISTS `edu_resources`;
CREATE TABLE `edu_resources`  (
  `resource_id` bigint UNSIGNED NOT NULL COMMENT '资源id',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源url',
  `resource_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源名',
  `type_id` int UNSIGNED NULL DEFAULT NULL COMMENT '资源类型id',
  `item_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '资源所属id',
  PRIMARY KEY (`resource_id`) USING BTREE,
  INDEX `resource_type_id_1`(`type_id` ASC) USING BTREE,
  INDEX `resource_item_id`(`item_id` ASC) USING BTREE,
  CONSTRAINT `resource_item_id` FOREIGN KEY (`item_id`) REFERENCES `edu_chapter_item` (`item_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `resource_type_id_1` FOREIGN KEY (`type_id`) REFERENCES `edu_resource_type` (`type_id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_resources
-- ----------------------------
INSERT INTO `edu_resources` VALUES (2, '/static/testvudio.mp4', '演示', 1, 1);
INSERT INTO `edu_resources` VALUES (3, '/static/testimage.jpg', '图片', 4, 1);
INSERT INTO `edu_resources` VALUES (4, '1', 'markdown', 3, 1);
INSERT INTO `edu_resources` VALUES (656993239318597, '/static_resource/656993239318597.jpg', '微信图片_20250319093015', 4, 1);
INSERT INTO `edu_resources` VALUES (656993550942277, '/static_resource/656993550942277.pdf', '设计模式课件', 2, 1);
INSERT INTO `edu_resources` VALUES (657998181855301, '/static_resource/657998181855301.pdf', '我与人工智能', 2, 1);
INSERT INTO `edu_resources` VALUES (657998368227397, '/static_resource/657998368227397.pdf', '设计模式课件', 2, 1);
INSERT INTO `edu_resources` VALUES (658000934142021, '/static_resource/658000934142021.pdf', '设计模式课件', 2, 1);
INSERT INTO `edu_resources` VALUES (658002338078789, '/static_resource/658002338078789.pdf', '简答题答案', 2, 1);
INSERT INTO `edu_resources` VALUES (658002722492485, '/static_resource/658002722492485.pdf', '计算与画图答案', 2, 1);
INSERT INTO `edu_resources` VALUES (658002913001541, '/static_resource/658002913001541.pdf', '计算与画图答案', 2, 1);
INSERT INTO `edu_resources` VALUES (658002951491653, '/static_resource/658002951491653.pdf', '简答题', 2, 1);
INSERT INTO `edu_resources` VALUES (658003589627973, '/static_resource/658003589627973.pdf', '简答题', 2, 1);
INSERT INTO `edu_resources` VALUES (658003625689157, '/static_resource/658003625689157.pdf', '简答计算应用未完成', 2, 1);
INSERT INTO `edu_resources` VALUES (658003790245957, '/static_resource/658003790245957.pdf', '简答计算应用未完成', 2, 1);
INSERT INTO `edu_resources` VALUES (658004380536901, '/static_resource/658004380536901.pdf', '我与人工智能', 2, 1);

-- ----------------------------
-- Table structure for edu_subject
-- ----------------------------
DROP TABLE IF EXISTS `edu_subject`;
CREATE TABLE `edu_subject`  (
  `subject_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `subject_title` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名',
  `subject_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '课程描述',
  `director_id` bigint NOT NULL COMMENT '课程负责人id',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '科目封面',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`subject_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '科目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_subject
-- ----------------------------
INSERT INTO `edu_subject` VALUES (1000, '计算机网络', '计网', 1, '/static/1', '2025-02-09 21:27:15');
INSERT INTO `edu_subject` VALUES (1001, '计算机组成原理', '计组', 101, '/static/2', '2025-02-10 17:45:12');
INSERT INTO `edu_subject` VALUES (1002, '操作系统', '计操', 102, NULL, '2025-02-10 17:45:42');
INSERT INTO `edu_subject` VALUES (1003, '算法', '算法', 103, NULL, '2025-02-10 17:46:10');
INSERT INTO `edu_subject` VALUES (1004, '软件工程', '软件工程', 104, '/static/3', '2025-02-11 15:22:14');

-- ----------------------------
-- Table structure for edu_subjective_question
-- ----------------------------
DROP TABLE IF EXISTS `edu_subjective_question`;
CREATE TABLE `edu_subjective_question`  (
  `question_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `question` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '题目内容',
  `right_answer` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '正确答案',
  `analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '题目解析',
  `level` int UNSIGNED NULL DEFAULT NULL COMMENT '难度等级',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '科目id',
  `create_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '创建教师id',
  `update_teacher_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '最后一次修改教师id',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `subjective_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `subjective_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 400007 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_subjective_question
-- ----------------------------
INSERT INTO `edu_subjective_question` VALUES (400001, '.数字信号与模拟信号的区别是什么？', NULL, NULL, 1, 1000, 1, NULL);
INSERT INTO `edu_subjective_question` VALUES (400002, '多路复用技术有哪些？请例举出来，并简单描述其中一种。', NULL, NULL, 1, 1000, 1, NULL);
INSERT INTO `edu_subjective_question` VALUES (400003, '如果数据字段为1110111，生成多项式G(X)=X3+X2+X+1,请计算CRC，并且写出发送的比特序列', NULL, NULL, 1, 1000, 1, NULL);
INSERT INTO `edu_subjective_question` VALUES (400004, '已知主机A和B之间传输介质的长度是2000km,电磁波传播速度为 m/s，主机A向主机B发送数据大小为100M，数据发送的速率为500kb/s,请计算发送延时和传播延时。', NULL, NULL, 1, 1000, 1, NULL);
INSERT INTO `edu_subjective_question` VALUES (400005, '已知UDP报头用16进制数表示为06320035 001CE217,请计算以下内容： （1）该报文的源端口号和目标端口号； （2）数据字段长度是多少； （3）高层数据承载的网络服务是什么； （4）该报文的发送方是客户端还是服务器。', NULL, NULL, 1, 1000, 1, NULL);

-- ----------------------------
-- Table structure for edu_task
-- ----------------------------
DROP TABLE IF EXISTS `edu_task`;
CREATE TABLE `edu_task`  (
  `course_id` bigint UNSIGNED NOT NULL COMMENT '课程id',
  `chapter_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '章节id',
  `chapter_idx` int NOT NULL COMMENT '章节序号',
  `item_id` bigint UNSIGNED NOT NULL COMMENT '项目id',
  `item_idx` int NOT NULL COMMENT '项目序号',
  PRIMARY KEY (`course_id`, `item_id`) USING BTREE,
  INDEX `task_chapter_id`(`chapter_id` ASC) USING BTREE,
  INDEX `task_item_id`(`item_id` ASC) USING BTREE,
  CONSTRAINT `task_chapter_id` FOREIGN KEY (`chapter_id`) REFERENCES `edu_chapter` (`chapter_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `task_course_id` FOREIGN KEY (`course_id`) REFERENCES `edu_course` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `task_item_id` FOREIGN KEY (`item_id`) REFERENCES `edu_chapter_item` (`item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程任务表(课程-章节-项目映射关系)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_task
-- ----------------------------
INSERT INTO `edu_task` VALUES (1005, 1, 9, 1, 0);
INSERT INTO `edu_task` VALUES (1005, 2, 2, 2, 1);
INSERT INTO `edu_task` VALUES (1005, 3, 3, 3, 1);
INSERT INTO `edu_task` VALUES (1005, 4, 4, 4, 1);
INSERT INTO `edu_task` VALUES (1005, 5, 5, 5, 1);
INSERT INTO `edu_task` VALUES (1005, 6, 6, 6, 1);
INSERT INTO `edu_task` VALUES (1005, 1, 9, 9, 1);
INSERT INTO `edu_task` VALUES (1005, 3, 3, 10, 2);
INSERT INTO `edu_task` VALUES (1005, 1, 9, 11, 2);
INSERT INTO `edu_task` VALUES (1009, 1, 1, 1, 0);
INSERT INTO `edu_task` VALUES (1009, 1, 1, 9, 1);
INSERT INTO `edu_task` VALUES (1009, 1, 1, 11, 2);

-- ----------------------------
-- Table structure for edu_teacher_info
-- ----------------------------
DROP TABLE IF EXISTS `edu_teacher_info`;
CREATE TABLE `edu_teacher_info`  (
  `t_uid` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `t_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师用户名',
  `t_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师密码',
  `t_realname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师姓名',
  `t_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师邮箱',
  `t_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`t_uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_teacher_info
-- ----------------------------
INSERT INTO `edu_teacher_info` VALUES (1, 'admin', '123456', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for edu_teacher_link_subject
-- ----------------------------
DROP TABLE IF EXISTS `edu_teacher_link_subject`;
CREATE TABLE `edu_teacher_link_subject`  (
  `t_uid` bigint UNSIGNED NOT NULL COMMENT '教师id',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '科目id',
  INDEX `link_subject_t_uid`(`t_uid` ASC) USING BTREE,
  INDEX `link_subject_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `link_subject_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `link_subject_t_uid` FOREIGN KEY (`t_uid`) REFERENCES `edu_teacher_info` (`t_uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_teacher_link_subject
-- ----------------------------
INSERT INTO `edu_teacher_link_subject` VALUES (1, 1000);
INSERT INTO `edu_teacher_link_subject` VALUES (1, 1001);
INSERT INTO `edu_teacher_link_subject` VALUES (1, 1002);
INSERT INTO `edu_teacher_link_subject` VALUES (1, 1003);
INSERT INTO `edu_teacher_link_subject` VALUES (1, 1004);

-- ----------------------------
-- Table structure for edu_test
-- ----------------------------
DROP TABLE IF EXISTS `edu_test`;
CREATE TABLE `edu_test`  (
  `test_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '练习id',
  `test_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '练习名称',
  `test_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '练习介绍',
  `subject_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '练习所属科目',
  `teacher_id` bigint NOT NULL COMMENT '创建教师id',
  `is_public` tinyint NULL DEFAULT NULL COMMENT '是否公开可见',
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `test_subject_id`(`subject_id` ASC) USING BTREE,
  CONSTRAINT `test_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `edu_subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程练习表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_test
-- ----------------------------
INSERT INTO `edu_test` VALUES (1, '第一章作业', '第一章作业1', 1000, 1, 1);
INSERT INTO `edu_test` VALUES (2, '第二章作业', '先完成第一章', 1000, 1, 1);
INSERT INTO `edu_test` VALUES (3, '第三章作业', '最先完成平时分加10分', 1000, 1, 1);
INSERT INTO `edu_test` VALUES (4, '第四章', NULL, 1000, 1, 1);
INSERT INTO `edu_test` VALUES (5, '第五章', NULL, 1000, 1, 1);
INSERT INTO `edu_test` VALUES (6, '第六章', NULL, 1000, 2, 0);
INSERT INTO `edu_test` VALUES (7, '第七章', NULL, 1000, 1, 1);
INSERT INTO `edu_test` VALUES (8, '第八章', NULL, 1000, 2, 1);
INSERT INTO `edu_test` VALUES (9, '第九章', NULL, 1000, 1, 1);
INSERT INTO `edu_test` VALUES (10, '第十章', NULL, 1000, 1, 1);
INSERT INTO `edu_test` VALUES (11, '第六章', NULL, 1000, 1, 0);
INSERT INTO `edu_test` VALUES (12, '第八章', NULL, 1000, 1, 1);
INSERT INTO `edu_test` VALUES (13, 'test', 'test', 1000, 1, 0);

-- ----------------------------
-- Table structure for edu_test_commit
-- ----------------------------
DROP TABLE IF EXISTS `edu_test_commit`;
CREATE TABLE `edu_test_commit`  (
  `commit_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '提交id',
  `score` float UNSIGNED NULL DEFAULT NULL COMMENT '提交得分(为null时代表已提交但老师手动改卷)',
  `s_uid` bigint UNSIGNED NULL DEFAULT NULL COMMENT '学生id',
  `test_id` bigint UNSIGNED NOT NULL COMMENT '提交所属测试id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  PRIMARY KEY (`commit_id`) USING BTREE,
  INDEX `commit_test_id`(`test_id` ASC) USING BTREE,
  INDEX `commit_s_uid`(`s_uid` ASC) USING BTREE,
  CONSTRAINT `commit_s_uid` FOREIGN KEY (`s_uid`) REFERENCES `stu_info` (`s_uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `commit_test_id` FOREIGN KEY (`test_id`) REFERENCES `edu_test` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程练习提交表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_test_commit
-- ----------------------------
INSERT INTO `edu_test_commit` VALUES (6, 0, 100000009, 1, '2025-02-20 21:00:08');
INSERT INTO `edu_test_commit` VALUES (7, NULL, 100000009, 1, '2025-02-22 21:38:12');
INSERT INTO `edu_test_commit` VALUES (8, 21, 100000009, 1, '2025-02-22 21:45:05');
INSERT INTO `edu_test_commit` VALUES (9, 3, 100000009, 1, '2025-02-22 21:50:46');
INSERT INTO `edu_test_commit` VALUES (10, 0, 100000009, 1, '2025-02-22 21:53:45');
INSERT INTO `edu_test_commit` VALUES (11, 0, 100000009, 1, '2025-02-22 21:55:32');
INSERT INTO `edu_test_commit` VALUES (12, 0, 100000009, 1, '2025-02-22 22:00:01');
INSERT INTO `edu_test_commit` VALUES (13, NULL, 100000009, 1, '2025-02-22 22:20:28');
INSERT INTO `edu_test_commit` VALUES (14, 2, 100000009, 1, '2025-02-22 22:22:21');
INSERT INTO `edu_test_commit` VALUES (15, 0, 100000009, 1, '2025-02-22 22:31:23');
INSERT INTO `edu_test_commit` VALUES (16, NULL, 100000009, 1, '2025-02-23 10:27:41');
INSERT INTO `edu_test_commit` VALUES (17, 2, 100000009, 1, '2025-02-23 10:28:25');
INSERT INTO `edu_test_commit` VALUES (18, NULL, 100000009, 1, '2025-02-23 10:33:37');
INSERT INTO `edu_test_commit` VALUES (19, 5, 100000009, 1, '2025-02-23 10:35:01');
INSERT INTO `edu_test_commit` VALUES (20, 3, 100000009, 1, '2025-02-23 10:35:52');
INSERT INTO `edu_test_commit` VALUES (21, 52, 100000009, 1, '2025-02-23 11:36:01');
INSERT INTO `edu_test_commit` VALUES (22, NULL, 100000009, 1, '2025-02-26 17:26:45');
INSERT INTO `edu_test_commit` VALUES (23, NULL, 100000009, 1, '2025-02-26 17:30:09');
INSERT INTO `edu_test_commit` VALUES (24, 1, 100000009, 1, '2025-02-26 17:41:55');
INSERT INTO `edu_test_commit` VALUES (25, 8, 100000009, 1, '2025-02-28 21:59:45');
INSERT INTO `edu_test_commit` VALUES (26, 10, 100000009, 1, '2025-02-28 22:22:16');

-- ----------------------------
-- Table structure for edu_test_paper
-- ----------------------------
DROP TABLE IF EXISTS `edu_test_paper`;
CREATE TABLE `edu_test_paper`  (
  `test_id` bigint UNSIGNED NOT NULL COMMENT '练习id',
  `question_idx` int NOT NULL COMMENT '题目编号',
  `question_type` int UNSIGNED NOT NULL COMMENT '题目类型',
  `question_id` int NOT NULL COMMENT '题目id',
  `question_score` int UNSIGNED NOT NULL COMMENT '题目分值',
  PRIMARY KEY (`test_id`, `question_id`) USING BTREE,
  INDEX `paper_question_type`(`question_type` ASC) USING BTREE,
  CONSTRAINT `paper_question_type` FOREIGN KEY (`question_type`) REFERENCES `edu_question_type` (`question_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `paper_test_id` FOREIGN KEY (`test_id`) REFERENCES `edu_test` (`test_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程练习卷表(练习-题目关系)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_test_paper
-- ----------------------------
INSERT INTO `edu_test_paper` VALUES (1, 1, 1, 10001, 3);
INSERT INTO `edu_test_paper` VALUES (1, 3, 1, 10003, 3);
INSERT INTO `edu_test_paper` VALUES (1, 4, 1, 10004, 3);
INSERT INTO `edu_test_paper` VALUES (1, 5, 1, 10005, 3);
INSERT INTO `edu_test_paper` VALUES (1, 6, 1, 10006, 3);
INSERT INTO `edu_test_paper` VALUES (1, 7, 1, 10007, 3);
INSERT INTO `edu_test_paper` VALUES (1, 8, 1, 10008, 3);
INSERT INTO `edu_test_paper` VALUES (1, 9, 1, 10009, 3);
INSERT INTO `edu_test_paper` VALUES (1, 2, 1, 10010, 3);
INSERT INTO `edu_test_paper` VALUES (1, 10, 1, 10011, 0);
INSERT INTO `edu_test_paper` VALUES (1, 11, 2, 20001, 3);
INSERT INTO `edu_test_paper` VALUES (1, 12, 2, 20002, 3);
INSERT INTO `edu_test_paper` VALUES (1, 13, 2, 20003, 3);
INSERT INTO `edu_test_paper` VALUES (1, 14, 2, 20004, 3);
INSERT INTO `edu_test_paper` VALUES (1, 15, 2, 20005, 3);
INSERT INTO `edu_test_paper` VALUES (1, 16, 2, 20006, 3);
INSERT INTO `edu_test_paper` VALUES (1, 17, 2, 20007, 3);
INSERT INTO `edu_test_paper` VALUES (1, 18, 2, 20008, 3);
INSERT INTO `edu_test_paper` VALUES (1, 19, 2, 20009, 3);
INSERT INTO `edu_test_paper` VALUES (1, 20, 2, 20010, 3);
INSERT INTO `edu_test_paper` VALUES (1, 21, 3, 30000, 4);
INSERT INTO `edu_test_paper` VALUES (1, 22, 3, 30001, 4);
INSERT INTO `edu_test_paper` VALUES (1, 23, 3, 30002, 4);
INSERT INTO `edu_test_paper` VALUES (1, 24, 3, 30003, 4);
INSERT INTO `edu_test_paper` VALUES (1, 25, 3, 30004, 4);
INSERT INTO `edu_test_paper` VALUES (1, 26, 3, 30005, 4);
INSERT INTO `edu_test_paper` VALUES (1, 27, 3, 30006, 4);
INSERT INTO `edu_test_paper` VALUES (1, 28, 3, 30007, 4);
INSERT INTO `edu_test_paper` VALUES (1, 29, 3, 30008, 4);
INSERT INTO `edu_test_paper` VALUES (1, 30, 3, 30009, 4);
INSERT INTO `edu_test_paper` VALUES (1, 31, 4, 400001, 5);
INSERT INTO `edu_test_paper` VALUES (1, 32, 4, 400002, 5);
INSERT INTO `edu_test_paper` VALUES (1, 33, 4, 400003, 5);
INSERT INTO `edu_test_paper` VALUES (1, 34, 4, 400004, 5);
INSERT INTO `edu_test_paper` VALUES (1, 35, 4, 400005, 5);

-- ----------------------------
-- Table structure for stu_info
-- ----------------------------
DROP TABLE IF EXISTS `stu_info`;
CREATE TABLE `stu_info`  (
  `s_uid` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `s_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '学生用户名',
  `s_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '学生密码',
  `s_realname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `s_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `s_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `s_school` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校',
  `s_course` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `s_num` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `s_sex` tinyint NULL DEFAULT NULL COMMENT '性别',
  `s_age` tinyint NULL DEFAULT NULL COMMENT '年龄',
  `s_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`s_uid`) USING BTREE,
  UNIQUE INDEX `nickname`(`s_nickname` ASC) USING BTREE COMMENT '用户名',
  UNIQUE INDEX `num`(`s_num` ASC) USING BTREE COMMENT '学号',
  UNIQUE INDEX `email`(`s_email` ASC) USING BTREE COMMENT '邮箱'
) ENGINE = InnoDB AUTO_INCREMENT = 100000015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_info
-- ----------------------------
INSERT INTO `stu_info` VALUES (100000, 'test', 'test', NULL, NULL, NULL, NULL, NULL, '111', NULL, NULL, NULL, '2025-01-28 18:19:09', '2025-02-08 16:31:01');
INSERT INTO `stu_info` VALUES (100000009, '小皓', 'e10adc3949ba59abbe56e057f20f883e', '梁竣皓', NULL, 'l1789951582@gmail.com', NULL, NULL, '221450216', 0, NULL, NULL, '2025-02-09 16:46:38', '2025-02-25 17:19:39');
INSERT INTO `stu_info` VALUES (100000010, 'test1', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, 'wch2804@163.com', NULL, NULL, NULL, 0, NULL, NULL, '2025-02-10 22:55:54', '2025-02-10 22:55:54');
INSERT INTO `stu_info` VALUES (100000011, '222', '25f9e794323b453885f5181f1b624d0b', NULL, NULL, 'cfis@foxmail.com', NULL, NULL, NULL, 0, NULL, NULL, '2025-02-11 15:09:04', '2025-02-11 15:09:04');
INSERT INTO `stu_info` VALUES (100000014, 'aa', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, '1789951582@qq.com', NULL, NULL, NULL, 0, NULL, NULL, '2025-02-28 22:18:53', '2025-02-28 22:18:53');

-- ----------------------------
-- Table structure for stu_link_class
-- ----------------------------
DROP TABLE IF EXISTS `stu_link_class`;
CREATE TABLE `stu_link_class`  (
  `s_uid` bigint UNSIGNED NULL DEFAULT NULL COMMENT '学生id',
  `class_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '班级id',
  INDEX `link_class_id`(`class_id` ASC) USING BTREE,
  INDEX `link_s_id`(`s_uid` ASC) USING BTREE,
  CONSTRAINT `link_class_id` FOREIGN KEY (`class_id`) REFERENCES `edu_class` (`class_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `link_s_id` FOREIGN KEY (`s_uid`) REFERENCES `stu_info` (`s_uid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生-班级关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_link_class
-- ----------------------------
INSERT INTO `stu_link_class` VALUES (100000, 2214502);
INSERT INTO `stu_link_class` VALUES (100000009, 2214502);
INSERT INTO `stu_link_class` VALUES (100000010, 2214502);
INSERT INTO `stu_link_class` VALUES (100000011, 2214502);

-- ----------------------------
-- Table structure for stu_test_answer
-- ----------------------------
DROP TABLE IF EXISTS `stu_test_answer`;
CREATE TABLE `stu_test_answer`  (
  `answer_id` bigint NOT NULL,
  `answer` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `score` double UNSIGNED NULL DEFAULT NULL,
  `test_id` bigint UNSIGNED NOT NULL,
  `commit_id` bigint UNSIGNED NULL DEFAULT NULL,
  `question_id` bigint UNSIGNED NOT NULL,
  `create` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`answer_id`) USING BTREE,
  INDEX `answer_test_id`(`test_id` ASC) USING BTREE,
  INDEX `answer_commit_id`(`commit_id` ASC) USING BTREE,
  CONSTRAINT `answer_commit_id` FOREIGN KEY (`commit_id`) REFERENCES `edu_test_commit` (`commit_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `answer_test_id` FOREIGN KEY (`test_id`) REFERENCES `edu_test` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生提交的答案' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_test_answer
-- ----------------------------
INSERT INTO `stu_test_answer` VALUES (647560580546629, 'A', 3, 1, 9, 10010, '2025-02-22 21:50:46');
INSERT INTO `stu_test_answer` VALUES (647560580550725, 'B', 0, 1, 9, 10001, '2025-02-22 21:50:46');
INSERT INTO `stu_test_answer` VALUES (647560580550726, '时分，波分，码分', 0, 1, 9, 400002, '2025-02-22 21:50:47');
INSERT INTO `stu_test_answer` VALUES (647560580550727, 'B', 0, 1, 9, 10003, '2025-02-22 21:50:46');
INSERT INTO `stu_test_answer` VALUES (647560580550728, '电平是否连续', 0, 1, 9, 400001, '2025-02-22 21:50:47');
INSERT INTO `stu_test_answer` VALUES (647560580550729, 'A', 0, 1, 9, 10000, '2025-02-22 21:50:46');
INSERT INTO `stu_test_answer` VALUES (647560580558917, '不会', 0, 1, 9, 400003, '2025-02-22 21:50:47');
INSERT INTO `stu_test_answer` VALUES (647560580558918, 'B', 0, 1, 9, 10004, '2025-02-22 21:50:46');
INSERT INTO `stu_test_answer` VALUES (647561314922565, '电平是否连续', 0, 1, 10, 400001, '2025-02-22 21:53:46');
INSERT INTO `stu_test_answer` VALUES (647561314922566, '时分，频分，波分，码分', 0, 1, 10, 400002, '2025-02-22 21:53:46');
INSERT INTO `stu_test_answer` VALUES (647561753174085, '电平是否连续', 0, 1, 11, 400001, '2025-02-22 21:55:33');
INSERT INTO `stu_test_answer` VALUES (647561753174086, '时分，频分，波分，码分', 0, 1, 11, 400002, '2025-02-22 21:55:33');
INSERT INTO `stu_test_answer` VALUES (647562902310981, '电平是否连续', 0, 1, 12, 400001, '2025-02-22 22:18:56');
INSERT INTO `stu_test_answer` VALUES (647567895384133, '时分多路复用，频分多路复用，波分多路复用，码分多路复用', 2, 1, 13, 400002, '2025-02-22 22:22:14');
INSERT INTO `stu_test_answer` VALUES (647568346042437, '时分多路复用，频分多路复用，波分多路复用，码分多路复用', 2, 1, 14, 400002, '2025-02-22 22:30:59');
INSERT INTO `stu_test_answer` VALUES (647568346042438, '电平是否连续', 0, 1, 14, 400001, '2025-02-22 22:30:59');
INSERT INTO `stu_test_answer` VALUES (647570561822789, '时分波分码分', 0, 1, 15, 400002, '2025-02-22 22:49:47');
INSERT INTO `stu_test_answer` VALUES (647746781941829, '时分多路复用，波分多路复用，频分多路复用，码分多路复用', 2, 1, 17, 400002, '2025-02-23 10:32:23');
INSERT INTO `stu_test_answer` VALUES (647748057522245, '时分多路复用，波分多路复用，码分多路复用，频分多路复用', 2, 1, 18, 400002, '2025-02-23 10:33:50');
INSERT INTO `stu_test_answer` VALUES (647748399415365, '时分多路复用，波分多路复用，码分多路复用，频分多路复用', 2, 1, 19, 400002, '2025-02-23 10:35:05');
INSERT INTO `stu_test_answer` VALUES (647748399415366, '电平是否连续', 2.5, 1, 19, 400001, '2025-02-23 10:35:03');
INSERT INTO `stu_test_answer` VALUES (647748610445381, '电平是否连续', 2.5, 1, 20, 400001, '2025-02-23 10:37:54');
INSERT INTO `stu_test_answer` VALUES (647763394052165, 'T', 3, 1, 21, 20007, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394052166, 'ChinaEDU', 0, 1, 21, 30004, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394052167, 'F', 3, 1, 21, 20002, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394052168, 'A', 3, 1, 21, 10010, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060357, 'F', 3, 1, 21, 20004, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060358, 'tcp和udp', 0, 1, 21, 30002, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060359, 'F', 0, 1, 21, 20001, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060360, 'T', 3, 1, 21, 20005, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060361, 'C', 0, 1, 21, 10005, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060362, '时分多路复用波分多路复用码分多路复用，频分多路复用', 2, 1, 21, 400002, '2025-02-23 11:36:04');
INSERT INTO `stu_test_answer` VALUES (647763394060363, 'A', 3, 1, 21, 10008, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060364, 'F', 3, 1, 21, 20010, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060365, '发送延时 200s \n传播延时= 0.01s \n', 2.5, 1, 21, 400004, '2025-02-23 11:36:07');
INSERT INTO `stu_test_answer` VALUES (647763394060366, 'F', 3, 1, 21, 20003, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394060367, 'c', 0, 1, 21, 30008, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394097221, 'T', 0, 1, 21, 20006, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394101317, '广域网，局域网，城域网', 0, 1, 21, 30005, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394109509, 'A', 3, 1, 21, 10009, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394113605, 'T', 3, 1, 21, 20009, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394121797, 'A', 3, 1, 21, 10003, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394125893, '802', 0, 1, 21, 30009, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394134085, '生成多项式11001(N=5,k=4)\n将发送数据比特序列扩大24，那么f(x).xk=110011 0000 \n最终发送数据为：110011 1001', 0, 1, 21, 400003, '2025-02-23 11:36:07');
INSERT INTO `stu_test_answer` VALUES (647763394138181, '应用层', 0, 1, 21, 30001, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394142277, 'A', 3, 1, 21, 10007, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394146373, '不会', 0, 1, 21, 400005, '2025-02-23 11:36:08');
INSERT INTO `stu_test_answer` VALUES (647763394150469, 'A', 3, 1, 21, 10006, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394154565, 'C', 0, 1, 21, 10004, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394158661, '通信子网和资源子网', 0, 1, 21, 30000, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394166853, 'T', 3, 1, 21, 20008, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394166854, '电平是否连续', 2.5, 1, 21, 400001, '2025-02-23 11:36:05');
INSERT INTO `stu_test_answer` VALUES (647763394170949, '同轴电缆，双绞线，光纤', 0, 1, 21, 30003, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394183237, '数字信号', 0, 1, 21, 30007, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394187333, 'D', 3, 1, 21, 10001, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394187334, '不知道', 0, 1, 21, 30006, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (647763394195525, 'A', 0, 1, 21, 10000, '2025-02-23 11:36:01');
INSERT INTO `stu_test_answer` VALUES (648915002445893, '时分，波分，码分，频分', 1, 1, 24, 400002, '2025-02-26 17:42:00');
INSERT INTO `stu_test_answer` VALUES (649686155321413, 'T', 0, 1, 25, 20002, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321414, '时分，波分，码分，频分', 2, 1, 25, 400002, '2025-02-28 21:59:49');
INSERT INTO `stu_test_answer` VALUES (649686155321415, 'B', 0, 1, 25, 10004, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321416, 'A', 0, 1, 25, 10000, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321417, '解调信号', 0, 1, 25, 30007, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321418, '电平是否连续', 2.5, 1, 25, 400001, '2025-02-28 21:59:51');
INSERT INTO `stu_test_answer` VALUES (649686155321419, 'B', 0, 1, 25, 10001, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321420, 'C', 0, 1, 25, 10006, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321421, 'T', 0, 1, 25, 20004, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321422, 'B', 3, 1, 25, 10005, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321423, 'C', 0, 1, 25, 10010, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155321424, '应用层', 0, 1, 25, 30001, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649686155337797, 'B', 0, 1, 25, 10003, '2025-02-28 21:59:46');
INSERT INTO `stu_test_answer` VALUES (649691688054853, '应用层', 0, 1, 26, 30001, '2025-02-28 22:22:16');
INSERT INTO `stu_test_answer` VALUES (649691688054854, 'F', 0, 1, 26, 20001, '2025-02-28 22:22:16');
INSERT INTO `stu_test_answer` VALUES (649691688058949, '时分，波分，码分，频分', 1, 1, 26, 400002, '2025-02-28 22:22:20');
INSERT INTO `stu_test_answer` VALUES (649691688058950, 'A', 0, 1, 26, 10000, '2025-02-28 22:22:16');
INSERT INTO `stu_test_answer` VALUES (649691688058951, 'F', 3, 1, 26, 20002, '2025-02-28 22:22:16');
INSERT INTO `stu_test_answer` VALUES (649691688058952, '电平是否连续', 2.5, 1, 26, 400001, '2025-02-28 22:22:24');
INSERT INTO `stu_test_answer` VALUES (649691688058953, '资源子网', 0, 1, 26, 30000, '2025-02-28 22:22:16');
INSERT INTO `stu_test_answer` VALUES (649691688063045, 'D', 3, 1, 26, 10001, '2025-02-28 22:22:16');

-- ----------------------------
-- View structure for stu_course_vo
-- ----------------------------
DROP VIEW IF EXISTS `stu_course_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `stu_course_vo` AS select `stu_link_class`.`s_uid` AS `s_uid`,`edu_course`.`course_id` AS `course_id`,`edu_subject`.`subject_title` AS `subject_title`,`edu_subject`.`subject_description` AS `subject_description`,`edu_course`.`course_cover` AS `course_cover`,`edu_course`.`course_status` AS `course_status`,`edu_course`.`create` AS `create` from (((`stu_link_class` join `edu_class` on((`stu_link_class`.`class_id` = `edu_class`.`class_id`))) join `edu_course` on((`edu_class`.`class_id` = `edu_course`.`class_id`))) join `edu_subject` on((`edu_course`.`subject_id` = `edu_subject`.`subject_id`)));

-- ----------------------------
-- View structure for stu_task_vo
-- ----------------------------
DROP VIEW IF EXISTS `stu_task_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `stu_task_vo` AS select `edu_task`.`course_id` AS `course_id`,`edu_task`.`chapter_id` AS `chapter_id`,`edu_task`.`chapter_idx` AS `chapter_idx`,`edu_chapter`.`chapter_title` AS `chapter_title`,`edu_task`.`item_id` AS `item_id`,`edu_task`.`item_idx` AS `item_idx`,`edu_chapter_item`.`item_title` AS `item_title` from ((`edu_task` join `edu_chapter` on((`edu_task`.`chapter_id` = `edu_chapter`.`chapter_id`))) join `edu_chapter_item` on(((`edu_chapter`.`chapter_id` = `edu_chapter_item`.`chapter_id`) and (`edu_task`.`item_id` = `edu_chapter_item`.`item_id`))));

-- ----------------------------
-- View structure for stu_test_vo
-- ----------------------------
DROP VIEW IF EXISTS `stu_test_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `stu_test_vo` AS select `edu_release_test`.`course_id` AS `course_id`,`edu_release_test`.`test_id` AS `test_id`,`edu_test`.`test_title` AS `test_title`,`edu_test`.`test_description` AS `test_description`,`edu_release_test`.`end_time` AS `end_time`,`edu_release_test`.`status` AS `status` from (`edu_test` join `edu_release_test` on((`edu_test`.`test_id` = `edu_release_test`.`test_id`)));

-- ----------------------------
-- View structure for tch_course_vo
-- ----------------------------
DROP VIEW IF EXISTS `tch_course_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tch_course_vo` AS select `edu_course`.`course_id` AS `course_id`,`edu_class`.`class_title` AS `class_title`,`edu_course`.`subject_id` AS `subject_id`,`edu_course`.`teacher_id` AS `teacher_id`,`edu_course`.`course_status` AS `course_status` from (`edu_course` join `edu_class` on((`edu_course`.`class_id` = `edu_class`.`class_id`)));

-- ----------------------------
-- View structure for tch_item_vo
-- ----------------------------
DROP VIEW IF EXISTS `tch_item_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tch_item_vo` AS select `edu_subject`.`subject_id` AS `subject_id`,`edu_chapter`.`chapter_id` AS `chapter_id`,`edu_chapter_item`.`item_id` AS `item_id`,`edu_chapter_item`.`item_title` AS `item_title`,`edu_chapter_item`.`teacher_id` AS `teacher_id`,`edu_chapter_item`.`is_public` AS `is_public`,`edu_chapter_item`.`create_time` AS `create_time` from ((`edu_chapter` join `edu_chapter_item` on((`edu_chapter`.`chapter_id` = `edu_chapter_item`.`chapter_id`))) join `edu_subject` on((`edu_chapter`.`subject_id` = `edu_subject`.`subject_id`))) order by `edu_chapter_item`.`create_time` desc;

-- ----------------------------
-- View structure for tch_subject_vo
-- ----------------------------
DROP VIEW IF EXISTS `tch_subject_vo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tch_subject_vo` AS select `edu_teacher_link_subject`.`t_uid` AS `t_uid`,`edu_teacher_link_subject`.`subject_id` AS `subject_id`,`edu_subject`.`subject_title` AS `subject_title`,`edu_subject`.`subject_description` AS `subject_description`,`edu_subject`.`director_id` AS `director_id`,`edu_subject`.`cover` AS `cover` from (`edu_subject` join `edu_teacher_link_subject` on((`edu_subject`.`subject_id` = `edu_teacher_link_subject`.`subject_id`)));

SET FOREIGN_KEY_CHECKS = 1;
