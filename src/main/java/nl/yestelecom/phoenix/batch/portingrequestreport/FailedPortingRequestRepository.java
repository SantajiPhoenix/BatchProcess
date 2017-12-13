package nl.yestelecom.phoenix.batch.portingrequestreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FailedPortingRequestRepository extends JpaRepository<PortingRequestObject, Long> {

    @Query(value = "select * from V_PORTING_FAILED_REQUEST_VIEW ", nativeQuery=true)
    public List<PortingRequestObject> fetchData();

}
