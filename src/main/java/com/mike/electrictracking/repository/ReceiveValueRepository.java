package com.mike.electrictracking.repository;

import com.mike.electrictracking.entity.ReceiveValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ReceiveValueRepository extends JpaRepository<ReceiveValue, Long> {
    Optional<ReceiveValue> findFirstByTimeAndParamNameAndTagsName(Date time, String paramName, String tagName);
}
