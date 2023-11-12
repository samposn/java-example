package com.kapokframework.poi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Main1
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main1 {

    private static final int CHARACTER = 256;

    private static final int LINE_HEIGHT = 18;

    private static final String[] TITLE_1 = {"Cost Centre Code", "Cost Center Name", "Account Code", "Account Name", "Sub Account Code", "Sub Account Name", "ICP", "PD Category Code", "PD Category Name", "Channel Code", "Region Code", "PD Code", "Pd Name", "Brand Code", "Control Type"};

    private static final String[] TITLE_2 = {"Total", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private static final String[] JSONOBJECT_KEY_1 = {"costCenterCode", "costCenterName", "accountCode", "accountName", "subAccountCode", "subAccountName", "icp", "pdCategoryCode", "pdCategoryName", "channel", "regionCode", "pdCode", "pdName", "brandCode", "controlType"};

    private static final String[] JSONOBJECT_KEY_2 = {"adjAmountTotal", "adjAmountJan", "adjAmountFeb", "adjAmountMar", "adjAmountApr", "adjAmountMay", "adjAmountJun", "adjAmountJul", "adjAmountAug", "adjAmountSep", "adjAmountOct", "adjAmountNov", "adjAmountDec"};

    private static final String[] JSONOBJECT_KEY_3 = {"iexpAdjustedTotal", "iexpAdjustedJan", "iexpAdjustedFeb", "iexpAdjustedMar", "iexpAdjustedApr", "iexpAdjustedMay", "iexpAdjustedJun", "iexpAdjustedJul", "iexpAdjustedAug", "iexpAdjustedSep", "iexpAdjustedOct", "iexpAdjustedNov", "iexpAdjustedDec"};

    public static void main(String[] args) throws Exception {

        URL url = Main1.class.getClassLoader().getResource("json/mobile_approve.json");

        if (url != null) {
            List<JSONObject> jsonObjectList = JSON.parseObject(url, new TypeReference<List<JSONObject>>() {
            }.getType());

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {

                XSSFSheet sheet = workbook.createSheet("Sheet1");

                sheet.setColumnWidth(0, 20 * CHARACTER);
                sheet.setColumnWidth(1, 70 * CHARACTER);
                sheet.setColumnWidth(2, 12 * CHARACTER);
                sheet.setColumnWidth(3, 20 * CHARACTER);
                sheet.setColumnWidth(4, 12 * CHARACTER);
                sheet.setColumnWidth(5, 20 * CHARACTER);

                // Basic Style
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFont(basicFont(workbook));
                style.setAlignment(HorizontalAlignment.LEFT);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);

                // Title Style
                XSSFCellStyle titleStyle = workbook.createCellStyle();
                titleStyle.cloneStyleFrom(style);
                XSSFFont titleFont = basicFont(workbook);
                titleFont.setBold(true);
                titleStyle.setFont(titleFont);

                XSSFCellStyle title_1Style = workbook.createCellStyle();
                title_1Style.cloneStyleFrom(titleStyle);
                title_1Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                title_1Style.setFillForegroundColor(new XSSFColor(new Color(217, 217, 217), null));

                XSSFCellStyle title_35Style = workbook.createCellStyle();
                title_35Style.cloneStyleFrom(title_1Style);
                XSSFFont title_35Font = basicFont(workbook);
                title_35Font.setBold(true);
                title_35Font.setColor(new XSSFColor(new Color(255, 255, 255), null));
                title_35Style.setFont(title_35Font);

                XSSFCellStyle title_2Style = workbook.createCellStyle();
                title_2Style.cloneStyleFrom(title_35Style);
                title_2Style.setFillForegroundColor(new XSSFColor(new Color(0, 176, 240), null));

                XSSFCellStyle title_4Style = workbook.createCellStyle();
                title_4Style.cloneStyleFrom(title_35Style);
                title_4Style.setFillForegroundColor(new XSSFColor(new Color(146, 208, 80), null));


                IntStream.range(0, jsonObjectList.size()).forEach(i -> {

                    int baseRowNum = i * 18;

                    JSONObject jsonObject = jsonObjectList.get(i);

                    // jsonObject 中缺少 iexpAdjustedTotal，在这里设置
                    jsonObject.put("iexpAdjustedTotal", "/");

                    XSSFRow row1 = sheet.createRow(baseRowNum);
                    row1.setHeightInPoints(LINE_HEIGHT);
                    XSSFCell cell11 = row1.createCell(0);
                    XSSFCellStyle title_0Style = workbook.createCellStyle();
                    title_0Style.cloneStyleFrom(titleStyle);
                    XSSFFont title_0Font = basicFont(workbook);
                    title_0Font.setBold(true);
                    title_0Font.setColor(new XSSFColor(new Color(0, 0, 255), null));
                    title_0Style.setFont(title_0Font);
                    cell11.setCellStyle(title_0Style);
                    cell11.setCellValue("#" + (i + 1));
                    MergeCell(sheet, CellRangeAddress.valueOf("A" + (baseRowNum + 1) + ":F" + (baseRowNum + 1)));

                    IntStream.range(0, TITLE_1.length).forEach(j -> {
                        XSSFRow row = sheet.createRow(j + (baseRowNum + 1));
                        row.setHeightInPoints(LINE_HEIGHT);
                        XSSFCell cellA = row.createCell(0);
                        cellA.setCellStyle(title_1Style);
                        cellA.setCellValue(TITLE_1[j]);

                        XSSFCell cellB = row.createCell(1);
                        cellB.setCellStyle(style);
                        cellB.setCellValue(jsonObject.getString(JSONOBJECT_KEY_1[j]));

                        if (j == 0) {
                            XSSFCell cellC = row.createCell(2);
                            XSSFCellStyle cell02Style = workbook.createCellStyle();
                            cell02Style.cloneStyleFrom(title_1Style);
                            cell02Style.setAlignment(HorizontalAlignment.CENTER);
                            cellC.setCellStyle(cell02Style);
                            cellC.setCellValue("Amount");
                            MergeCell(sheet, CellRangeAddress.valueOf("C" + (baseRowNum + 2) + ":F" + (baseRowNum + 2)));
                        }

                        if (j == 1) {
                            XSSFCell cellC1 = row.createCell(2);
                            XSSFCellStyle cell12Style = workbook.createCellStyle();
                            cell12Style.cloneStyleFrom(title_2Style);
                            cell12Style.setAlignment(HorizontalAlignment.CENTER);
                            cellC1.setCellStyle(cell12Style);
                            cellC1.setCellValue("ADJ");
                            MergeCell(sheet, CellRangeAddress.valueOf("C" + (baseRowNum + 3) + ":D" + (baseRowNum + 3)));

                            XSSFCell cellE1 = row.createCell(4);
                            XSSFCellStyle cell14Style = workbook.createCellStyle();
                            cell14Style.cloneStyleFrom(title_4Style);
                            cell14Style.setAlignment(HorizontalAlignment.CENTER);
                            cellE1.setCellStyle(cell14Style);
                            cellE1.setCellValue("iExpense Available Budget-Adjusted");
                            MergeCell(sheet, CellRangeAddress.valueOf("E" + (baseRowNum + 3) + ":F" + (baseRowNum + 3)));
                        }

                        if (j > 1) {
                            XSSFCellStyle numStyle = workbook.createCellStyle();
                            numStyle.cloneStyleFrom(style);
                            DataFormat dataFormat = workbook.createDataFormat();
                            numStyle.setAlignment(HorizontalAlignment.RIGHT);
                            numStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

                            XSSFCell cellC2 = row.createCell(2);
                            cellC2.setCellStyle(title_2Style);
                            cellC2.setCellValue(TITLE_2[j - 2]);

                            XSSFCell cellD = row.createCell(3);
                            cellD.setCellStyle(numStyle);
                            cellD.setCellValue(jsonObject.getDoubleValue(JSONOBJECT_KEY_2[j - 2]));

                            XSSFCell cellE2 = row.createCell(4);
                            cellE2.setCellStyle(title_4Style);
                            cellE2.setCellValue(TITLE_2[j - 2]);

                            XSSFCell cellF = row.createCell(5);
                            cellF.setCellStyle(numStyle);
                            if (j == 2) {
                                cellF.setCellValue(jsonObject.getString(JSONOBJECT_KEY_3[j - 2]));
                            } else {
                                cellF.setCellValue(jsonObject.getDoubleValue(JSONOBJECT_KEY_3[j - 2]));
                            }
                        }
                    });

                });

                FileOutputStream outputStream = new FileOutputStream("D:\\output.xlsx");
                workbook.write(outputStream);
            }
        }

    }

    private static XSSFFont basicFont(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 9);
        return font;
    }

    private static void MergeCell(Sheet sheet, CellRangeAddress cellAddresses) {
        sheet.addMergedRegion(cellAddresses);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellAddresses, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellAddresses, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellAddresses, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellAddresses, sheet);
    }

}
