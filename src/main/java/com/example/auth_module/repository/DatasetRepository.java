package com.example.auth_module.repository;

import com.example.auth_module.entity.DatasetMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatasetRepository extends JpaRepository<DatasetMetadata, Long> {
    @Query(value = "SELECT * FROM dataset_metadata WHERE tags @> CAST(CONCAT('[\"', :tag, '\"] Kind') AS jsonb)", nativeQuery = true)
    List<DatasetMetadata> findByTagName(@Param("tag") String tag);
}
