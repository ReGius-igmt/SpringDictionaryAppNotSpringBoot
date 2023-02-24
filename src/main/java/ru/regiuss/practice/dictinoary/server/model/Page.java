package ru.regiuss.practice.dictinoary.server.model;

import lombok.Data;

import java.util.Collection;

@Data
public class Page<T> {
    private Collection<T> items;
    private long total;
    private int count;
    private int skip;

    public Page(Collection<T> items, long total, int skip) {
        this.items = items;
        this.total = total;
        this.count = items.size();
        this.skip = skip;
    }
}
