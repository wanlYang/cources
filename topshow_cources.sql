/*
 Navicat Premium Data Transfer

 Source Server         : YANG
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : topshow_cources

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 23/11/2019 21:15:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `adminname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员密码',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员邮箱',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员实际状态',
  `forceTime` datetime(0) NULL DEFAULT NULL COMMENT '管理生效时间',
  `lastLoginTime` datetime(0) NOT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('ADMIN7826110349', 'TOPSHOW', 'MjVmOWU3OTQzMjNiNDUzODg1ZjUxODFmMWI2MjRkMGI=', '18802927580', '344295704@qq.com', '0', '2019-11-07 18:56:07', '2019-11-07 18:56:10');

-- ----------------------------
-- Table structure for admin_nav
-- ----------------------------
DROP TABLE IF EXISTS `admin_nav`;
CREATE TABLE `admin_nav`  (
  `nav_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '导航栏ID',
  `nav_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级ID',
  `nav_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导航栏标题',
  `nav_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导航路径',
  `nav_index` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '索引',
  `is_target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打开新标签',
  `nav_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `nav_spread` tinyint(4) NULL DEFAULT 0 COMMENT '是否展开',
  `nav_role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`nav_id`) USING BTREE,
  INDEX `nav_parent_id`(`nav_parent_id`) USING BTREE,
  INDEX `nav_role`(`nav_role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin_nav
-- ----------------------------
INSERT INTO `admin_nav` VALUES (1, 0, '首页内容', '0', 'contentManagement', NULL, '&#xe63c;', 0, '1,3');
INSERT INTO `admin_nav` VALUES (2, 1, '动态课程表', 'cources/table/list', 'cources_table_list', NULL, 'icon-text', 0, '1,3');
INSERT INTO `admin_nav` VALUES (32, 1, '店面添加', 'cources/table/storefront/list', 'storefront', NULL, 'icon-text', 0, '1,3');

-- ----------------------------
-- Table structure for storefront
-- ----------------------------
DROP TABLE IF EXISTS `storefront`;
CREATE TABLE `storefront`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店面ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店面名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of storefront
-- ----------------------------
INSERT INTO `storefront` VALUES ('10132947561574263774261', '东郊店(&金花路店)');
INSERT INTO `storefront` VALUES ('14763258915744854224964', '曲江店');
INSERT INTO `storefront` VALUES ('47101389251574507011770', '丰禾店');

-- ----------------------------
-- Table structure for table_cources
-- ----------------------------
DROP TABLE IF EXISTS `table_cources`;
CREATE TABLE `table_cources`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `star_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程星级',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '舞蹈类型',
  `effect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '舞蹈作用，介绍',
  `start_time` time(0) NULL DEFAULT NULL COMMENT '课程开始时间',
  `end_time` time(0) NULL DEFAULT NULL COMMENT '结束时间',
  `week` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '星期ID',
  `storefront` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店面ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `week`(`week`) USING BTREE,
  INDEX `table_ course_ibfk_2`(`storefront`) USING BTREE,
  CONSTRAINT `table_cources_ibfk_2` FOREIGN KEY (`storefront`) REFERENCES `storefront` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `table_cources_ibfk_3` FOREIGN KEY (`week`) REFERENCES `week` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of table_cources
-- ----------------------------
INSERT INTO `table_cources` VALUES ('10246853791574333684832', 'admin', '★★★★★', '成品舞', '悬链肌肉', '09:00:00', '13:00:00', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('10379682411574495577352', '肚皮舞', '★★★★★', '成品舞3', '悬链肌肉', '10:00:00', '19:00:00', '39657101421574247168608', '14763258915744854224964');
INSERT INTO `table_cources` VALUES ('10867214951574338274151', 'admin', '★★★★★', '成品舞', '悬链肌肉', '15:00:00', '16:00:00', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('10968142371574337048524', 'admin', '★★★★★', '成品舞', '悬链肌肉', '14:00:00', '14:30:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('10986721351574335568211', 'admin', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '13:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('12683510741574335427306', 'admin', '★★★★★', '成品舞', '悬链肌肉', '11:00:00', '14:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('13104582761574338868269', 'admin', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '14:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('1623758491574342465428', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '16:00:00', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('19726310841574333388582', 'admin', '★★★★★', '成品舞', '悬链肌肉', '17:00:00', '18:00:00', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('21035917481574500147973', '肚皮舞', '★★★★★', '成品舞3', '悬链肌肉', '16:00:00', '16:00:00', '21964327101574247282899', '14763258915744854224964');
INSERT INTO `table_cources` VALUES ('23954110871574329507005', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '12:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('25678911031574335949910', 'admin', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '12:00:00', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('25938104671574336305414', 'admin', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '16:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('27108915631574332258819', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '09:00:00', '11:00:00', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('28759410131574334802424', 'admin', '★★★★★', '成品舞', '悬链肌肉', '09:00:00', '10:00:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('29311048751574336517696', 'admin', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '14:00:00', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('36184210951574333718680', 'admin', '★★★★★', '成品舞', '悬链肌肉', '16:00:00', '20:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('39108671251574335893249', 'admin', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '14:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('39410157861574333510560', 'admin', '★★★★★', '成品舞', '悬链肌肉', '11:00:00', '18:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('41037195261574332729168', 'admin', '★★★★★', '成品舞', '悬链肌肉', '14:00:00', '16:00:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('41759361081574333612834', 'admin', '★★★★★', '成品舞', '悬链肌肉', '15:00:00', '17:00:00', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('42975361081574336773594', 'admin', '★★★★★', '成品舞', '悬链肌肉', '11:00:00', '22:00:00', '47196108431574247199561', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('47815321091574336730098', 'admin', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '13:00:00', '47196108431574247199561', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('47910651231574336235317', 'admin', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '11:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('54862109371574334890756', 'admin', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '12:00:00', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57860910931565797075245', '阿斯顿', '★★★★★', '水电费', '梵蒂冈', '13:18:26', '13:18:28', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57860910931574297075245', 'asfda', '★★★★★', '打算', '发过火', '13:17:07', '13:17:09', '47196108431574247199561', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57860910934574297075245', '阿斯顿', '★★★★★', '发过火', '即可', '13:18:00', '13:18:02', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57861210931574297075245', '啊发顺', '★★★★★', '按时', '国防生的', '13:15:12', '13:15:14', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57861210931574297075286', '奥术大', '★★★★★', '阿打算', '阿666', '10:34:05', '10:34:07', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57861210931574297075287', '相持不', '★★★★★', '水电费', '胜多负少', '04:00:00', '13:00:00', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57861210931574297075845', '阿斯顿', '★★★★★', '阿达', '似懂非懂', '13:16:05', '13:16:08', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('57861210931574297089245', '阿斯VB', '★★★★★', '规划局', '4儿童', '13:16:39', '13:16:41', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('58210437191574335042271', 'admin', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '14:00:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('5826749311574316771996', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '12:30:00', '14:00:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('5826914371574333944985', 'admin', '★★★★★', '成品舞', '悬链肌肉', '12:00:00', '13:00:00', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('61104982751574316960765', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '11:00:00', '47196108431574247199561', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('61472810531574338310833', 'admin', '★★★★★', '成品舞', '悬链肌肉', '16:00:00', '17:00:00', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('61537984101574338767801', 'admin', '★★★★★', '成品舞', '悬链肌肉', '16:00:00', '17:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('62531041981574338916459', 'admin', '★★★★★', '成品舞', '悬链肌肉', '16:00:00', '17:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('65210793841574337457349', 'admin', '★★★★★', '成品舞', '悬链肌肉', '15:00:00', '21:00:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('69210485731574340027598', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '14:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('71362108451574335990140', 'admin', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '14:00:00', '67341681051574247226155', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('76510891341574335302028', 'admin', '★★★★★', '成品舞', '悬链肌肉', '12:00:00', '13:00:00', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('79103281641574338991264', 'admin', '★★★★★', '成品舞', '悬链肌肉', '19:00:00', '20:00:00', '51065329141574247214318', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('81279653101574333907422', 'admin', '★★★★★', '成品舞', '悬链肌肉', '12:00:00', '15:00:00', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('82159103461574337407802', 'admin', '★★★★★', '成品舞', '悬链肌肉', '15:00:00', '16:00:00', '11976352101574247112256', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('83126457101574332063141', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '11:00:00', '14:00:00', '47196108431574247199561', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('86135109721574336829073', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '10:00:00', '20:00:00', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('91042176831574333832404', 'admin', '★★★★★', '成品舞', '悬链肌肉', '16:00:00', '17:00:00', '47196108431574247199561', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('91673104821574332568010', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '14:00:00', '71046358971574247275310', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('93548210161574316807981', '肚皮舞', '★★★★★', '成品舞', '悬链肌肉', '14:00:00', '15:00:00', '39657101421574247168608', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('95724106381574337518295', 'admin', '★★★★★', '成品舞', '悬链肌肉', '13:00:00', '16:00:00', '21964327101574247282899', '10132947561574263774261');
INSERT INTO `table_cources` VALUES ('96183102541574504268271', '肚皮舞', '★★★★★', '成品舞3', '悬链肌肉', '04:00:00', '05:00:00', '11976352101574247112256', '14763258915744854224964');
INSERT INTO `table_cources` VALUES ('98141073651574335515121', 'admin', '★★★★★', '成品舞', '悬链肌肉', '12:00:00', '13:00:00', '47196108431574247199561', '10132947561574263774261');

-- ----------------------------
-- Table structure for week
-- ----------------------------
DROP TABLE IF EXISTS `week`;
CREATE TABLE `week`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '星期ID',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `english` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of week
-- ----------------------------
INSERT INTO `week` VALUES ('11976352101574247112256', '星期一', 'monday');
INSERT INTO `week` VALUES ('21964327101574247282899', '星期二', 'tuesday');
INSERT INTO `week` VALUES ('39657101421574247168608', '星期三', 'wednesday');
INSERT INTO `week` VALUES ('47196108431574247199561', '星期四', 'thursday');
INSERT INTO `week` VALUES ('51065329141574247214318', '星期五', 'friday');
INSERT INTO `week` VALUES ('67341681051574247226155', '星期六', 'saturday');
INSERT INTO `week` VALUES ('71046358971574247275310', '星期天', 'sunday');

SET FOREIGN_KEY_CHECKS = 1;
