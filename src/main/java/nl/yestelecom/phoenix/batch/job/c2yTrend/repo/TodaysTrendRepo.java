package nl.yestelecom.phoenix.batch.job.c2yTrend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.yestelecom.phoenix.batch.job.c2yTrend.model.TodaysTrend;

public interface TodaysTrendRepo extends JpaRepository<TodaysTrend, Long> {
    @Query(value = "select rownum id , v.* from V_TODAYS_TREND v", nativeQuery = true)
    TodaysTrend findTodaysTrend();

}
