package ru.regiuss.practice.dictinoary.server.mapper;

import org.springframework.stereotype.Component;
import ru.regiuss.practice.dictinoary.server.dto.WordDTO;
import ru.regiuss.practice.dictinoary.server.model.Word;

import java.util.function.Function;

@Component
public class WordMapper implements Function<Word, WordDTO> {
    @Override
    public WordDTO apply(Word word) {
        WordDTO dto = new WordDTO();
        dto.setKey(word.getKey());
        dto.setValue(word.getValue());
        return dto;
    }
}
