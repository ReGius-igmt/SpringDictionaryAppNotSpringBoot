package ru.regiuss.practice.dictinoary.server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.regiuss.practice.dictinoary.server.model.Page;
import ru.regiuss.practice.dictinoary.server.model.Word;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;

@Repository
@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepository {

    private final EntityManager em;

    @Override
    public void save(Word word) {
        em.getTransaction().begin();
        if(word.getId() == null) em.persist(word);
        else em.merge(word);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Word word) {
        em.getTransaction().begin();
        em.remove(word);
        em.getTransaction().commit();
    }

    @Override
    public Word findById(int id) {
        return em.createQuery("from Word w where w.id = :id", Word.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
    }

    @Override
    public Page<Word> findAllByDictionary(String dictionary, int skip, int count) {
        long total = em.createQuery("select count(1) from Word w where w.dictionary = :dictionary", Long.class)
                .setParameter("dictionary", dictionary)
                .getSingleResult();
        Collection<Word> items;
        if(total < skip) items = Collections.emptyList();
        else items = em.createQuery("from Word w where w.dictionary = :dictionary", Word.class)
                .setParameter("dictionary", dictionary)
                .setFirstResult(skip)
                .setMaxResults(count)
                .getResultList();
        return new Page<>(items, total, skip);
    }

    @Override
    public Word findByDictionaryAndByKey(String dictionary, String key) {
        return em.createQuery("from Word w where w.dictionary = :dictionary and w.key = :key", Word.class)
                .setParameter("dictionary", dictionary)
                .setParameter("key", key)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
    }
}
