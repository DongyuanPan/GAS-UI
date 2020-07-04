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
@Table(name = "workflow")//对应的表名
public class Workflow {

    //主键按照下面的设置
    @Id//表明这个成员变量是 主键
    @GeneratedValue
    @GenericGenerator(name = "system-uuid", strategy = "identity")
    @Column(name = "ID", nullable = false)
    private Integer id;

    //下面是非主键字段
    private String title;
    private String localAddress;
    private String fileName;
    private String information;

}