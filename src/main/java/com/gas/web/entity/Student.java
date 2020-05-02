package com.gas.web.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter//自动给成员变量创建Get函数
@Setter//自动给成员变量创建Set函数
@Entity//表明这个是一个实体类，对应数据库的
public class Student {


    @Id//表明这个成员变量是 主键
    @GeneratedValue//自增型
    private Integer id;

    private String studentNum;
    private String name;
    private Integer age;
    private String sex;
    private String email;
}