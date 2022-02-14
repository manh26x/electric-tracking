package com.mike.electrictracking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamValueResponse {
    private List<ParamValue> eTotal;
    private List<ParamValue> temp;
    private String paramName;
    private Date time;
    private Long totalError;
}
