package nl.yestelecom.phoenix.batch.simupload.io;

import java.util.List;

import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;

public interface FileCreator {
    void writeData(List<LoadSim> sims, String filename);

}
