package com.opencbs.genesis.services.factories;

import com.opencbs.genesis.helpers.DateHelper;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Makhsut Islamov on 12.12.2016.
 */
public class ApplicationCriterionFactory {
    private static final String PROFILE_NAME = "profileName";
    private static final String STATE_NAME = "stateName";
    private static final String CREATED_USER_ID = "createdUserId";
    private static final String RESPONSIBLE_USER_ID = "responsibleUserId";
    private static final String COMPLETED = "completed";
    private static final String NAME = "name";
    private static final String START_CREATED_DATE = "startCreatedDate";
    private static final String END_CREATED_DATE = "endCreatedDate";
    private static final String STATE_RESPONSIBLE_USER_ID = "stateResponsibleUserId";

    public static Criterion create(Map<String, String> fields){
        List<Criterion> criterionList = new ArrayList<>();
        fields.forEach((key, value) -> {
            Criterion criterion = get(key, value);
            if(null != criterion){
                criterionList.add(criterion);
            }
        });

        return CollectionUtils.isEmpty(criterionList)
                ? null
                : Restrictions.and(criterionList.toArray(new Criterion[criterionList.size()]));
    }

    public static Long getStateResponsibleUserId(Map<String,String> fields){
        if(fields.containsKey(STATE_RESPONSIBLE_USER_ID)){
            return Long.parseLong(fields.get(STATE_RESPONSIBLE_USER_ID));
        }

        return 0L;
    }

    private static Criterion get(String key, String value){
        switch (key){
            case PROFILE_NAME:
                return createProfileCriterion(value);
            case STATE_NAME:
                return createStateCriterion(value);
            case CREATED_USER_ID:
                return createCreatedUserCriterion(value);
            case RESPONSIBLE_USER_ID:
                return createResponsibleUserCriterion(value);
            case COMPLETED:
                return createCompletedCriterion(value);
            case NAME:
                return createNameCriterion(value);
            case START_CREATED_DATE:
                return createStartCreatedDateCriterion(value);
            case END_CREATED_DATE:
                return createEndCreatedDateCriterion(value);
            default:
                return null;
        }
    }

    private static Criterion createProfileCriterion(String value) {
        String[] values = value.split("\\s+");
        if (values.length == 2) {

            Criterion chunkedNamesCriterion = Restrictions.or(
                    Restrictions.and(
                            Restrictions.like("profile.firstName", values[0], MatchMode.ANYWHERE),
                            Restrictions.like("profile.lastName", values[1], MatchMode.ANYWHERE)),
                    Restrictions.and(
                            Restrictions.like("profile.firstName", values[1], MatchMode.ANYWHERE),
                            Restrictions.like("profile.lastName", values[0], MatchMode.ANYWHERE)));

            return Restrictions.or(
                    chunkedNamesCriterion,
                    Restrictions.like("profile.firstName", value, MatchMode.ANYWHERE),
                    Restrictions.like("profile.lastName", value, MatchMode.ANYWHERE)
            );
        }
        
        return Restrictions.or(
                Restrictions.like("profile.firstName", value, MatchMode.ANYWHERE),
                Restrictions.like("profile.lastName", value, MatchMode.ANYWHERE)
        );
    }

    private static Criterion createStateCriterion(String value){
        return Restrictions.like("state.name", value, MatchMode.ANYWHERE);
    }

    private static Criterion createCreatedUserCriterion(String value){
        return Restrictions.eq("createdUser.id", Integer.parseInt(value));
    }

    private static Criterion createResponsibleUserCriterion(String value){
        return Restrictions.eq("responsibleUser.id", Integer.parseInt(value));
    }


    private static Criterion createCompletedCriterion(String value){
        return Restrictions.eq("completed", Boolean.parseBoolean(value));
    }

    private static Criterion createNameCriterion(String value){
        return Restrictions.like("name", value, MatchMode.ANYWHERE);
    }

    private static Criterion createStartCreatedDateCriterion(String value){
        Date date = DateHelper.convert(value);
        return Restrictions.ge("createdDate", DateHelper.getStartOfDay(date));
    }

    private static Criterion createEndCreatedDateCriterion(String value){
        Date date = DateHelper.convert(value);
        return Restrictions.le("createdDate", DateHelper.getEndOfDay(date));
    }
}