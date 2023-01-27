package com.opencbs.genesis.helpers;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
public class ListHelper {
    public static <T> T findFirst(List<T> source, Predicate<T> filter){
        return filter(source, filter).findFirst().get();
    }

    public static <T> T findFirstOrDefault(List<T> source, Predicate<T> filter){
        if(CollectionUtils.isEmpty(source)) return null;

        Optional<T> optional = filter(source, filter).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public static <T> List<T> find(List<T> source, Predicate<T> filter) {
        if (CollectionUtils.isEmpty(source)) return null;
        return filter(source, filter).collect(Collectors.toList());
    }


    private static <T> Stream<T> filter(List<T> source, Predicate<T> filter){
        return source.stream().filter(filter);
    }
}