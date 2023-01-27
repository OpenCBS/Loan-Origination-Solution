package com.opencbs.genesis.converters;

import com.opencbs.genesis.domain.Worklog;
import com.opencbs.genesis.dto.requests.WorklogRequest;

/**
 * Created by alopatin on 16-May-17.
 */
public class WorklogConverter {
    public static Worklog toEntity (WorklogRequest worklogRequest){
        Worklog worklog = new Worklog();
        worklog.setNote(worklogRequest.getNote());
        worklog.setSpentHours(worklogRequest.getSpentHours());
        worklog.setEnteredDate(worklogRequest.getEnteredDate());

        return worklog;
    }
}
