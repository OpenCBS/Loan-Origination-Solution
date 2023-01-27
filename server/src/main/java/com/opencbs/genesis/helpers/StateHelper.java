package com.opencbs.genesis.helpers;

import com.opencbs.genesis.domain.State;
import com.opencbs.genesis.domain.enums.StateType;

/**
 * Created by Makhsut Islamov on 14.11.2016.
 */
public class StateHelper {
    public static boolean isCompleted(State source){
        return source.getStateType() == StateType.COMPLETE
                || source.getStateType() == StateType.CANCELLED
                || source.getStateType() == StateType.DENIED;
    }
}