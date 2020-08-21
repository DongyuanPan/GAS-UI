package com.gas.web.entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patent")//对应的表名
public class Patent {

    @Id//表明这个成员变量是 主键
    @GeneratedValue(generator="generator")
    @GenericGenerator(name="generator",strategy="increment")
    @Column(name = "ID", nullable = false)
    private Integer id;

    //下面是非主键字段
    private String name;
    private String secondname;
    private String enrollmentTime;
    private String type;
    private String patname;
    private String summary;
    private String path;
}