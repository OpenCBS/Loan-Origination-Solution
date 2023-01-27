package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.dto.FieldDto;
import com.opencbs.genesis.dto.FieldSectionDto;
import com.opencbs.genesis.dto.requests.FieldValue;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.ListHelper;
import com.opencbs.genesis.repositories.ApplicationFieldValueRepository;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.repositories.StateFieldRepository;
import com.opencbs.genesis.services.ApplicationFieldService;
import com.opencbs.genesis.validators.UpdateApplicationFieldsValidator;
import com.opencbs.genesis.validators.UpdateCurrentStateFieldsValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import com.opencbs.genesis.validators.helpers.PermissionValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
@Service
public class ApplicationFieldServiceImpl extends BaseService implements ApplicationFieldService {
    private final StateFieldRepository stateFieldRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationFieldValueRepository applicationFieldValueRepository;

    @Autowired
    public ApplicationFieldServiceImpl(StateFieldRepository stateFieldRepository,
                                       ApplicationRepository applicationRepository,
                                       ApplicationFieldValueRepository applicationFieldValueRepository) {
        this.stateFieldRepository = stateFieldRepository;
        this.applicationRepository = applicationRepository;
        this.applicationFieldValueRepository = applicationFieldValueRepository;
    }

    @Override
    public List<FieldSectionDto> getCurrentStateFields(Long id, User principal) throws ApiException {
        Application application = applicationRepository.findOne(id);
        Assert.notNull(application, String.format("Application with {id: %d} not found", id));
        PermissionValidatorHelper.validateCurrentStatePermissions(application, principal);

        List<StateField> currentStateFields = stateFieldRepository.findByStateId(
                application.getCurrentState().getId()
        );

        return stateFieldsToListDto(currentStateFields, application.getFieldValues());
    }

    @Override
    public List<FieldSectionDto> getAllFields(Long id) throws ApiException {
        Application application = applicationRepository.findOne(id);
        Assert.notNull(application, String.format("Application with {id: %d} not found", id));

        return toListDto(application.getWorkflow().getSections(), application.getFieldValues());
    }

    @Override
    @Transactional
    @ValidateWith(UpdateCurrentStateFieldsValidator.class)
    public List<FieldSectionDto> updateCurrentStateFields(Long id, List<FieldValue> fieldValues, User principal) throws ApiException {
        Application application = applicationRepository.findOne(id);
        List<StateField> currentStateFields = stateFieldRepository.findByStateId(
                application.getCurrentState().getId()
        );

        if (CollectionUtils.isEmpty(currentStateFields)) return new ArrayList<>();

        currentStateFields.forEach(stateField -> {
            FieldValue readOnlyFieldValue = ListHelper.findFirstOrDefault(fieldValues,
                    x -> stateField.isReadOnly() && stateField.getField().getId().equals(x.getFieldId())
            );

            if (readOnlyFieldValue == null) return;
            fieldValues.remove(readOnlyFieldValue);
        });

        List<WorkflowField> fields = currentStateFields.stream().map(StateField::getField).collect(Collectors.toList());
        List<ApplicationFieldValue> applicationFieldValues = updateFieldsInternal(fieldValues, fields, application);

        return stateFieldsToListDto(currentStateFields, applicationFieldValues);
    }

    @Override
    @Transactional
    @ValidateWith(UpdateApplicationFieldsValidator.class)
    public List<FieldSectionDto> updateFields(Long id, List<FieldValue> fieldValues) throws ApiException {
        Application application = applicationRepository.findOne(id);

        List<WorkflowField> fields = application.getWorkflow()
                .getSections().stream().map(WorkflowFieldSection::getFields)
                .flatMap(List::stream).collect(Collectors.toList());

        List<ApplicationFieldValue> applicationFieldValues =
                updateFieldsInternal(fieldValues, fields, application);
        return toListDto(application.getWorkflow().getSections(), applicationFieldValues);
    }


    private List<FieldSectionDto> stateFieldsToListDto(List<StateField> stateFields, List<ApplicationFieldValue> fieldValues) {
        Map<WorkflowFieldSection, List<StateField>> groupedStateFields = stateFields.stream()
                .collect(Collectors.groupingBy(x -> x.getField().getSection()));

        return groupedStateFields.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey().getOrder()))
                .map(group -> {
                    List<FieldDto> fieldDtoList = group.getValue().stream().sorted(Comparator.comparing(x -> x.getField().getOrder()))
                            .map(stateField -> toDto(stateField.getField(), findValue(fieldValues, stateField.getField()), stateField.isReadOnly()))
                            .collect(Collectors.toList());

                    return toFieldSectionDto(group.getKey(), fieldDtoList);
                }).collect(Collectors.toList());
    }

    private List<FieldSectionDto> toListDto(List<WorkflowFieldSection> sections, List<ApplicationFieldValue> fieldValues) {
        return sections.stream().sorted(Comparator.comparing(WorkflowFieldSection::getOrder))
                .map(section -> {
                    List<FieldDto> fieldDtoList = section.getFields().stream()
                            .map(field -> toDto(field, findValue(fieldValues, field), false))
                            .collect(Collectors.toList());

                    return toFieldSectionDto(section, fieldDtoList);
                }).collect(Collectors.toList());
    }

    private FieldSectionDto toFieldSectionDto(WorkflowFieldSection section, List<FieldDto> fieldDtoList) {
        return new FieldSectionDto(section.getId(), section.getCaption(), section.getOrder(), fieldDtoList);
    }

    private FieldDto toDto(WorkflowField field, ApplicationFieldValue fieldValue, boolean readOnly) {
        return new FieldDto(
                field,
                fieldValue == null ? null : fieldValue.getValue(),
                readOnly
        );
    }

    private ApplicationFieldValue findValue(List<ApplicationFieldValue> fieldValues, WorkflowField field) {
        return ListHelper.findFirstOrDefault(fieldValues,
                fv -> fv.getField().getId().equals(field.getId()));
    }

    private List<ApplicationFieldValue> updateFieldsInternal(List<FieldValue> newFieldValues,
                                                             List<WorkflowField> workflowFields,
                                                             Application application) {

        if (CollectionUtils.isEmpty(workflowFields)) return new ArrayList<>();

        newFieldValues.forEach(newFieldValue -> {
            WorkflowField workflowField = ListHelper.findFirst(workflowFields, x -> x.getId().equals(newFieldValue.getFieldId()));
            ApplicationFieldValue fieldValue = ListHelper.findFirstOrDefault(application.getFieldValues(),
                    x -> workflowField.getId().equals(x.getField().getId())
            );

            if (fieldValue != null) {
                fieldValue.setValue(newFieldValue.getValue());
            } else {

                if (application.getFieldValues() == null) {
                    application.setFieldValues(new ArrayList<>());
                }

                application.getFieldValues().add(new ApplicationFieldValue(application, workflowField, newFieldValue.getValue()));
            }
        });

        return applicationFieldValueRepository.save(application.getFieldValues());
    }
}