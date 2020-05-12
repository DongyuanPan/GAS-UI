package com.gas.web.entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")//对应的表名
public class Student {

    //主键按照下面的设置
    @Id//表明这个成员变量是 主键
    @GeneratedValue(generator="generator")
    @GenericGenerator(name="generator",strategy="increment")
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