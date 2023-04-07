package com.maid.cafe.c.maidcafec.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email = :email")


@Data
@Entity
@DynamicInsert        // DynamicInsert and DynamicUpdate do not seem to be the best option:
@DynamicUpdate        // https://thorben-janssen.com/dynamic-inserts-and-updates-with-spring-data-jpa/#Standard_behavior
@Table(name = "user") // https://stackoverflow.com/questions/3404630/hibernate-dynamic-update-dynamic-insert-performance-effects
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")    // Если ты укажешь тип GeneratedValue(strategy = GenerationType.IDENTITY), то Hibernate делегирует установку ID на уровень базы данных. Обычно при этом используется колонка, помеченная как PRIMARY KEY, AUTOINCREMENT.
    private Integer id;     // А вот если ты хочешь, чтобы твои ID были уникальными и генерировались по специально заданному алгоритму, то можешь воспользоваться аннотацией GeneratedValue(strategy = GenerationType.SEQUENCE)
                            // https://javarush.com/quests/lectures/questhibernate.level12.lecture02
    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
