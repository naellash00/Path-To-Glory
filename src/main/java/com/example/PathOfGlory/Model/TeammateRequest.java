package com.example.PathOfGlory.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeammateRequest { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    //join column --> specifies the database column used for this relationship
    @JoinColumn(name = "sender_id", nullable = false)
    private Athlete sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Athlete receiver;

    // status should be accepted - rejected - pending
    @Column(columnDefinition = "varchar(10)")
    private String status;

    @Column
    private LocalDateTime requestDate;
    @Column
    private LocalDateTime responseDate;
}
