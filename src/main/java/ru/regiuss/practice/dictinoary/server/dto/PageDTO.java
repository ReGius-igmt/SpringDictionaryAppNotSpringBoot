package ru.regiuss.practice.dictinoary.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
public class PageDTO<T> {
    private Collection<T> items;
    private long total;
    private int count;
    private int skip;

    public PageDTO(Collection<T> items, long total, int skip) {
        this.items = items;
        this.total = total;
        this.count = items.size();
        this.skip = skip;
    }
}
