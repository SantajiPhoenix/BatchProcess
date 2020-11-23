package nl.yestelecom.phoenix.batch.job.c2yTrend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.yestelecom.phoenix.batch.job.c2yTrend.model.FutureTerimination;

public interface FutureTeriminationRepo extends JpaRepository<FutureTerimination, Long> {
    @Query(value = "select rownum id , v.* from V_FUTURE_TERMINATION v", nativeQuery = true)
    List<FutureTerimination> findfutureTermination();

}
