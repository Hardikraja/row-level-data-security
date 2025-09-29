package com.data.security.demo.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // SUPER_ADMIN, EVENT_MANAGER, STAFF, ATTENDEE, VENDOR

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users;

}

