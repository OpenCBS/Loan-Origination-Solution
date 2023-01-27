package com.opencbs.genesis.export.viewResolver;

import com.opencbs.genesis.export.views.ExcelView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Created by alopatin on 11-May-17.
 */
public class ExcellViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new ExcelView();
    }
}
