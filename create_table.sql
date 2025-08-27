#db name id_generator

CREATE TABLE `biz_tiny_id`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `biz_type` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '业务类型，唯一',
  `begin_id` bigint NOT NULL COMMENT '开始id，仅记录初始值，无其他含义。初始化时begin_id和max_id应相同',
  `max_id` bigint NOT NULL COMMENT '当前最大id',
  `step` int NOT NULL COMMENT '步长 号段长度',
  `delta` int NOT NULL COMMENT '每次id增量',
  `remainder` int NOT NULL COMMENT '余数',
  `version` bigint NOT NULL COMMENT '版本号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 COMMENT = 'id信息表' ROW_FORMAT = DYNAMIC;

INSERT INTO `biz_tiny_id` (`id`, `biz_type`, `begin_id`, `max_id`, `step`, `delta`, `remainder`, `version`, `create_time`, `update_time`) VALUES (1, 'goods', 1, 1, 1000, 1, 0, 1, '2023-07-18 11:03:28', '2023-08-25 17:45:39');
INSERT INTO `biz_tiny_id` (`id`, `biz_type`, `begin_id`, `max_id`, `step`, `delta`, `remainder`, `version`, `create_time`, `update_time`) VALUES (2, 'order', 1, 1, 1000, 1, 0, 1, '2023-07-21 13:35:47', '2023-09-01 14:17:03');
INSERT INTO `biz_tiny_id` (`id`, `biz_type`, `begin_id`, `max_id`, `step`, `delta`, `remainder`, `version`, `create_time`, `update_time`) VALUES (3, 'order_goods_item', 1, 1, 1000, 1, 0, 1, '2023-07-21 13:35:47', '2023-09-01 14:17:03');
