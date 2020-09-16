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

# patent 表
DROP TABLE IF EXISTS `patent`;#判断patent表是否存在，若存在则执行删除表操作
CREATE TABLE `patent` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) default NULL,
    `secondname` varchar(255) default NULL,
    `enrollmentTime` varchar(255) default NULL,
    `type` varchar(255) default NULL,
    `patname` varchar(255) default NULL,
    `summary` varchar(300) default NULL,
    `path` varchar(200) default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patent 给表添加一些初始化数据用来测试,暂无专利文件，自行上传
-- ----------------------------
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', '毛毛毛', '2017-09-01', '实用新型', '云资源调度', '云资源');
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', '派大星', '2015-09-01', '实用新型', '分布式计算', '分布式');
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', '海海海', '2015-07-08', '实用新型', '边缘计算','边缘');
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', '绵绵绵', '2019-12-12', '发明', '混合云调度','混合云');
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', '章鱼哥', '2019-09-09', '发明', 'DAG任务调度', 'DAG');
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', 'victor', '2018-10-01', '实用新型', 'clustering', '减少makespan');
INSERT INTO `patent` (name, secondname, enrollmentTime, type, patname, summary) VALUES ('李小平', 'victor', '2009-09-01', '发明', '多目标优化', '减少数据传输代价及完成时间');

#resource表
DROP TABLE IF EXISTS `resource`;#判断resource表是否存在，若存在则执行删除表操作
CREATE TABLE `resource` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `person` varchar(255),
    `name`   varchar(255) NOT NULL unique,
    `hostnum` int(10),
    `crtime`  varchar(255),
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert  into `resource` (person, name, hostnum, crtime) values ('毛毛','resource1', '3', '2020/8/30'),('佩奇','resource2', '4', '2020/8/29'),('乔治','resource3', '5','2020/6/1');


#host表
drop table if exists `host`;
create table `host`(
  `id` int(10) not null auto_increment,
  `hostorder` int(10),
  `resId` int(10),
  `hostName` varchar(255),
  `vmnum` int(10),
  `crtime` varchar(255),
  primary key (`id`)
)engine=InnoDB default charset=utf8;
insert into `host` (hostorder, resId, hostName, vmnum, crtime)values ('1','1', 'res1Host1', '2', '2020/8/30'), ('2','1', 'resHost2', '2', '2020/8/30'),('3','1', 'res1Host3', '2', '2020/8/30');
insert into `host` (hostorder, resId, hostName, vmnum, crtime)values ('1','2', 'res2Host1', '2', '2020/8/30'), ('2' ,'2', 'res2Host2', '2', '2020/8/30'), ('3','2','res2Host3', '2', '2020/8/30'), ('4','2', 'res2Host3', '2', '2020/8/30');

#vm表
DROP TABLE IF EXISTS `vm`;#判断resource表是否存在，若存在则执行删除表操作
CREATE TABLE `vm` (
    `id` int(10) NOT NULL auto_increment,
    `hostId`  int(10),
    `resId` int(10),
    `user` varchar(255),
    `vmname` varchar(255),
    `count` int(10) default NULL,
    `mirror` int default NULL,
    `ram`    int default NULL,
    `mips`   int default NULL,
    `bw`     int default NULL,
    `cpu`    int default NULL,
    `enrollmentTime` varchar(255),
    primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into `vm` (hostId, resId, vmname, user, count, mirror, ram, mips, bw, cpu, enrollmentTime) values ('1', '1', 'vm1', 'susan', '10', '10000', '512', '1000', '1000', '2', '2020/8/1'),('1','1', 'vm2', 'susan', '2', '12000', '1024', '1100', '1000', '3', '2019/10/1'),('1','2', 'vm3', 'susan', '3', '15000', '1000', '1200', '1500', '2', '2020/6/1'),('2','1','vm21','susan','4','12000','1000','1100','1200','3','2020/9/1');


# resSample 表
DROP TABLE IF EXISTS `resSample`;#判断resource表是否存在，若存在则执行删除表操作
CREATE TABLE `resSample` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `user`   varchar(255) NOT NULL unique,
    `vmname` varchar(255) default NULL,
    `count`  int default NULL,
    `mirror` int default NULL,
    `ram`    int default NULL,
    `mips`   int default NULL,
    `bw`     int default NULL,
    `cpu`    int default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into `resSample` (user, vmname, count, mirror, ram, mips, bw, cpu) values ('susan', 'sample', '10', '10000', '512', '1000', '1000', '2');

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


# XXX表
# project 表
DROP TABLE IF EXISTS `project`;#判断student表是否存在，若存在则执行删除表操作

CREATE TABLE `project` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) default NULL,
    `pic` varchar(255) default NULL,
    `startDate` varchar(255) default NULL,
    `endDate` varchar(255) default NULL,
    `type` varchar(255) default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project 给表添加一些初始化数据用来测试
-- ----------------------------

INSERT INTO `project` (title, pic, startDate, endDate, type)  VALUES ('sample1','li','2020-7','2022-7','国家自然基金项目');
INSERT INTO `project` (title, pic, startDate, endDate, type)  VALUES ('sample2','li','2020-7','2022-7','国家自然基金项目');
INSERT INTO `project` (title, pic, startDate, endDate, type)  VALUES ('sample3','li','2020-7','2022-7','国家自然基金项目');
INSERT INTO `project` (title, pic, startDate, endDate, type)  VALUES ('sample4','li','2020-7','2022-7','国家自然基金项目');
INSERT INTO `project` (title, pic, startDate, endDate, type)  VALUES ('sample5','li','2020-7','2022-7','国家自然基金项目');


# algorithm 表
DROP TABLE IF EXISTS `algorithm`;#判断algorithm表是否存在，若存在则执行删除表操作
CREATE TABLE `algorithm` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) default NULL,
    `summary` varchar(300) default NULL,
    `path` varchar(200) default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of algorithm 给表添加一些初始化数据用来测试,暂无算法文件，自行上传
-- ----------------------------
INSERT INTO `algorithm` (name, summary) VALUES ('X算法', 'XXX');
INSERT INTO `algorithm` (name, summary) VALUES ('Y算法', 'YYY');
INSERT INTO `algorithm` (name, summary) VALUES ('Z算法', 'ZZZ');
