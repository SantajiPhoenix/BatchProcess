package nl.yestelecom.phoenix.batch.job.c2yTrend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.yestelecom.phoenix.batch.job.c2yTrend.model.C2yReport;

public interface C2yReportRepository extends JpaRepository<C2yReport, Long> {
    @Query(value = "select rownum id , v.* from V_C2YREPORT v", nativeQuery = true)
    List<C2yReport> findc2ywithid();

}
