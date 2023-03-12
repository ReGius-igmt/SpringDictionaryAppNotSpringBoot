package ru.regiuss.practice.dictinoary.server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.regiuss.practice.dictinoary.server.model.DictionaryFilter;
import ru.regiuss.practice.dictinoary.server.model.Page;
import ru.regiuss.practice.dictinoary.server.model.Word;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepository {

    private final EntityManager em;

    @Override
    public void save(Word word) {
        if(word.getId() == null) em.persist(word);
        else em.merge(word);
    }

    @Override
    public void delete(Word word) {
        em.remove(word);
    }

    @Override
    public Word findById(int id) {
        try {
            return em.createQuery("from Word w where w.id = :id", Word.class)
                    .setParameter("id", id)
                    .setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Word findByDictionaryAndByKey(String dictionary, String key) {
        try {
            return em.createQuery("from Word w where w.dictionary = :dictionary and w.key = :key", Word.class)
                    .setParameter("dictionary", dictionary)
                    .setParameter("key", key)
                    .setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Page<Word> findAllByDictionary(DictionaryFilter filter) {
        List<String> predicates = new LinkedList<>();
        Map<String, Object> params = new HashMap<>();
        predicates.add("w.dictionary = :dictionary");
        params.put("dictionary", filter.getDictionary());
        if(filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            predicates.add("w.key like :search");
            params.put("search", '%' + filter.getSearch() + '%');
        }
        String predicateString = String.join(" and ", predicates);

        long total;
        {
            TypedQuery<Long> q = em.createQuery("select count(1) from Word w where " + predicateString, Long.class);
            for(String s : params.keySet()) {
                q.setParameter(s, params.get(s));
            }
            total = q.getSingleResult();
        }
        Collection<Word> items;
        if(total < filter.getSkip()) items = Collections.emptyList();
        else {
            TypedQuery<Word> q = em.createQuery("from Word w where " + predicateString, Word.class);
            for(String s : params.keySet()) {
                q.setParameter(s, params.get(s));
            }
            items = q.setFirstResult(filter.getSkip())
                    .setMaxResults(filter.getCount())
                    .getResultList();
        }
        return new Page<>(items, total, filter.getSkip());
    }
}
