package com.kapokframework.poi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private static final float LINE_HEIGHT = 18.5f;

    private static final String[] TITLE_1 = {"Cost Centre Code", "Cost Center Name", "Account Code", "Account Name", "Sub Account Code", "Sub Account Name", "ICP Code", "PD Category Code", "Channel Code", "Region Code", "PD Code", "PD Name", "Brand Code", "Control Type", "Budget Category"};

    private static final String[] TITLE_2 = {"Total", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private static final String[] JSONOBJECT_KEY_1 = {"costCenterCode", "costCenterName", "accountCode", "accountName", "subAccountCode", "subAccountName", "icp", "pdCategoryCode", "channel", "regionCode", "pdCode", "pdName", "brandCode", "controlType", "budgetCategory"};

    private static final String[] JSONOBJECT_KEY_2 = {"adjAmountTotal", "adjAmountJan", "adjAmountFeb", "adjAmountMar", "adjAmountApr", "adjAmountMay", "adjAmountJun", "adjAmountJul", "adjAmountAug", "adjAmountSep", "adjAmountOct", "adjAmountNov", "adjAmountDec"};

    private static final String[] JSONOBJECT_KEY_3 = {"-", "iexpAdjustedJan", "iexpAdjustedFeb", "iexpAdjustedMar", "iexpAdjustedApr", "iexpAdjustedMay", "iexpAdjustedJun", "iexpAdjustedJul", "iexpAdjustedAug", "iexpAdjustedSep", "iexpAdjustedOct", "iexpAdjustedNov", "iexpAdjustedDec"};

    private static final String[] JSONOBJECT_KEY_4 = {"-", "budCatAdjustedJan", "budCatAdjustedFeb", "budCatAdjustedMar", "budCatAdjustedApr", "budCatAdjustedMay", "budCatAdjustedJun", "budCatAdjustedJul", "budCatAdjustedAug", "budCatAdjustedSep", "budCatAdjustedOct", "budCatAdjustedNov", "budCatAdjustedDec"};


    public static void main(String[] args) throws Exception {

        URL url = Main1.class.getClassLoader().getResource("json/mobile_approve.json");

        if (url != null) {
            List<JSONObject> jsonObjectList = JSON.parseObject(url, new TypeReference<List<JSONObject>>() {}.getType());

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {

                XSSFSheet sheet = workbook.createSheet("Sheet1");

                sheet.setColumnWidth(0, 18 * CHARACTER);
                sheet.setColumnWidth(1, 60 * CHARACTER);
                sheet.setColumnWidth(2, 10 * CHARACTER);
                sheet.setColumnWidth(3, 25 * CHARACTER);
                sheet.setColumnWidth(4, 25 * CHARACTER);
                sheet.setColumnWidth(5, 25 * CHARACTER);

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

                    // iplnBudgetAdjustDetail 中 budgetCategory 为空时，设置值为 "/"
                    String budgetCategory = jsonObject.getString("budgetCategory");
                    if (StringUtils.isBlank(budgetCategory)) {
                        jsonObject.put("budgetCategory", "/");
                    }

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
                        row.setHeightInPoints((j == 1 ? LINE_HEIGHT * 2 : LINE_HEIGHT));
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
                            XSSFCell cellC = row.createCell(2);
                            XSSFCellStyle cell12Style = workbook.createCellStyle();
                            cell12Style.cloneStyleFrom(title_1Style);
                            cell12Style.setAlignment(HorizontalAlignment.CENTER);
                            cellC.setCellStyle(cell12Style);

                            XSSFCell cellD = row.createCell(3);
                            XSSFCellStyle cell13Style = workbook.createCellStyle();
                            cell13Style.cloneStyleFrom(title_2Style);
                            cell13Style.setAlignment(HorizontalAlignment.CENTER);
                            cellD.setCellStyle(cell13Style);
                            cellD.setCellValue("ADJ");

                            XSSFCell cellE = row.createCell(4);
                            XSSFCellStyle cell14Style = workbook.createCellStyle();
                            cell14Style.cloneStyleFrom(title_4Style);
                            cell14Style.setAlignment(HorizontalAlignment.CENTER);
                            cell14Style.setWrapText(true);
                            cellE.setCellStyle(cell14Style);
                            cellE.setCellValue("Single COA iExpense\nAvailable Budget-Adjusted");

                            XSSFCell cellF = row.createCell(5);
                            XSSFCellStyle cell15Style = workbook.createCellStyle();
                            cell15Style.cloneStyleFrom(title_4Style);
                            cell15Style.setAlignment(HorizontalAlignment.CENTER);
                            cell15Style.setWrapText(true);
                            cellF.setCellStyle(cell15Style);
                            cellF.setCellValue("Budget Category iExpense\nAvailable Amount - Adjusted");
                        }

                        if (j > 1) {
                            XSSFCellStyle numStyle = workbook.createCellStyle();
                            numStyle.cloneStyleFrom(style);
                            DataFormat dataFormat = workbook.createDataFormat();
                            numStyle.setAlignment(HorizontalAlignment.RIGHT);
                            numStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

                            XSSFCell cellC = row.createCell(2);
                            cellC.setCellStyle(title_1Style);
                            cellC.setCellValue(TITLE_2[j - 2]);

                            XSSFCell cellD = row.createCell(3);
                            cellD.setCellStyle(numStyle);
                            cellD.setCellValue(jsonObject.getDoubleValue(JSONOBJECT_KEY_2[j - 2]));

                            XSSFCell cellE = row.createCell(4);
                            cellE.setCellStyle(numStyle);
                            if (j == 2) {
                                cellE.setCellValue("/");
                            } else {
                                cellE.setCellValue(jsonObject.getDoubleValue(JSONOBJECT_KEY_3[j - 2]));
                            }

                            XSSFCell cellF = row.createCell(5);
                            cellF.setCellStyle(numStyle);
                            if (j == 2 || StringUtils.isBlank(budgetCategory)) {
                                cellF.setCellValue("/");
                            } else {
                                cellF.setCellValue(jsonObject.getDoubleValue(JSONOBJECT_KEY_4[j - 2]));
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
