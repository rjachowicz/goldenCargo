package com.goldencargo.repository;

import com.goldencargo.model.dto.api.ReportDTO;
import com.goldencargo.model.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Report b SET b.isDeleted = true WHERE b.reportId = :id")
    void softDelete(@Param("id") Long id);

    @Query("""
            SELECT new com.goldencargo.model.dto.api.ReportDTO(
                r.reportId,
                r.reportType,
                r.dateGenerated,
                r.content,
                r.generatedBy.username)
            FROM Report r 
            WHERE r.isDeleted = false AND r.generatedBy.userId = :userId
            """)
    List<ReportDTO> getReportsByUserId(@Param("userId") Long userId);
}