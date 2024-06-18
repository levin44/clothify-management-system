package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;
    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "user",  orphanRemoval = true)
    private List<Orders> orders;
}
