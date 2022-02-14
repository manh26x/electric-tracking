package com.mike.electrictracking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamValue {
    private String tagName;
    private String tagCode;
    private Double value;
}
