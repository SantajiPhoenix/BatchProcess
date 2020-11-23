package nl.yestelecom.phoenix.batch.job.c2yTrend;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.c2yTrend.model.C2yReport;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.ConnectionList;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.FutureOutport;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.FutureTerimination;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.TodaysTrend;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.C2yReportRepository;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.ConnectionListRepo;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.FutureOutportRepo;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.FutureTeriminationRepo;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.TodaysTrendRepo;

@Service
@Transactional
public class GenerateExcelForC2yReport {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    @Autowired
    C2yReportRepository c2yReportRepository;
    @Autowired
    FutureTeriminationRepo futureTeriminationRepo;
    @Autowired
    ConnectionListRepo connectionListRepo;
    @Autowired
    FutureOutportRepo futureOutportRepo;
    @Autowired
    TodaysTrendRepo todaysTrendRepo;
    @Value("${c2yreport.fileName}")
    private String fileName;
    @Value("${c2yreport.filePath}")
    private String filePath;

    public File generateExcel() {
        File outFile = null;
        try {
            final List<C2yReport> c2yTrends = c2yReportRepository.findc2ywithid();
            final List<FutureTerimination> findfutureTerminationlist = futureTeriminationRepo.findfutureTermination();
            final List<FutureOutport> findfutureOutportlist = futureOutportRepo.findfutureOutport();
            final List<ConnectionList> findTypeOfConnectionCountList = connectionListRepo.findTypeOfConnectionCount();
            final TodaysTrend findTodaysTrend = todaysTrendRepo.findTodaysTrend();
            FileOutputStream fos = null;
            XSSFWorkbook workbook;
            workbook = generateXls(c2yTrends, findfutureTerminationlist, findfutureOutportlist, findTypeOfConnectionCountList, findTodaysTrend);
            outFile = new File(filePath + fileName);
            fos = new FileOutputStream(outFile);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            workbook.write(fos);
            fos.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return outFile;

    }

    private XSSFWorkbook generateXls(List<C2yReport> c2yTrends, List<FutureTerimination> findfutureTerminationlist, List<FutureOutport> findfutureOutportlist,
            List<ConnectionList> findTypeOfConnectionCountList, TodaysTrend findTodaysTrend) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet();
        int rowCount = 0;
        createHeaderRowForTodaysTrend(sheet, rowCount);
        rowCount = rowCount + 1;
        final XSSFRow rowfirst = sheet.createRow(rowCount++);
        int colIndexfirst = 0;
        rowfirst.createCell(colIndexfirst++).setCellValue(null != findTodaysTrend.getActiveConnCount() ? findTodaysTrend.getActiveConnCount() : 0);
        rowfirst.createCell(colIndexfirst++).setCellValue(null != findTodaysTrend.getActiveCusCount() ? findTodaysTrend.getActiveCusCount() : 0);
        rowfirst.createCell(colIndexfirst++).setCellValue(null != findTodaysTrend.getActiveDlrCount() ? findTodaysTrend.getActiveDlrCount() : 0);
        createHeaderRowForConnectionType(sheet, rowCount);
        rowCount = rowCount + 1;
        for (final ConnectionList c : findTypeOfConnectionCountList) {
            final XSSFRow row = sheet.createRow(rowCount++);
            int colIndex = 0;
            row.createCell(colIndex++).setCellValue(null != c.getType() ? c.getType() : null);
            row.createCell(colIndex++).setCellValue(null != c.getOutportConnCount() ? c.getOutportConnCount() : 0);
        }
        createHeaderRowForOutport(sheet, rowCount);
        rowCount = rowCount + 1;
        for (final FutureOutport c : findfutureOutportlist) {
            final XSSFRow row = sheet.createRow(rowCount++);
            int colIndex = 0;
            row.createCell(colIndex++).setCellValue(null != c.getMONTH() ? c.getMONTH() : null);
            row.createCell(colIndex++).setCellValue(null != c.getFuture_Outport_Count() ? c.getFuture_Outport_Count() : 0);
        }

        createHeaderRowForTermination(sheet, rowCount);
        rowCount = rowCount + 1;
        for (final FutureTerimination c : findfutureTerminationlist) {
            final XSSFRow row = sheet.createRow(rowCount++);
            int colIndex = 0;
            row.createCell(colIndex++).setCellValue(null != c.getMONTH() ? c.getMONTH() : null);
            row.createCell(colIndex++).setCellValue(null != c.getTERMINATION_FUTURE_COUNT() ? c.getTERMINATION_FUTURE_COUNT() : 0);
        }

        createHeaderRow(sheet, rowCount);
        rowCount = rowCount + 1;
        for (final C2yReport c : c2yTrends) {
            final XSSFRow row = sheet.createRow(rowCount++);
            int colIndex = 0;
            row.createCell(colIndex++).setCellValue(null != c.getDATUM() ? dateFormat.format(c.getDATUM()) : null);
            row.createCell(colIndex++).setCellValue(null != c.getOutportConnCount() ? c.getOutportConnCount() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getActiveConnCount() ? c.getActiveConnCount() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getActiveCusCount() ? c.getActiveCusCount() : 0);
            row.createCell(colIndex++).setCellValue(null != c.getActiveDlrCount() ? c.getActiveDlrCount() : 0);
        }
        return workbook;
    }

    private void createHeaderRowForTodaysTrend(XSSFSheet sheet, int rowCount) {
        final CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        final Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        final Row row = sheet.createRow(rowCount);
        createCell(cellStyle, row, 0, "ACTIVE_CONN_COUNT");
        createCell(cellStyle, row, 1, "ACTIVE_CUS_COUNT");
        createCell(cellStyle, row, 2, "ACTIVE_DLR_COUNT");

    }

    private void createHeaderRowForConnectionType(XSSFSheet sheet, int rowCount) {
        final CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        final Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        final Row row = sheet.createRow(rowCount);
        createCell(cellStyle, row, 0, "TYPE");
        createCell(cellStyle, row, 1, "COUNT");
    }

    private void createHeaderRowForOutport(XSSFSheet sheet, int rowCount) {
        final CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        final Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        final Row row = sheet.createRow(rowCount);
        createCell(cellStyle, row, 0, "MONTH");
        createCell(cellStyle, row, 1, "OUTPORT_FUTURE_COUNT");
    }

    private void createHeaderRowForTermination(XSSFSheet sheet, int rowCount) {
        final CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        final Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        final Row row = sheet.createRow(rowCount);
        createCell(cellStyle, row, 0, "MONTH");
        createCell(cellStyle, row, 1, "TERMINATION_FUTURE_COUNT");

    }

    private void createHeaderRow(XSSFSheet sheet, int rowCount) {
        final CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        final Font font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        final Row row = sheet.createRow(rowCount);
        createCell(cellStyle, row, 0, "DATUM");
        createCell(cellStyle, row, 1, "OUTPORTED_CONN_COUNT");
        createCell(cellStyle, row, 2, "ACTIVE_CONN_COUNT");
        createCell(cellStyle, row, 3, "ACTIVE_CUS_COUNT");
        createCell(cellStyle, row, 4, "ACTIVE_DLR_COUNT");
        rowCount++;

    }

    private void createCell(final CellStyle cellStyle, final Row row, int cellIndex, String value) {
        final Cell titleCell1 = row.createCell(cellIndex);
        titleCell1.setCellStyle(cellStyle);
        titleCell1.setCellValue(value);
    }

}
