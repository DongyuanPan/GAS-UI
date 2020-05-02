package com.gas.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class MenuKey implements Serializable {
    private Long id;
    private String title;
    private String href;
}