package com.gas.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vm")//对应的表名
public class Vm {
    @Id//表明这个成员变量是 主键
    @GeneratedValue(generator="generator")
    @GenericGenerator(name="generator",strategy="increment")
    @Column(name = "ID", nullable = false)
    private Integer id;
    private Integer hostId;
    private Integer resId;
    private String  user;
    private String  vmname;
    private Integer count;
    private Integer mirror;
    private Integer ram;
    private Integer mips;
    private Integer bw;
    private Integer cpu;
    private String  enrollmentTime;
}
