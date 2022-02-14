package com.mike.electrictracking.repository;

import com.mike.electrictracking.dto.response.ParamValue;
import com.mike.electrictracking.entity.ReceiveValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReceiveValueRepository extends JpaRepository<ReceiveValue, Long> {
    Optional<ReceiveValue> findFirstByTimeAndParamNameAndTagsName(Date time, String paramName, String tagName);

    @Query("select max(rv.time) from ReceiveValue  rv where rv.tagsName = :tagId")
    Date getLatestDate(String tagId);

    @Query("select max(rv.time) from ReceiveValue  rv")
    Date getLatestDate();

    @Query("select new com.mike.electrictracking.dto.response.ParamValue(t.name, t.code, rv.paramValue) from " +
            " ReceiveValue  rv join Tags t on rv.tagsName = t.name " +
            " where rv.paramName = :paramName and rv.time = :time")
    List<ParamValue> getAllParamValueByTime(String paramName, Date time);


    @Query("select distinct rv.paramName from ReceiveValue  rv")
    List<String> getAllParamName();

    @Query("select rv from ReceiveValue rv where rv.time <= :toDate and rv.time >= :fromDate " +
            " and rv.paramName = :paramName and rv.tagsName = :tagId")
    Page<ReceiveValue> getAllWithDate(Date fromDate, Date toDate, Pageable pageable, String paramName, String tagId);
}
