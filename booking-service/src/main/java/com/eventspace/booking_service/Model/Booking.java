package com.eventspace.booking_service.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    private String id;
    @Column(nullable = false, updatable = false)
    private Long venueId;
    @Column(nullable = false, updatable = false)
    private Long userId;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate bookingDate;
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @Column(nullable = false, updatable = false)
    private Long amountPaid;
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Status status;


    @PrePersist
    void generateId() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }
}
