package com.mike.electrictracking.service.impl;

import com.mike.electrictracking.dto.RawData;
import com.mike.electrictracking.dto.request.TagAnalysisSearch;
import com.mike.electrictracking.dto.response.ParamValueResponse;
import com.mike.electrictracking.dto.response.TagsAnalysisResponse;
import com.mike.electrictracking.dto.response.TagsNotFoundException;
import com.mike.electrictracking.entity.ReceiveValue;
import com.mike.electrictracking.entity.Tags;
import com.mike.electrictracking.repository.ReceiveValueRepository;
import com.mike.electrictracking.repository.TagsRepository;
import com.mike.electrictracking.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private ReceiveValueRepository receiveValueRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Value("${data.dashboard-param}")
    private String dashboardParam;
    @Override
    public TagsAnalysisResponse analysisOneTags(String tagId, TagAnalysisSearch tagAnalysisSearch, Pageable pageable) {
        Optional<Tags> tags = tagsRepository.findById(tagId);
        if(tags.isPresent()) {
            TagsAnalysisResponse tagsAnalysisResponse = new TagsAnalysisResponse();
            tagsAnalysisResponse.setTagsProperty(tags.get());
            tagsAnalysisResponse.setLatestDateUpdate(receiveValueRepository.getLatestDate(tagId));
            List<String> allParam = receiveValueRepository.getAllParamName();
            tagsAnalysisResponse.setParamList(allParam);
            HashMap<Date, RawData> data = new HashMap<>();
            allParam.forEach(param -> {
                Page<ReceiveValue> valuePage = receiveValueRepository.getAllWithDate(tagAnalysisSearch.getFromDate(), tagAnalysisSearch.getToDate(),
                        pageable, param, tagId);
                if(tagsAnalysisResponse.getTotalItems() == null || tagsAnalysisResponse.getTotalItems() < valuePage.getTotalElements()) {
                    tagsAnalysisResponse.setTotalItems(valuePage.getTotalElements());
                    tagsAnalysisResponse.setTotalPage(valuePage.getTotalPages());
                    tagsAnalysisResponse.setPage(valuePage.getNumber());
                    tagsAnalysisResponse.setSize(valuePage.getSize());
                }
                valuePage.get().forEach(e -> {
                    RawData rawData = data.get(e.getTime());
                    if(rawData == null) {
                        rawData = new RawData();
                        rawData.setTime(e.getTime());
                    }
                    if(rawData.getParamValue() == null) {
                        rawData.setParamValue(new HashMap<>());
                    }
                    rawData.getParamValue().put(e.getParamName(), e.getParamValue());
                    data.put(e.getTime(), rawData);
                });
            });
            tagsAnalysisResponse.setData(new ArrayList<>(data.values()));
            return tagsAnalysisResponse;
        }
        throw new TagsNotFoundException("Tags Not Found");
    }

    @Override
    public ParamValueResponse getDashboard() {
        Date latestTime = receiveValueRepository.getLatestDate();
        ParamValueResponse response = new ParamValueResponse();
        String eTotalParam = this.dashboardParam.split(",")[0];
        String tempParam = this.dashboardParam.split(",")[1];
        response.setParamName(eTotalParam);
        response.setTime(latestTime);
        response.setTotalError(this.tagsRepository.getSumTotalError());
        response.setETotal(receiveValueRepository.getAllParamValueByTime(eTotalParam, latestTime));
        response.setTemp(receiveValueRepository.getAllParamValueByTime(tempParam, latestTime));
        return response;
    }
}
