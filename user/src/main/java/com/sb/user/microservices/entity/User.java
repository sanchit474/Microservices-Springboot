package com.sb.user.microservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column( name = "ID")
    private String userId;

    @Column( name = "NAME")
    private String name;

    @Column( name = "EMAIL")
    private String email;

    @Column( name = "ABOUT")
    private String about;


    @Transient
    private List<Rating> rating;
}
