package com.opencbs.genesis.validators.helpers;

/**
 * Created by Makhsut Islamov on 08.11.2016.
 */
public class ParamsHelper {
    public static Object get(Object[] source, int index){
        if(null != source && source.length > index){
            return source[index];
        }
        return null;
    }

    public static <T> T getAs(Object[] source, int index){
       return (T)get(source, index);
    }
}
