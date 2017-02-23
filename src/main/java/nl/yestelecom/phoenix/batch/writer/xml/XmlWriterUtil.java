package nl.yestelecom.phoenix.batch.writer.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class XmlWriterUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlWriterUtil.class);
    @Autowired
    @Qualifier("classPathTemplateEngineXML")
    private TemplateEngine templateEngineXML;

    private String fileName;
    private String filePath;
    private String templateName;
    private Map<String, Object> xmlData;
    private String xmlContent;

    public void generateXML() {
        logger.info("Writing file >> " + fileName);
        xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        final Context ctx = new Context(LocaleContextHolder.getLocale(), xmlData);
        xmlContent += templateEngineXML.process(templateName, ctx);
    }

    public void write() {

        final File file = new File(filePath + fileName);
        boolean fileCreated = false;
        try {
            if (!file.exists()) {
                fileCreated = file.createNewFile();
            }
        } catch (final IOException e) {
            logger.error("Error while file creation " + e);
        }
        try (BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            bufferWritter.write(xmlContent);
            logger.info("Finished Write");
        } catch (final IOException e) {
            logger.error("Error while writing file >> " + e);
        }
        /*
         * try { if (!file.exists()) { file.createNewFile(); } fileWriter = new FileWriter(file.getAbsoluteFile()); bufferWritter = new BufferedWriter(fileWriter); bufferWritter.write(xmlContent);
         * logger.info("Finished Write"); } catch (final IOException e) { logger.error("Error while writing file >> " + e); } finally { try { if (null != bufferWritter) { bufferWritter.close(); } if
         * (null != fileWriter) { fileWriter.close(); } } catch (final IOException e) { logger.error("Error while closing buffer writers >> " + e); } }
         */
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setXmlData(Map<String, Object> xmlData) {
        this.xmlData = xmlData;
    }

    public void setFileProperties(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
