// Osama Alghamdi

package com.example.PathOfGlory.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Please enter your username")
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Enter just characters")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$",message = "Password must be : " +
            "At least 8 characters long\n" +
            "Contains at least one digit\n" +
            "Contains at least one lowercase letter\n" +
            "Contains at least one uppercase letter\n" +
            "Contains at least one special character from the set: !@#$%^&*")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
    @NotEmpty(message = "Please enter your name")
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Enter just characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @NotEmpty(message = "Please enter your phone number")
    @Pattern(regexp = "^05\\d{8}$",message = "Start with 05 and exactly 10 digits")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;
    @NotEmpty(message = "Please enter your email")
    @Email
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;
    @NotEmpty(message = "Please enter your city")
    @Column(columnDefinition = "varchar(15) not null")
    private String city;
    @NotEmpty(message = "Please enter the Certification Record")
    @Pattern(regexp = "^1\\d{9}$",message = "certificateRecord must be 10 digits and start with number 1")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String certificateRecord;

    @Column(columnDefinition = "varchar(10)")
    private String isActivated;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "athleteSponsor")
    private Set<SponsorShip> sponsorShipList;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sponsor")
    private Set<EventHeldRequest> eventHeldRequestList;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sponsor")
    private Set<EventParticipationRequest> eventParticipationRequestList;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sponsor")
    private Set<Event> eventList;
}