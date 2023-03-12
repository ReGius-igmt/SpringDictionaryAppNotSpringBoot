package ru.regiuss.practice.dictinoary.server.model;

import lombok.Data;

@Data
public class DictionaryFilter {
    private String dictionary;
    private int skip;
    private int count;
    private String search;
}
