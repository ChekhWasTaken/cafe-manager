package com.chekh.cafemanager.authorization.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
public class CafeUserRole implements GrantedAuthority {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, updatable = false, columnDefinition = "CHAR(64)")
    private String id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CafeUserRoleType type;

    @Override
    public String getAuthority() {
        return type.name();
    }
}
