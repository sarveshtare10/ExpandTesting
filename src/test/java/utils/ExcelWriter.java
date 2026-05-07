package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public static void writeToExcel(String filePath, String sheetName, String username, String password) {
        Workbook workbook;
        Sheet sheet;
        
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
                // Create header row if sheet is new
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Username");
                header.createCell(1).setCellValue("Password");
            }
        } catch (IOException e) {
            // File doesn't exist, create a new workbook and sheet
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(sheetName);
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Username");
            header.createCell(1).setCellValue("Password");
        }

        int lastRowNum = sheet.getLastRowNum();
        Row newRow = sheet.createRow(lastRowNum + 1);
        newRow.createCell(0).setCellValue(username);
        newRow.createCell(1).setCellValue(password);

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to Excel file at: " + filePath, e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}