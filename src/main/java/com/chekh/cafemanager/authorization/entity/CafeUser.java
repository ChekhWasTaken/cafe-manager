package com.chekh.cafemanager.authorization.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CafeUser {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, updatable = false, columnDefinition = "CHAR(64)")
    private String id;

    @Column
    @NonNull
    private String username;

    @Column
    @NonNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "api_user_role",
            joinColumns = @JoinColumn(name = "api_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @NonNull
    private Set<CafeUserRole> roles = new HashSet<>();

}
