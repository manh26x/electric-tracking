package com.mike.electrictracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawData {
    private Date time;
    private HashMap<String, Double> paramValue;
}
