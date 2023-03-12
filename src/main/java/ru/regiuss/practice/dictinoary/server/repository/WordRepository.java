package ru.regiuss.practice.dictinoary.server.repository;

import org.springframework.stereotype.Repository;
import ru.regiuss.practice.dictinoary.server.model.DictionaryFilter;
import ru.regiuss.practice.dictinoary.server.model.Page;
import ru.regiuss.practice.dictinoary.server.model.Word;

@Repository
public interface WordRepository {
    void save(Word word);
    void delete(Word word);
    Word findById(int id);
    Word findByDictionaryAndByKey(String dictionary, String key);
    Page<Word> findAllByDictionary(DictionaryFilter filter);
}
