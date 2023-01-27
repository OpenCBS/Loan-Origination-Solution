package com.opencbs.genesis.export.views.factories;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by alopatin on 11-May-17.
 */
public abstract class ExcellExporterBase {
    protected Object data;

    HttpServletResponse response;
    Workbook workbook;

    private CellStyle cellStyle;
    private Sheet sheet;
    private Row header;
    private int columnIndex;

    ExcellExporterBase(Object data, Workbook workbook, HttpServletResponse response) {
        this.data = data;
        this.response = response;
        this.workbook = workbook;
        this.columnIndex = 0;
    }

    public void export() {
        if (!castData()) {
            return;
        }

        this.sheet = prepareExport();

        createCellStyle();
        createHeaderRow();
        addHeaders();
        addDataRows();
    }

    private Sheet prepareExport() {
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.xls\"", getFileName()));
        Sheet sheet = this.workbook.createSheet(getSheetName());
        sheet.setDefaultColumnWidth(30);

        return sheet;
    }

    protected abstract void addHeaders();
    protected abstract void addDataRows();
    protected abstract String getFileName();
    protected abstract String getSheetName();
    protected abstract boolean castData();

    Sheet getSheet() {
        return this.sheet;
    }

    void addHeaderCell(String title) {
        header.createCell(this.columnIndex).setCellValue(title);
        header.getCell(this.columnIndex).setCellStyle(this.cellStyle);

        this.columnIndex++;
    }

    private void createCellStyle() {
        this.cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        this.cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        this.cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        this.cellStyle.setFont(font);
    }

    private void createHeaderRow() {
        this.header = sheet.createRow(0);
    }
}
