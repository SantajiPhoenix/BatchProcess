package nl.yestelecom.phoenix.batch.job.simoverview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.simoverview.model.SimOverview;

@Service
public class SimOverviewHelper {
    private static Logger logger = LoggerFactory.getLogger(SimOverviewHelper.class);

    SimOverview buildSimOverview(String simType, Long count, SimOverview simOverview) {
        logger.info("Counting Sim Type");

        if (SimOverviewConstants.Y32K.equals(simType)) {
            simOverview.setY32KCount(count);
        }
        if (SimOverviewConstants.MICRO_USIM.equals(simType)) {
            simOverview.setMICROUSIMCount(count);
        }
        if (SimOverviewConstants.Y32KDUO.equals(simType)) {
            simOverview.setY32KCount(count);
        }
        if (SimOverviewConstants.YES_USIM.equals(simType)) {
            simOverview.setUSIMCount(count);
        }
        if (SimOverviewConstants.YES_USIM_DUO.equals(simType)) {
            simOverview.setUSIMDUOCount(count);
        }
        if (SimOverviewConstants.MICRO_USIM_DUO.equals(simType)) {
            simOverview.setMICROUSIMDUOCount(count);
        }
        if (SimOverviewConstants.YES_NANO_MICRO_COMBI_DUO_USIM.equals(simType)) {
            simOverview.setYESNANOMICROCOMBIDUOCount(count);
        }
        if (SimOverviewConstants.NANO_USIM.equals(simType)) {
            simOverview.setNANOUSIMCount(count);
        }
        if (SimOverviewConstants.YES_NANO_NANO_DUO.equals(simType)) {
            simOverview.setYESNANONANODUOCount(count);
        }
        if (SimOverviewConstants.NANO_USIM_DUO.equals(simType)) {
            simOverview.setNANOUSIMDUOCount(count);
        }
        if (SimOverviewConstants.NFCV1_USIM.equals(simType)) {
            simOverview.setNFCV1USIMCount(count);
        }

        return simOverview;
    }

    List<String> simOverviewStringData(List<SimOverview> simOverviewData) {
        logger.info("Formating data");
        final List<String> simOverviewStringList = new ArrayList<>();
        simOverviewStringList.addAll(getSecodaryHeader());
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

    private Collection<? extends String> getSecodaryHeader() {
        final List<String> headerList = new ArrayList<>();
        headerList.add("Voorraad;;Soort;;;;;;;;;;;;;");
        headerList.add(SimOverviewFileFormat.HEADERROWS);
        return headerList;
    }

    Long getTypeCount(Long count) {
        if (count != null) {
            return count;
        }
        return (long) 0;
    }

}
