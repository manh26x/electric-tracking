package com.mike.electrictracking.repository;

import com.mike.electrictracking.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, String> {
    @Query("select t.totalError from Tags t where t.name = :id")
    Long getTotalError(String id);
    @Query("select sum(t.totalError) from Tags t")
    Long getSumTotalError();
}
