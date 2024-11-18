/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80400 (8.4.0)
 Source Host           : localhost:3306
 Source Schema         : mynews

 Target Server Type    : MySQL
 Target Server Version : 80400 (8.4.0)
 File Encoding         : 65001

 Date: 18/11/2024 14:08:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document`
(
    `doc_id`           bigint UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `upload_person_id` bigint UNSIGNED                                               NOT NULL,
    `document_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `upload_time`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `review_person_id` bigint UNSIGNED                                               NULL     DEFAULT NULL,
    `review_time`      timestamp                                                     NULL     DEFAULT NULL,
    `size`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL,
    `status`           tinyint UNSIGNED                                              NULL     DEFAULT 0,
    PRIMARY KEY (`doc_id`) USING BTREE,
    INDEX `user_id` (`upload_person_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_content
-- ----------------------------
DROP TABLE IF EXISTS `file_content`;
CREATE TABLE `file_content`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT,
    `document_id` bigint   NOT NULL,
    `content`     longblob NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_document_id` (`document_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`     bigint UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `role`        tinyint UNSIGNED                                              NOT NULL DEFAULT 1,
    `create_time` timestamp                                                     NULL     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
