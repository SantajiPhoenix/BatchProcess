package nl.yestelecom.phoenix.batch.job.c2yTrend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.yestelecom.phoenix.batch.job.c2yTrend.model.ConnectionList;

public interface ConnectionListRepo extends JpaRepository<ConnectionList, Long> {
    @Query(value = "select rownum id , v.* from CONNECTION_LIST v", nativeQuery = true)
    List<ConnectionList> findTypeOfConnectionCount();

}
