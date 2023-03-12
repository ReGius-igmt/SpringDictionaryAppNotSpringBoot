package ru.regiuss.practice.dictinoary.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.regiuss.practice.dictinoary.server.model.Word;
import ru.regiuss.practice.dictinoary.server.repository.WordRepository;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    @Transactional
    public void delete(int id) {
        Word word = wordRepository.findById(id);
        if(word == null)
            throw new RuntimeException(String.format("word not found with id: %s", id));
        wordRepository.delete(word);
    }

    @Transactional
    public Word edit(Word word) {
        if(word.getKey() == null || word.getKey().isEmpty())
            throw new RuntimeException("key cant been empty");
        if(word.getValues() == null || word.getValues().isEmpty())
            throw new RuntimeException("value cant been empty");
        Word fromDB = wordRepository.findById(word.getId());
        if(fromDB == null) throw new RuntimeException(String.format("word id - [%s] not exist", word.getId()));
        fromDB.setValues(word.getValues());
        fromDB.setKey(word.getKey());
        wordRepository.save(fromDB);
        return fromDB;
    }

    public Word get(int wordID) {
        Word w = wordRepository.findById(wordID);
        if(w == null)
            throw new RuntimeException(String.format("word id - [%s] not exist", wordID));
        return w;
    }
}
