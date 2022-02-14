package com.mike.electrictracking.dto.response;

import com.mike.electrictracking.dto.RawData;
import com.mike.electrictracking.entity.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsAnalysisResponse {
    private Date latestDateUpdate;
    private Tags tagsProperty;
    private List<RawData> data;
    private Integer page;
    private Integer size;
    private Long totalItems;
    private List<String> paramList;
    private Integer totalPage;
}
