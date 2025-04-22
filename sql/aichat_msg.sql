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

 Date: 06/04/2025 13:03:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aichat_msg
-- ----------------------------
DROP TABLE IF EXISTS `aichat_msg`;
CREATE TABLE `aichat_msg`  (
  `mid` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `role` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `cid` bigint UNSIGNED NOT NULL COMMENT '所属对话id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `msg_aichat_chat_cid`(`cid` ASC) USING BTREE,
  CONSTRAINT `msg_aichat_chat_cid` FOREIGN KEY (`cid`) REFERENCES `aichat_chat` (`cid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aichat_msg
-- ----------------------------
INSERT INTO `aichat_msg` VALUES (45, 'user', '你能给我介绍一下广州吗', 662094719733829, '2025-04-04 23:30:34');
INSERT INTO `aichat_msg` VALUES (46, 'assistant', '当然可以。广州，简称“穗”，是广东省省会、华南地区重要的中心城市之一，位于中国南部沿海的珠江三角洲中心位置，是一座历史悠久、文化底蕴深厚的城市。\n\n### 历史文化\n\n- **历史**：广州有着2200多年的历史，是中国古代海上丝绸之路的起点。自秦汉以来，广州一直是全国最重要的港口城市之一。\n- **文化**：广州拥有丰富的历史文化遗迹，如陈家祠、镇海楼等，这些都见证了广州的历史变迁和文化底蕴。此外，广州还保留了许多传统节日和民俗活动，如春节、端午节等。\n\n### 经济发展\n\n- **经济地位**：广州是中国重要的经济中心之一，是中国四大一线城市之一，也是中国重要的交通枢纽和物流中心。\n- **产业**：广州的经济结构以制造业、服务业为主，其中汽车制造、电子产品制造、生物医药等行业尤为突出。同时，广州还是中国重要的金融中心之一，拥有众多金融机构和企业。\n\n### 旅游景点\n\n- **历史文化景点**：除了上述提到的陈家祠和镇海楼外，还有上下九步行街、北京路步行街、白云山等著名景点。\n- **自然风光**：广州周边有许多自然景观，如花城广场、珠江夜游、长隆旅游度假区等，适合休闲度假。\n\n### 当代生活\n\n广州是一个现代化与传统文化并存的城市，拥有便利的生活设施和国际化的生活方式。近年来，广州不断推进城市建设和国际化进程，吸引了大量国内外人才和投资者。\n\n总的来说，广州是一座充满活力、文化底蕴深厚的城市，无论是历史文化、经济发展还是现代生活方式，都值得深入探索。', 662094719733829, '2025-04-04 23:30:52');
INSERT INTO `aichat_msg` VALUES (47, 'user', '你好\n', 662094719733829, '2025-04-05 00:07:09');
INSERT INTO `aichat_msg` VALUES (48, 'assistant', '你好！有什么我可以帮助你的吗？', 662094719733829, '2025-04-05 00:07:09');
INSERT INTO `aichat_msg` VALUES (49, 'user', '111', 662094719733829, '2025-04-05 00:07:26');
INSERT INTO `aichat_msg` VALUES (50, 'assistant', '您好！您输入的是数字111，如果您有任何问题或需要关于广州或其他主题的信息，请随时告诉我！如果想聊点别的，也可以继续交流。', 662094719733829, '2025-04-05 00:07:28');
INSERT INTO `aichat_msg` VALUES (51, 'user', '111', 662094719733829, '2025-04-05 18:27:17');
INSERT INTO `aichat_msg` VALUES (52, 'assistant', '收到，您输入的是数字111。如果您有其他问题或者需要关于广州或者其他主题的信息，请随时告诉我！如果想聊点别的，我也很乐意继续交流。', 662094719733829, '2025-04-05 18:27:20');
INSERT INTO `aichat_msg` VALUES (53, 'user', '111\n', 662094719733829, '2025-04-05 18:31:25');
INSERT INTO `aichat_msg` VALUES (54, 'assistant', '好的，您输入的仍然是数字111。如果您有任何具体的问题或者需要了解的信息，比如关于广州的某个方面、某个话题，或者是其他任何事情，都可以随时告诉我。我会尽力帮助您。', 662094719733829, '2025-04-05 18:31:29');
INSERT INTO `aichat_msg` VALUES (55, 'user', '你好', 662426184978501, '2025-04-05 21:59:13');
INSERT INTO `aichat_msg` VALUES (56, 'assistant', '你好！有什么问题或需要帮助的吗？', 662426184978501, '2025-04-05 21:59:14');
INSERT INTO `aichat_msg` VALUES (57, 'user', '你能给我介绍一下肇庆吗', 662426184978501, '2025-04-05 21:59:27');
INSERT INTO `aichat_msg` VALUES (58, 'assistant', '当然可以。肇庆，位于中国广东省的西部，是一座有着悠久历史和丰富文化底蕴的城市。它不仅是广东省的一个重要城市，也是岭南文化的重要发源地之一。肇庆的历史可以追溯到秦汉时期，有着两千多年的历史。\n\n肇庆以独特的自然风光和丰富的文化遗产而闻名。最著名的莫过于七星岩、鼎湖山、肇庆古城墙等旅游景点。七星岩以其奇特的岩洞和秀丽的山水景观著称，鼎湖山则以其原始森林和清新的空气吸引着众多游客。此外，肇庆还是一个历史悠久的文化名城，拥有大量的古建筑和文物古迹，如端州博物馆、怀远楼等。\n\n在经济方面，肇庆是珠三角地区重要的交通枢纽和制造业基地，与广州、深圳等大城市紧密相连。近年来，随着粤港澳大湾区建设的推进，肇庆也在积极融入这一国家战略，致力于发展高新技术产业和现代服务业。\n\n总的来说，肇庆是一个集自然美景、历史文化与经济发展于一体的城市，值得人们去探索和了解。', 662426184978501, '2025-04-05 21:59:37');
INSERT INTO `aichat_msg` VALUES (59, 'user', '介绍一下樱花', 662094719733829, '2025-04-05 23:07:46');
INSERT INTO `aichat_msg` VALUES (60, 'assistant', '樱花是一种美丽的观赏植物，以其短暂而绚烂的花期著称。它们通常在春季盛开，象征着春天的到来和生命的复苏。下面是一些关于樱花的基本介绍：\n\n### 樱花的种类\n樱花有很多不同的种类，根据花色和花型的不同，可以分为多个亚种。例如：\n- **普通樱花（Cherry Blossom）**：是最常见的类型，花瓣白色或粉色。\n- **山樱（Prunus serrulata）**：是最受欢迎的樱花品种之一，花朵较小，花色多为白色或粉红色。\n- **彼岸花（Prunus x yedoensis）**：也被称为日本樱花，花色多为白色或淡粉色。\n\n### 樱花的文化意义\n樱花在日本文化中占有非常重要的地位，被视为美丽的象征和短暂生命的礼赞。每年春天，日本各地都会举行赏樱活动，称为“Hanami”，意为“赏花”。\n\n### 樱花的生长环境\n樱花喜欢温暖湿润的气候条件，适宜生长在肥沃、排水良好的土壤中。它们通常在春季开花，花期较短，一般持续一周左右。\n\n### 樱花的观赏地点\n日本是欣赏樱花的最佳地点之一，但世界各地也有很多地方可以观赏到樱花，包括美国、加拿大、欧洲等地的公园和庭院。\n\n### 樱花的花语\n樱花的花语通常是“短暂之美”、“美丽易逝”和“青春”。因为樱花花期短暂，所以也被视为一种珍惜美好时光的象征。\n\n希望这些信息对您有所帮助！如果您有更具体的问题或想了解更多细节，欢迎继续提问。', 662094719733829, '2025-04-05 23:08:02');
INSERT INTO `aichat_msg` VALUES (61, 'user', '有能种在南方的樱花吗', 662094719733829, '2025-04-05 23:08:18');
INSERT INTO `aichat_msg` VALUES (62, 'assistant', '确实有一些樱花品种适应南方的气候条件，并且可以在南方种植。虽然樱花原产于东亚地区，但在一些温和的南方地区，仍然可以成功栽培。以下是一些适合南方种植的樱花品种：\n\n### 适合南方种植的樱花品种\n\n1. **垂枝樱花（Pendulous Cherry Blossom）**\n   - 特点：树形优美，枝条下垂。\n   - 花期：通常在3月下旬至4月上旬。\n\n2. **山樱（Prunus serrulata）**\n   - 特点：树形紧凑，花朵小巧。\n   - 花期：通常在3月中旬至4月初。\n\n3. **彼岸花（Prunus x yedoensis）**\n   - 特点：树形较高大，花朵较大。\n   - 花期：通常在3月中旬至4月初。\n\n4. **春日樱花（Prunus serrulata \'Kiku-shidare-zakura\'）**\n   - 特点：枝条下垂，花朵密集。\n   - 花期：通常在3月中旬至4月初。\n\n5. **白樱花（Prunus serrulata \'Kiku-shidare-zakura\' var. alba）**\n   - 特点：纯白色的花朵。\n   - 花期：通常在3月中旬至4月初。\n\n### 种植建议\n\n- **土壤要求**：樱花喜欢排水良好、富含有机质的土壤。南方地区的土壤可能较为潮湿，需要注意排水。\n- **光照**：充足的阳光有助于樱花健康生长和开花。\n- **温度**：樱花对温度的适应范围较广，但避免极端寒冷或炎热的天气。\n- **浇水**：保持土壤适度湿润，特别是在花期和生长季节。\n- **修剪**：定期修剪可以帮助樱花保持良好的树形和促进健康生长。\n\n### 注意事项\n\n- 即使是在南方，种植樱花也需要考虑当地的气候条件和病虫害情况。\n- 在选择品种时，最好咨询当地的园艺专家或植物学家，以确保所选品种适合当地环境。\n\n希望这些信息对您有所帮助！如果您有更多具体的问题或需要进一步的信息，请随时告诉我。', 662094719733829, '2025-04-05 23:08:42');

SET FOREIGN_KEY_CHECKS = 1;
