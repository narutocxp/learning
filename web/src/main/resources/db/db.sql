CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_account` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_password` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_role` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_account` (`user_account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;