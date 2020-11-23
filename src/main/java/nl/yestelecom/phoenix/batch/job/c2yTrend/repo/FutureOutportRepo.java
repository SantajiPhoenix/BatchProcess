package nl.yestelecom.phoenix.batch.job.c2yTrend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.yestelecom.phoenix.batch.job.c2yTrend.model.FutureOutport;

public interface FutureOutportRepo extends JpaRepository<FutureOutport, Long> {
    @Query(value = "select rownum id , v.* from FUTURE_TRENDS v", nativeQuery = true)
    List<FutureOutport> findfutureOutport();

}
