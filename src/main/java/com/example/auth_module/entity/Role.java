package com.example.auth_module.entity;

import com.example.auth_module.constant.RoleName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name; // 예: ROLE_USER, ROLE_ADMIN

    public Role(RoleName name) {
        this.name = name;
    }
}
