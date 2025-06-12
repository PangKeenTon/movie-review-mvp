// src/main/java/com/example/moviereviewmvp/entity/Role.java
package com.example.moviereviewmvp.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Ví dụ: "ROLE_USER", "ROLE_ADMIN", "ROLE_CRITIC"

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Constructors, Getters, Setters
    public Role() {}
    public Role(String name) { this.name = name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
}