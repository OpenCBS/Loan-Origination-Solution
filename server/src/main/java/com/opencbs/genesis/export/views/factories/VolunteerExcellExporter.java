package com.opencbs.genesis.export.views.factories;

import com.opencbs.genesis.domain.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by alopatin on 28-Jun-17.
 */
public class VolunteerExcellExporter extends ExcellExporterBase {
    private List<User> volunteers;

    public VolunteerExcellExporter(Object data, Workbook workbook, HttpServletResponse response) {
        super(data, workbook, response);
    }

    @Override
    protected void addHeaders() {
        addHeaderCell("First Name");
        addHeaderCell("Last Name");
        addHeaderCell("Phone Number");
        addHeaderCell("Mobile Number");
        addHeaderCell("Location");
        addHeaderCell("Street");
        addHeaderCell("Postal Code");
        addHeaderCell("E-mail");
        addHeaderCell("Current occupation");
        addHeaderCell("Citizenship");
        addHeaderCell("Spoken languages");
        addHeaderCell("Preferred working language");
        addHeaderCell("Area(s) of specialisation");
        addHeaderCell("Availability per week");
        addHeaderCell("Have you already carried out volunteering?");
        addHeaderCell("Type of mentoring or activity you would be most interested in");
    }

    @Override
    protected void addDataRows() {
        int rowCount = 1;

        for(User volunteer : this.volunteers){
            Row row = this.getSheet().createRow(rowCount++);
            row.createCell(0).setCellValue(volunteer.getFirstName());
            row.createCell(1).setCellValue(volunteer.getLastName());
            row.createCell(2).setCellValue(volunteer.getPhoneNumber());
            row.createCell(3).setCellValue(volunteer.getMobilePhone());
            row.createCell(4).setCellValue(volunteer.getAddress());
            row.createCell(5).setCellValue(volunteer.getStreet());
            row.createCell(6).setCellValue(volunteer.getPostalCode());
            row.createCell(7).setCellValue(volunteer.getEmail());
            row.createCell(8).setCellValue(volunteer.getCurrentOccupation());
            row.createCell(9).setCellValue(volunteer.getCitizenship());
            row.createCell(10).setCellValue(volunteer.getSpokenLanguages());
            row.createCell(11).setCellValue(volunteer.getPreferredWorkingLanguages());
            row.createCell(12).setCellValue(volunteer.getSpecialization());
            row.createCell(13).setCellValue(volunteer.getAvailability());
            row.createCell(14).setCellValue(volunteer.getAlreadyVolunteered());

            String text = "";
            boolean added = false;
            if(!StringUtils.isEmpty(volunteer.getSupport())){
                text+=volunteer.getSupport().replace("-"," ");
                added = true;
            }

            if(!StringUtils.isEmpty(volunteer.getSupportOther())){
                if(added){
                    text+=", ";
                }

                added = true;
                text+= volunteer.getSupportOther();
            }

            if(!StringUtils.isEmpty(volunteer.getSupportPromoters())){
                if(added){
                    text+=", ";
                }
                added = true;
                text+= volunteer.getSupportPromoters().replace("-"," ");
            }


            if(!StringUtils.isEmpty( volunteer.getSupportPromotersOther())){
                if(added){
                    text+=", ";
                }
                text+=  volunteer.getSupportPromotersOther();
            }

            row.createCell(12).setCellValue(String.join(", ", text));
        }
    }

    @Override
    protected String getFileName() {
        return "VolunteersReport";
    }

    @Override
    protected String getSheetName() {
        return "Volunteers";
    }

    @Override
    protected boolean castData() {
        try {
            volunteers = (List<User>) this.data;
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
