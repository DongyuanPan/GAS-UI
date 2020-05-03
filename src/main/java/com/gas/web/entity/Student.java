package com.gas.web.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter//自动给成员变量创建Get函数
@Setter//自动给成员变量创建Set函数
@Entity//表明这个是一个实体类，对应数据库的
@Table(name = "student")//对应的表名
public class Student {

    //主键按照下面的设置
    @Id//表明这个成员变量是 主键
    @GeneratedValue
    @GenericGenerator(name = "system-uuid", strategy = "identity")
    @Column(name = "ID", nullable = false)
    private Integer id;

    //下面是非主键字段
    private String studentNum;
    private String name;
    private String sex;
    private String enrollmentTime;
    private String phone;
    private String email;
    private String degree;
    private String type;
    private String employment;
}