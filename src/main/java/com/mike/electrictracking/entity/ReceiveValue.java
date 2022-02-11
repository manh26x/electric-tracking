package com.mike.electrictracking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class ReceiveValue {
    @Id
    @GeneratedValue
    private Long id;
    private Double paramValue;
    private Date time;
    private String paramName;
    private String tagsName;
}
