package ru.regiuss.practice.dictinoary.server.service;

import org.springframework.stereotype.Service;
import ru.regiuss.practice.dictinoary.server.model.Dictionary;
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
                new Dictionary("DIGIT", key -> key == null || key.length() != 5 || !Pattern.matches("[0-9]+", key)),
                new Dictionary("LATIN", key -> key == null || key.length() != 4 || !Pattern.matches("[A-Za-z]+", key))
        };
    }

    public Page<Word> getWords(Integer dictionaryID, int skip, int count) {
        if(dictionaryID == null)
            throw new RuntimeException("dictionary cant been empty");
        if(dictionaryID < 0 || dictionaryID > dictionaries.length-1)
            throw new RuntimeException("dictionary not exist");
        if(skip < 0) skip = 0;
        if(count < 1) count = 1;
        else if(count > 20) count = 20;
        return wordRepository.findAllByDictionary(dictionaries[dictionaryID].getName(), skip, count);
    }

    public void delete(Integer dictionaryID, String key) {
        if(dictionaryID == null)
            throw new RuntimeException("dictionary cant been empty");
        if(dictionaryID < 0 || dictionaryID > dictionaries.length-1)
            throw new RuntimeException("dictionary not exist");
        if(key == null || key.isEmpty())
            throw new RuntimeException("key cant been empty");
        Dictionary dictionary = dictionaries[dictionaryID];
        Word word = wordRepository.findByDictionaryAndByKey(dictionary.getName(), key);
        if(word == null)
            throw new RuntimeException(String.format("word not found with dictionary %s and key %s", dictionary.getName(), key));
        wordRepository.delete(word);
    }
    
    public Word add(Word word, Integer dictionaryID) {
        if(dictionaryID == null)
            throw new RuntimeException("dictionary cant been empty");
        if(dictionaryID < 0 || dictionaryID > dictionaries.length-1)
            throw new RuntimeException("dictionary not exist");
        if(word.getKey() == null || word.getKey().isEmpty())
            throw new RuntimeException("key cant been empty");
        if(word.getValue() == null || word.getValue().isEmpty())
            throw new RuntimeException("value cant been empty");
        Dictionary dictionary = dictionaries[dictionaryID];
        Word fromDB = wordRepository.findByDictionaryAndByKey(dictionary.getName(), word.getKey());
        if(fromDB != null) {
            fromDB.setValue(word.getValue());
            word = fromDB;
        } else {
            if(dictionary.validate(word.getKey()))
                throw new RuntimeException(String.format("unavailable key [%s]", word.getKey()));
        }
        word.setDictionary(dictionary.getName());
        wordRepository.save(word);
        return word;
    }
    
    public Word search(Integer dictionaryID, String key) {
        if(dictionaryID == null)
            throw new RuntimeException("dictionary cant been empty");
        if(dictionaryID < 0 || dictionaryID > dictionaries.length-1)
            throw new RuntimeException("dictionary not exist");
        if(key == null || key.isEmpty())
            throw new RuntimeException("key cant been empty");
        Dictionary dictionary = dictionaries[dictionaryID];
        return wordRepository.findByDictionaryAndByKey(dictionary.getName(), key);
    }

    public Dictionary[] getDictionaries() {
        return dictionaries;
    }
}
