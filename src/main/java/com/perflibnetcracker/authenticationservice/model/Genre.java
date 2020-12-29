package com.perflibnetcracker.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre", schema = "main_model")
public class Genre {

    @Id
    @Column(name = "genre_id")
    private Long genreId;

    private String name;
}
