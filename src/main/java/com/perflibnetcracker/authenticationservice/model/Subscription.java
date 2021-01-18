package com.perflibnetcracker.authenticationservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription", schema = "auth_service")
public class Subscription extends BaseEntity {
    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    // TODO(Kuptsov) MAJOR: Переименовать - это кол-во бесплатных книг, а не бесплатная
    //  книга, не забыть про миграции (alter table) и dto на стороне фронта
    @Column(name = "free_book_count")
    private Integer freeBookCount;
}
