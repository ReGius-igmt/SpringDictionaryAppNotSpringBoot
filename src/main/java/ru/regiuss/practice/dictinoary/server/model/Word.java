package ru.regiuss.practice.dictinoary.server.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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
    @Column(name = "w_value", nullable = false, length = 30)
    private String value;

    public Word(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
