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
# patent 表
DROP TABLE IF EXISTS `patent`;#判断student表是否存在，若存在则执行删除表操作
CREATE TABLE `patent` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `studentNum` varchar(255)  default NULL,
    `name` varchar(255) default NULL,
    `degree` varchar(255) default NULL,
    `enrollmentTime` varchar(255) default NULL,
    `patname` varchar(255) default NULL,
    `type` varchar(255) default NULL,
    `summary` varchar(300) default NULL,
    `email` varchar(255) default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patent 给表添加一些初始化数据用来测试
-- ----------------------------
INSERT INTO `patent` (studentNum, name, degree,enrollmentTime, patname, type, summary, email) VALUES ('221', '毛毛毛', '硕士', '2017-09-01','云资源调度','实用新型', '云资源', '1851234452@qq.com');
INSERT INTO `patent` (studentNum, name, degree,enrollmentTime, patname, type, summary, email) VALUES ('222', '派大星', '硕士', '2015-09-01','分布式计算','实用新型', '分布式', '9876543210@qq.com');
INSERT INTO `patent` (studentNum, name, degree,enrollmentTime, patname, type, summary, email) VALUES ('223', '海海海', '博士', '2015-07-08','边缘计算','实用新型', '边缘', '1851234452@qq.com');
INSERT INTO `patent` (studentNum, name, degree,enrollmentTime, patname, type, summary, email) VALUES ('224', '绵绵绵', '硕士', '2019-12-12','混合云调度','发明', '混合云', '1851234452@qq.com');
INSERT INTO `patent` (studentNum, name, degree,enrollmentTime, patname, type, summary, email) VALUES ('225', '章鱼哥', '博士', '2019-09-09','DAG任务调度','发明', 'DAG', '1851234452@qq.com');






