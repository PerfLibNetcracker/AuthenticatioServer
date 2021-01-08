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
@Table(name = "bought_books", schema = "auth_service")
public class BoughtBooks {
    @Id
    @Column(name = "book_id")
    private Long bookId;

    private String name;

    private Long price;
}
