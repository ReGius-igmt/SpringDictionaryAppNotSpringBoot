package ru.regiuss.practice.dictinoary.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "dictionary", nullable = false, length = 20)
    private String dictionary;
    @Column(name = "w_key", nullable = false, length = 30)
    private String key;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dictionary_values", joinColumns = @JoinColumn(name = "word_id"))
    @Column(name = "value")
    private Set<String> values;

    public Word(String key, Set<String> value) {
        this.key = key;
        this.values = value;
    }

    public Word(Integer id, String key, Set<String> values) {
        this.id = id;
        this.key = key;
        this.values = values;
    }
}
