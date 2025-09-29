package com.data.security.demo.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_booth")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorBooth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boothName;

    private String location;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private AppUser vendor;
}

