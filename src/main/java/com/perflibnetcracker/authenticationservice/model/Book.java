package com.perflibnetcracker.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book", schema = "main_model")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @Column(name = "total_views")
    private Long totalViews;

    private Double rating;

    @Column(name = "url_image")
    private String urlImage;
    // TODO(Kuptsov) MAJOR: Убрать за ненадобностью, проследить что фронт согласован с этим
    @Column(name = "genre_id")
    private Long genreId;
    // TODO(Kuptsov) MAJOR: Убрать за ненадобностью, проследить что фронт согласован с этим
    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;
    // TODO(Kutpsov) MAJOR: Переименовать в ratedUsers
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_user",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> users = new HashSet<>();

}
