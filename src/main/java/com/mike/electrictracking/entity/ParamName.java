package com.mike.electrictracking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ParamName {
    @Id
    private String name;
}
