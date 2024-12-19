// Osama Alghamdi

package com.example.PathOfGlory.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SponsorShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Please enter the amount of sponsor ship")
    @Column(columnDefinition = "int not null")
    private Integer sponsorShipAmount;

    // status should be accepted - rejected - pending
    @Column(columnDefinition = "varchar(10)")
    private String status;

    @NotNull(message = "Please enter start date")
    @Column(columnDefinition = "Date not null")
    private LocalDateTime startDate;

    @NotNull(message = "Please enter end date")
    @Column(columnDefinition = "Date not null")
    private LocalDateTime endDate;

    @ManyToOne
    private Sponsor athleteSponsor;

    @OneToOne
    private Athlete athlete;

}
