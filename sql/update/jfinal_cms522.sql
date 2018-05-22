-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.26-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 jfinal_cms 的数据库结构
CREATE DATABASE IF NOT EXISTS `jfinal_cms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jfinal_cms`;


-- 导出  表 jfinal_cms.busi_activity 结构
CREATE TABLE IF NOT EXISTS `busi_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activity_name` varchar(200) NOT NULL COMMENT '活动名',
  `activity_status` tinyint(4) DEFAULT NULL COMMENT '活动状态',
  `scoretpl_id` int(11) DEFAULT NULL COMMENT '评分模版ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int(10) unsigned DEFAULT '0' COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int(11) unsigned DEFAULT '0' COMMENT '创建者',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='活动表';

-- 正在导出表  jfinal_cms.busi_activity 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `busi_activity` DISABLE KEYS */;
REPLACE INTO `busi_activity` (`id`, `activity_name`, `activity_status`, `scoretpl_id`, `update_time`, `update_id`, `create_time`, `create_id`, `remarks`) VALUES
	(4, 'XX创意大赛', 0, 0, '2018-05-22 11:34:48', 0, '2018-05-22 11:34:48', 0, '0'),
	(5, 'XX创意大赛1', 0, 0, '2018-05-22 11:34:48', 0, '2018-05-22 11:34:48', 0, '0');
/*!40000 ALTER TABLE `busi_activity` ENABLE KEYS */;


-- 导出  表 jfinal_cms.busi_activity_slave 结构
CREATE TABLE IF NOT EXISTS `busi_activity_slave` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nodeid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '赛程节点ID在配置表1：初赛，2：复赛，3：决赛',
  `busi_activity_id` int(11) NOT NULL COMMENT ' 主表ID',
  `from_time` datetime DEFAULT NULL COMMENT '开始时间',
  `to_time` datetime DEFAULT NULL COMMENT '结束时间',
  `JudgesUid` varchar(500) DEFAULT NULL COMMENT '专业列表ids',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='活动详情关系表:初赛，复赛，决赛。';

-- 正在导出表  jfinal_cms.busi_activity_slave 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `busi_activity_slave` DISABLE KEYS */;
REPLACE INTO `busi_activity_slave` (`id`, `nodeid`, `busi_activity_id`, `from_time`, `to_time`, `JudgesUid`) VALUES
	(1, 0, 5, '2018-05-22 15:03:55', '2018-05-22 15:03:57', '张天'),
	(2, 1, 5, '2018-05-22 15:03:55', '2018-05-22 15:03:57', '李酒'),
	(3, 2, 5, '2018-05-22 15:03:55', '2018-05-22 15:03:57', '三为');
/*!40000 ALTER TABLE `busi_activity_slave` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
