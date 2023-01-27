package com.opencbs.genesis.export.views.factories;

import com.opencbs.genesis.dto.ApplicationSimpleDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.PageImpl;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by alopatin on 11-May-17.
 */
public class ApplicationExcellExporter extends ExcellExporterBase {

    private List<ApplicationSimpleDto> applications;

    public ApplicationExcellExporter(Object data, Workbook workbook, HttpServletResponse response) {
        super(data, workbook, response);
    }

    @Override
    protected void addHeaders() {
        addHeaderCell("Application Name");
        addHeaderCell("Workflow");
        addHeaderCell("Profile Name");
        addHeaderCell("Current State");
        addHeaderCell("State Responsible User");
        addHeaderCell("Responsible User");
        addHeaderCell("Date Created");
        addHeaderCell("Created By");
    }

    @Override
    protected void addDataRows() {
        int rowCount = 1;
        DateFormat df = new SimpleDateFormat("MMM d, yyyy");

        for(ApplicationSimpleDto app : this.applications){
            Row userRow = this.getSheet().createRow(rowCount++);
            userRow.createCell(0).setCellValue(app.getName());
            userRow.createCell(1).setCellValue(app.getWorkflowInfo().getName());
//            userRow.createCell(2).setCellValue(app.getProfile().getFullName());
            userRow.createCell(3).setCellValue(app.getCurrentState().getName());
            userRow.createCell(4).setCellValue(app.getCurrentState().getResponsibleUsers());
            userRow.createCell(5).setCellValue(app.getResponsibleUser().getName());
            userRow.createCell(6).setCellValue(df.format(app.getCreatedDate()));
            userRow.createCell(7).setCellValue(app.getCreatedUser().getName());
        }
    }

    @Override
    protected String getFileName() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        return String.format("Applications%s", df.format(date));
    }

    @Override
    protected String getSheetName() {
        return "Applications";
    }

    @Override
    protected boolean castData() {
       try {
           applications = (List<ApplicationSimpleDto>) ((PageImpl)this.data).getContent();
           return true;
       }
       catch (Exception ex){
           return false;
       }
    }
}
