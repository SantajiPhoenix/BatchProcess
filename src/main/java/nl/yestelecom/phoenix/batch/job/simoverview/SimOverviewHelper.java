package nl.yestelecom.phoenix.batch.job.simoverview;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.simoverview.model.SimOverview;
import nl.yestelecom.phoenix.batch.job.simoverview.model.SimTypeCount;

@Service
public class SimOverviewHelper {
    private static Logger logger = LoggerFactory.getLogger(SimOverviewHelper.class);

    SimOverview buildSimOverview(SimTypeCount simTypecount, SimOverview simOverview) {
        logger.info("Counting Sim Type");

        if (SimOverviewConstants.Y32K.equals(simTypecount.getSimType())) {
            simOverview.setY32KCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.MICRO_USIM.equals(simTypecount.getSimType())) {
            simOverview.setMICROUSIMCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.Y32KDUO.equals(simTypecount.getSimType())) {
            simOverview.setY32KCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.YES_USIM.equals(simTypecount.getSimType())) {
            simOverview.setUSIMCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.YES_USIM_DUO.equals(simTypecount.getSimType())) {
            simOverview.setUSIMDUOCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.MICRO_USIM_DUO.equals(simTypecount.getSimType())) {
            simOverview.setMICROUSIMDUOCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.YES_NANO_MICRO_COMBI_DUO_USIM.equals(simTypecount.getSimType())) {
            simOverview.setYESNANOMICROCOMBIDUOCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.NANO_USIM.equals(simTypecount.getSimType())) {
            simOverview.setNANOUSIMCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.YES_NANO_NANO_DUO.equals(simTypecount.getSimType())) {
            simOverview.setYESNANONANODUOCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.NANO_USIM_DUO.equals(simTypecount.getSimType())) {
            simOverview.setNANOUSIMDUOCount(simTypecount.getCount());
        }
        if (SimOverviewConstants.NFCV1_USIM.equals(simTypecount.getSimType())) {
            simOverview.setNFCV1USIMCount(simTypecount.getCount());
        }

        return simOverview;
    }

    List<String> simOverviewStringData(List<SimOverview> simOverviewData) {
        logger.info("Formating data");
        final List<String> simOverviewStringList = new ArrayList<>();
        for (final SimOverview simOver : simOverviewData) {
            String sim = "";
            sim = sim + simOver.getMainDealerName() + ";";
            sim = sim + simOver.getDealerName() + ";";
            sim = sim + getTypeCount(simOver.getY32KCount()) + ";";
            sim = sim + getTypeCount(simOver.getY32KDUOCount()) + ";";
            sim = sim + getTypeCount(simOver.getUSIMCount()) + ";";
            sim = sim + getTypeCount(simOver.getUSIMDUOCount()) + ";";
            sim = sim + getTypeCount(simOver.getMICROUSIMCount()) + ";";
            sim = sim + getTypeCount(simOver.getMICROUSIMDUOCount()) + ";";
            sim = sim + getTypeCount(simOver.getNANOUSIMCount()) + ";";
            sim = sim + getTypeCount(simOver.getNANOUSIMDUOCount()) + ";";
            sim = sim + getTypeCount(simOver.getYESNANOMICROCOMBIDUOCount()) + ";";
            sim = sim + getTypeCount(simOver.getYESNANONANODUOCount()) + ";";
            sim = sim + getTypeCount(simOver.getNFCV1USIMCount()) + ";";
            sim = sim + getTypeCount(simOver.getGrossStock()) + ";";
            sim = sim + getTypeCount(simOver.getNecessary()) + ";";
            sim = sim + getTypeCount(simOver.getNetStock()) + ";";
            simOverviewStringList.add(sim);
        }

        return simOverviewStringList;
    }

    Long getTypeCount(Long count) {
        if (count != null) {
            return count;
        }
        return (long) 0;
    }

}
