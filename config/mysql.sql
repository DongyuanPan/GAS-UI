Create Database If Not Exists GASUI Character Set UTF8; #如果数据库 GASUI 不存在 则创建GASUI

use GASUI;# 切换到数据库 GASUI

# 添加表就在下面仿照这个例子就可以

# student 表
DROP TABLE IF EXISTS `student`;#判断student表是否存在，若存在则执行删除表操作
CREATE TABLE `student` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
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
INSERT INTO `student` (studentNum, name, sex, enrollmentTime, phone, email, degree, type, employment) VALUES ('110', '潘潘潘', '男', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` (studentNum, name, sex, enrollmentTime, phone, email, degree, type, employment) VALUES ('111', '嘻嘻嘻', '女', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` (studentNum, name, sex, enrollmentTime, phone, email, degree, type, employment) VALUES ('112', '哈哈哈', '男', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` (studentNum, name, sex, enrollmentTime, phone, email, degree, type, employment) VALUES ('113', '叶叶叶', '男', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');
INSERT INTO `student` (studentNum, name, sex, enrollmentTime, phone, email, degree, type, employment) VALUES ('114', '捞捞捞', '女', '2018.9', '15151269930','614613461@qq.com','硕士', '在籍', '未知');

# XXX表
# paper 表
DROP TABLE IF EXISTS `paper`;#判断student表是否存在，若存在则执行删除表操作

CREATE TABLE `paper` (
`id` int(10) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) default NULL,
    `author1` varchar(255) default NULL,
    `author2` varchar(255) default NULL,
    `authorOther` varchar(255) default NULL,
    `origin` varchar(255) default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paper 给表添加一些初始化数据用来测试
-- ----------------------------

INSERT INTO `paper` (title, author1, author2, authorOther, origin)  VALUES ('sample1', 'authorA', 'authorB', 'xxx', 'seu');
INSERT INTO `paper` (title, author1, author2, authorOther, origin)  VALUES ('sample2', 'authorA', 'authorB', 'xxx', 'seu');
INSERT INTO `paper` (title, author1, author2, authorOther, origin)  VALUES ('sample3', 'authorA', 'authorB', 'xxx', 'seu');
INSERT INTO `paper` (title, author1, author2, authorOther, origin)  VALUES ('sample4', 'authorA', 'authorB', 'xxx', 'seu');
INSERT INTO `paper` (title, author1, author2, authorOther, origin)  VALUES ('sample5', 'authorA', 'authorB', 'xxx', 'seu');


# XXX表
# workflow表
DROP TABLE IF EXISTS `workflow`;#判断student表是否存在，若存在则执行删除表操作

CREATE TABLE `workflow` (
`id` int(10) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) default NULL,
    `localAddress` varchar(255) default NULL,
    `fileName` varchar(255) default NULL,
    `information` varchar(255) default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

