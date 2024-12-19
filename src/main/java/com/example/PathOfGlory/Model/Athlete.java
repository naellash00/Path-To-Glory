package com.example.PathOfGlory.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Athlete { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "full name cannot be empty")
    @Size(min = 5, message = "full name cannot be less than 5 letters")
    @Column(columnDefinition = "varchar(30) not null")
    private String fullName;

    @NotEmpty(message = "username cannot be empty")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @NotEmpty(message = "phone number cannot be empty")
    @Size(min = 10, max = 10, message = "phone number must be 10 digits")
    @Pattern(regexp = "^05\\d+$", message = "phone number must start with '05'")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String phoneNumber;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "enter a valid email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotNull(message = "age cannot be empty")
    @Min(value = 13, message = "age cannot be less than 13")
    @Column(columnDefinition = "int not null")
    private Integer age;

    @NotEmpty(message = "gender cannot be empty")
    @Column(columnDefinition = "varchar(6) not null")
    private String gender;

    @NotEmpty(message = "city cannot be empty")
    @Column(columnDefinition = "varchar(15) not null")
    private String city;

    @NotEmpty(message = "sport name cannot be empty")
    @Column(columnDefinition = "varchar(15) not null")
    private String sportName;

    // if athlete deleted --> their booking deleted too
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "athlete")
    private Set<BookCoach> coachBookings;

    // remove so when athlete delete --> achievements get deleted, but not vice versa
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "athlete")
    private Set<Achievement> achievements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "athlete")
    private Set<BookOffering> bookOfferings;

    @OneToOne
    @JsonIgnore
    private SponsorShip sponsorShip;
}