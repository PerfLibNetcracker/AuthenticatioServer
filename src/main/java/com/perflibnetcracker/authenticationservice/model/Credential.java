package com.perflibnetcracker.authenticationservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {

    @Id
    private Long id;
    private String emailId;
    private String userName;
    private String password;
}
