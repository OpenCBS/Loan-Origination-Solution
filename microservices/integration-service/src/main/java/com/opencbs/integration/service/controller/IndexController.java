package com.opencbs.integration.service.controller;

import com.opencbs.integration.service.ClientValidator;
import com.opencbs.integration.service.dto.Client;
import com.opencbs.integration.service.dto.FieldValue;
import com.opencbs.integration.service.dto.OnlineApplyDto;
import com.opencbs.integration.service.component.RestClientComponent;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class IndexController {

    @Value("${genesis.integration.url}")
    private String genesisUrl;

    private final RestClientComponent restClientComponent;
    private final ClientValidator clientValidator;

    @Autowired
    public IndexController(RestClientComponent restClientComponent,
                           ClientValidator clientValidator) {
        this.restClientComponent = restClientComponent;
        this.clientValidator = clientValidator;
    }


    @RequestMapping(value = "/", method = GET)
    public String index(Model model) {
        model.addAttribute("client", new Client());
        return "index";
    }

    @RequestMapping(value = "/index-fr", method = GET)
    public String indexFrench(Model model) {
        model.addAttribute("client", new Client());
        return "index-fr";
    }

    @RequestMapping(value = "/apply", method = POST)
    public String apply(@ModelAttribute("client") @Valid Client client) {
        this.clientValidator.validate(client);
        log.info(client.toString());
        this.restClientComponent.post(genesisUrl, this.getDto(client), String.class);
        return "redirect:success";
    }

    @RequestMapping(value = "/success")
    public String success() {
        return "success";
    }

    private OnlineApplyDto getDto(Client client) {
        OnlineApplyDto onlineApplyDto = new ModelMapper().map(client, OnlineApplyDto.class);
        List<FieldValue> fieldValues = new ArrayList<>();
        FieldValue fieldValue = new FieldValue();
        fieldValue.setFieldId(2);
        fieldValue.setValue(client.getAddress());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(3);
        fieldValue.setValue(client.getPostalCode());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(4);
        fieldValue.setValue(client.getCity());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(5);
        fieldValue.setValue(client.getDateOfBirth());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(7);
        fieldValue.setValue(client.getPlaceOfBirth());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(8);
        fieldValue.setValue(client.getCitizenship());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(10);
        fieldValue.setValue(client.getLanguage());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(12);
        fieldValue.setValue(client.getHearAbout());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(14);
        fieldValue.setValue(client.getCompanyName());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(16);
        fieldValue.setValue(client.getCompanyCreationDate());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(17);
        fieldValue.setValue(client.getCompanyStatus());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(18);
        if (client.getNumberOfEmployees() == null) fieldValue.setValue("");
        else fieldValue.setValue(String.valueOf(client.getNumberOfEmployees()));
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(21);
        fieldValue.setValue(client.getProjectDescription());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(29);
        fieldValue.setValue(client.getRequestedAmount());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(30);
        fieldValue.setValue(client.getPurposeOfCredit());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(31);
        fieldValue.setValue(client.getPreviousCredit());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(32);
        fieldValue.setValue(String.valueOf(client.getRepayEachMonth()));
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(35);
        fieldValue.setValue(client.getFamilyStatus());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(36);
        fieldValue.setValue(client.getEducation());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(37);
        fieldValue.setValue(client.getProfessionalSituation());
        fieldValues.add(fieldValue);
        fieldValue = new FieldValue();
        fieldValue.setFieldId(50);
        fieldValue.setValue(client.getCountry());
        fieldValues.add(fieldValue);

        fieldValues.forEach((FieldValue x) -> {
            if (x.getValue() == null) {
                x.setValue("");
            }
        });
        onlineApplyDto.setFields(fieldValues);
        return onlineApplyDto;
    }
}