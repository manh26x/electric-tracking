package com.mike.electrictracking.controller;

import com.mike.electrictracking.dto.request.TagAnalysisSearch;
import com.mike.electrictracking.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("**")
public class DataAnalysisController {

    @Autowired
    private AnalysisService analysisService;



    @GetMapping("analysis/{tagId}")
    public ResponseEntity<?> analysisOneTag(@PathVariable String tagId, TagAnalysisSearch tagAnalysisSearch, Pageable pageable) {
        return ResponseEntity.ok(analysisService.analysisOneTags(tagId, tagAnalysisSearch, pageable));
    }

    @GetMapping("dashboard")
    public ResponseEntity<?> getDashboard() {
        return ResponseEntity.ok(analysisService.getDashboard());
    }
}
