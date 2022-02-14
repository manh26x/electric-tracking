package com.mike.electrictracking.service;

import com.mike.electrictracking.dto.request.TagAnalysisSearch;
import com.mike.electrictracking.dto.response.ParamValueResponse;
import com.mike.electrictracking.dto.response.TagsAnalysisResponse;
import org.springframework.data.domain.Pageable;

public interface AnalysisService {
    TagsAnalysisResponse analysisOneTags(String tagId, TagAnalysisSearch tagAnalysisSearch, Pageable pageable);
    ParamValueResponse getDashboard();
}
