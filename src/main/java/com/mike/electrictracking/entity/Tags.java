package com.mike.electrictracking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Tags {
    @Id
    private String name;
    private String code;
    private Long totalError;
}
