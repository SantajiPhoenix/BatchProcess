package nl.yestelecom.phoenix.batch.simupload.util;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;

@Service
public class JsonToObjectConverter {

    public List<LoadSim> convert(String json) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final List<LoadSim> obj = mapper.readValue(json, new TypeReference<List<LoadSim>>() {
        });
        return obj;

    }

}
