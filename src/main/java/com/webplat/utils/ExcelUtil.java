package com.webplat.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    private static final String FILE_PATH = "TestResults.xlsx";
    private static Workbook workbook;
    private static Sheet sheet;

    static {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Results");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Test Case");
            header.createCell(1).setCellValue("Result");
            header.createCell(2).setCellValue("Screenshot Path");
            header.createCell(3).setCellValue("HTML Report Path");
        }
    }

    public static void writeResult(String testCase, String result, String screenshotPath, String reportPath) {
        int rowCount = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(result);
        row.createCell(2).setCellValue(screenshotPath);
        row.createCell(3).setCellValue(reportPath);

        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
