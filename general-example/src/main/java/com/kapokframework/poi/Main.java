package com.kapokframework.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        URL url = Main.class.getClassLoader().getResource("excel/MRT_2023_Forecast9_Adjusted.xlsm");
        if (url != null) {
            File outPutfile = new File("D:\\MRT_2023_Forecast9_Adjusted_1.xlsm");
            try (
                    XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(new File(url.getFile()));
                    FileOutputStream fileOutputStream = new FileOutputStream(outPutfile)
            ) {
                workbook.removeSheetAt(1);
                Sheet sheet = workbook.getSheetAt(0);
                sheet.setColumnHidden(15, true);
                sheet.setColumnHidden(16, true);
                sheet.setColumnHidden(17, true);
                sheet.setColumnHidden(18, true);
                sheet.setColumnHidden(19, true);
                sheet.setColumnHidden(20, true);
                sheet.setColumnHidden(21, true);
                sheet.setColumnHidden(22, true);
                sheet.setColumnHidden(23, true);
                sheet.setColumnHidden(24, true);
                sheet.setColumnHidden(25, true);
                sheet.setColumnHidden(26, true);
                workbook.write(fileOutputStream);
            }
        }
    }

    private static void deleteColumn(Sheet sheet, int columnToDelete) {
        for (int rId = 0; rId <= sheet.getLastRowNum(); rId++) {
            Row row = sheet.getRow(rId);
            for (int cID = columnToDelete; cID <= row.getLastCellNum(); cID++) {
                Cell cOld = row.getCell(cID);
                if (cOld != null) {
                    row.removeCell(cOld);
                }
                Cell cNext = row.getCell(cID + 1);
                if (cNext != null) {
                    Cell cNew = row.createCell(cID, cNext.getCellType());
                    cloneCell(cNew, cNext);
                    if (rId == 0) {
                        sheet.setColumnWidth(cID, sheet.getColumnWidth(cID + 1));
                    }
                }
            }
        }
    }

    private static void cloneCell(Cell cNew, Cell cOld) {
        cNew.setCellComment(cOld.getCellComment());
        cNew.setCellStyle(cOld.getCellStyle());
        if (CellType.BOOLEAN == cNew.getCellType()) {
            cNew.setCellValue(cOld.getBooleanCellValue());
        } else if (CellType.NUMERIC == cNew.getCellType()) {
            cNew.setCellValue(cOld.getNumericCellValue());
        } else if (CellType.STRING == cNew.getCellType()) {
            cNew.setCellValue(cOld.getStringCellValue());
        } else if (CellType.ERROR == cNew.getCellType()) {
            cNew.setCellValue(cOld.getErrorCellValue());
        } else if (CellType.FORMULA == cNew.getCellType()) {
            cNew.setCellValue(cOld.getCellFormula());
        }
    }

}
