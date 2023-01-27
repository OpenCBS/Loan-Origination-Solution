package com.opencbs.genesis.export.views;

import com.opencbs.genesis.domain.enums.ExportEntity;
import com.opencbs.genesis.export.views.factories.ApplicationExcellExporter;
import com.opencbs.genesis.export.views.factories.ExcellExporterBase;
import com.opencbs.genesis.export.views.factories.VolunteerExcellExporter;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * Created by alopatin on 11-May-17.
 */
public class ExcelView  extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        ExcellExporterBase excelExporter = null;

        for (Map.Entry<String, Object> entity : model.entrySet()) {
            if (Objects.equals(entity.getKey(), ExportEntity.APPLICATIONS.toString())) {
                excelExporter = new ApplicationExcellExporter(entity.getValue(), workbook, response);
                break;
            }

            if (Objects.equals(entity.getKey(), ExportEntity.VOLUNTEERS.toString())) {
                excelExporter = new VolunteerExcellExporter(entity.getValue(), workbook, response);
                break;
            }
        }

        if (excelExporter != null) {
            excelExporter.export();
        }
    }
}
