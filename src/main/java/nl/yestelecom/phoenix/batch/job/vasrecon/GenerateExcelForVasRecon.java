package nl.yestelecom.phoenix.batch.job.vasrecon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconData;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconDataRepository;

@Service
@Transactional
public class GenerateExcelForVasRecon {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    @Autowired
    private VasReconDataRepository vasReconDataRepository;
    @Value("${vasrecon.fileName}")
    private String fileName;
    @Value("${vasrecon.filePath}")
    private String filePath;

    public File generateExcel() throws IOException {

        final List<VasReconData> vasReconDataList = vasReconDataRepository.findAll();
        File outFile = null;
        FileOutputStream fos = null;
        XSSFWorkbook workbook;
        workbook = generateXls(vasReconDataList);
        outFile = new File(filePath + fileName);
        fos = new FileOutputStream(outFile);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        workbook.write(fos);
        fos.close();

        return outFile;

    }

    private XSSFWorkbook generateXls(List<VasReconData> vasReconDataList) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet();
        createHeaderRow(sheet);
        int rowCount = 1;
        for (final VasReconData c : vasReconDataList) {
            final XSSFRow row = sheet.createRow(rowCount++);
            int colIndex = 0;
            row.createCell(colIndex++).setCellValue(null != c.getId() ? c.getId() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getGssId() ? c.getId() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getGssProdId() ? c.getGssProdId() : 0);
            row.createCell(colIndex++).setCellValue(StringUtils.defaultString(c.getC2yCode()));
            row.createCell(colIndex++).setCellValue(StringUtils.defaultString(c.getAction()));
            row.createCell(colIndex++).setCellValue(null != c.getStatus() ? dateFormat.format(c.getStatus()) : null);
            row.createCell(colIndex++).setCellValue(null != c.getExecutionTime() ? dateFormat.format(c.getExecutionTime()) : null);
            row.createCell(colIndex++).setCellValue(null != c.getCreationDate() ? dateFormat.format(c.getCreationDate()) : null);
            row.createCell(colIndex++).setCellValue(null != c.getLastUpdated() ? dateFormat.format(c.getLastUpdated()) : null);
            row.createCell(colIndex++).setCellValue(StringUtils.defaultString(c.getSource()));
            row.createCell(colIndex++).setCellValue(StringUtils.defaultString(c.getRetry()));
            row.createCell(colIndex++).setCellValue(null != c.getAmountBilled() ? c.getAmountBilled() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getC2yPrice() ? c.getC2yPrice() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getPlannedAMount() ? c.getPlannedAMount() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getConfigAmount() ? c.getConfigAmount() : 0);
        }
        return workbook;
    }

    private void createHeaderRow(Sheet sheet) {

        final CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        final Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        final Row row = sheet.createRow(0);
        createCell(cellStyle, row, 0, "ID");
        createCell(cellStyle, row, 1, "GSS_ID");
        createCell(cellStyle, row, 2, "GSS_PROD_ID");
        createCell(cellStyle, row, 3, "C2Y_CODE");
        createCell(cellStyle, row, 4, "ACTION");
        createCell(cellStyle, row, 5, "STATUS");
        createCell(cellStyle, row, 6, "EXECUTION_TIME");
        createCell(cellStyle, row, 7, "CREATION_DATE");
        createCell(cellStyle, row, 8, "LAST_UPDATED");
        createCell(cellStyle, row, 9, "SOURCE");
        createCell(cellStyle, row, 10, "RETRY");
        createCell(cellStyle, row, 11, "AMOUNT_BILLED");
        createCell(cellStyle, row, 12, "C2Y_PRICE");
        createCell(cellStyle, row, 13, "PLANNED_AMOUNT");
        createCell(cellStyle, row, 14, "CONFIG_AMOUNT");
    }

    private void createCell(final CellStyle cellStyle, final Row row, int cellIndex, String value) {
        final Cell titleCell1 = row.createCell(cellIndex);
        titleCell1.setCellStyle(cellStyle);
        titleCell1.setCellValue(value);
    }

}
