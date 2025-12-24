create
database if not exists learning;

use
learning;

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `user`;
-- auto-generated definition
create table user
(
    id            bigint auto_increment
        primary key,
    user_account  varchar(255) null,
    user_password varchar(50) null,
    user_role     varchar(50) null,
    constraint uq_user_account
        unique (user_account)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户';