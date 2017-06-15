package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CreditControlRepository extends JpaRepository<CreditControl, String> {
    @Override
	public List<CreditControl> findAll();

    @Query(value="select rownum as ID,  TYPE, INTERVAL, REASON, CREATOR, AUTORISATOR, END_DATE, BEGIN_DATE, CHARGE_CODE, CHARGE_TYPE, CHARGE_LEVEL, STATUS, MONTHLY_AMOUNT, INVOICE_DATE, ZYGO_AMOUNT, "
            + " ZYGO_CODE, DEALER_NAME, DEALER_CODE, CUSTOMER_NAME, CUSTOMER_NUMBER, GSM_NO, SYSTEM  from V_CREDITCONTROL ", nativeQuery=true)
    public List<CreditControl> fetchAllData();

}
