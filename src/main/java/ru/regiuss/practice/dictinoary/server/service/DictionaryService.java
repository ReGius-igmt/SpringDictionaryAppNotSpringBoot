package ru.regiuss.practice.dictinoary.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.regiuss.practice.dictinoary.server.model.Dictionary;
import ru.regiuss.practice.dictinoary.server.model.DictionaryFilter;
import ru.regiuss.practice.dictinoary.server.model.Page;
import ru.regiuss.practice.dictinoary.server.model.Word;
import ru.regiuss.practice.dictinoary.server.repository.WordRepository;
import ru.regiuss.practice.dictinoary.server.repository.WordRepositoryImpl;

import java.util.regex.Pattern;

@Service
public class DictionaryService {

    private final WordRepository wordRepository;
    private final Dictionary[] dictionaries;

    public DictionaryService(WordRepositoryImpl wordRepository) {
        this.wordRepository = wordRepository;
        this.dictionaries = new Dictionary[] {
                new Dictionary("DIGIT", key -> {
                    if(key == null) throw new RuntimeException("key cant been null");
                    if(key.length() != 5 || !Pattern.matches("[0-9]+", key))
                        throw new RuntimeException("Ключ должен быть длиной 5 символов и состоять только из цифр");
                    return false;
                }),
                new Dictionary("LATIN", key -> {
                    if(key == null) throw new RuntimeException("key cant been null");
                    if(key.length() != 4 || !Pattern.matches("[A-Za-z]+", key))
                        throw new RuntimeException("Ключ должен быть длиной 4 символа и состоять только из букв латинского алфавита");
                    return false;
                })
        };
    }

    public Page<Word> getWords(Integer dictionaryID, DictionaryFilter filter) {
        if(dictionaryID == null)
            throw new RuntimeException("dictionary cant been empty");
        if(dictionaryID < 0 || dictionaryID > dictionaries.length-1)
            throw new RuntimeException("dictionary not exist");
        if(filter.getSkip() < 0) filter.setSkip(0);
        if(filter.getCount() < 1) filter.setCount(1);
        else if(filter.getCount() > 20) filter.setCount(20);
        filter.setDictionary(dictionaries[dictionaryID].getName());
        return wordRepository.findAllByDictionary(filter);
    }

    @Transactional
    public Word add(Word word, Integer dictionaryID) {
        if(dictionaryID == null)
            throw new RuntimeException("dictionary cant been empty");
        if(dictionaryID < 0 || dictionaryID > dictionaries.length-1)
            throw new RuntimeException("dictionary not exist");
        if(word.getKey() == null || word.getKey().isEmpty())
            throw new RuntimeException("key cant been empty");
        if(word.getValues() == null || word.getValues().isEmpty())
            throw new RuntimeException("value cant been empty");
        Dictionary dictionary = dictionaries[dictionaryID];
        Word fromDB = wordRepository.findByDictionaryAndByKey(dictionary.getName(), word.getKey());
        if(fromDB != null) {
            fromDB.setValues(word.getValues());
            word = fromDB;
        } else {
            if(dictionary.validate(word.getKey()))
                throw new RuntimeException(String.format("unavailable key [%s]", word.getKey()));
        }
        word.setDictionary(dictionary.getName());
        wordRepository.save(word);
        return word;
    }

    public Dictionary[] getDictionaries() {
        return dictionaries;
    }
}
