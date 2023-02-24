package ru.regiuss.practice.dictinoary.server.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class Dictionary {
    @Getter
    private final String name;
    private final Function<String, Boolean> validator;

    public Boolean validate(String key) {
        if(validator == null) return false;
        return validator.apply(key);
    }
}
