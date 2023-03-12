package ru.regiuss.practice.dictinoary.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {
    private Integer id;
    private String key;
    private Set<String> values;
}
