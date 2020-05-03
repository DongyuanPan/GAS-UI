Create Database If Not Exists GASUI Character Set UTF8; #如果数据库 GASUI 不存在 则创建GASUI

use GASUI;# 切换到数据库 GASUI

# 添加表就在下面仿照这个例子就可以

# student 表
DROP TABLE IF EXISTS `student`;#判断student表是否存在，若存在则执行删除表操作
CREATE TABLE `student` (
                           `id` int(10) NOT NULL,
                           `studentNum` varchar(255) default NULL,
                           `name` varchar(255) default NULL,
                           `sex` varchar(255) default NULL,
                           `enrollmentTime` varchar(255) default NULL,
                           `phone` varchar(255) default NULL,
                           `email` varchar(255) default NULL,
                           `degree` varchar(255) default NULL,
                           `type` varchar(255) default NULL,
                           `employment` varchar(255) default NULL,
                           PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student 给表添加一些初始化数据用来测试
-- ----------------------------
INSERT INTO `student` VALUES ('0', '110', '潘潘潘', '男', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` VALUES ('1', '111', '嘻嘻嘻', '女', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` VALUES ('2', '112', '哈哈哈', '男', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` VALUES ('3', '113', '叶叶叶', '男', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` VALUES ('4', '114', '捞捞捞', '女', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');

# XXX表





