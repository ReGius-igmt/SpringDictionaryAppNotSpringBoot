package ru.regiuss.practice.dictinoary.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Utils {
    public static <T, R> Map<Integer, R> arrayToMap(T[] values, Function<T, R> mapper) {
        Map<Integer, R> map = new HashMap<>(values.length<<1);
        for (int i = 0; i < values.length; i++) map.put(i+1, mapper.apply(values[i]));
        return map;
    }
}