package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "isAccountNonExpired")
    private boolean isAccountNonExpired;

    @Column(name = "isAccountNonLocked")
    private boolean isAccountNonLocked;

    @Column(name = "isCredentialsNonExpired")
    private boolean isCredentialsNonExpired;

    @Column(name = "isEnabled")
    private boolean isEnabled;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Genre> genreList;

}
