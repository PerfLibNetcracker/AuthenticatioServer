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
@Table(name = "author", schema = "main_model")
public class Author {

    @Id
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "full_name")
    private String fullName;


}
