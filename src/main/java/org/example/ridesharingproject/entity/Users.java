package org.example.ridesharingproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.ridesharingproject.util.Gender;

import java.util.Set;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private Gender gender;

    private String profileImage;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "users_id",
                    foreignKey = @ForeignKey(name = "fk_users_roles_user")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id",
                    foreignKey = @ForeignKey(name = "fk_users_roles_role")
            ),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_users_roles",
                    columnNames = {"users_id", "roles_id"}
            )
    )
    private Set<Role> roles;
}
