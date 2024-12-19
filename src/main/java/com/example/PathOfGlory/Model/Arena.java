package com.example.PathOfGlory.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Arena {  //Renad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username can't be empty.")
    @Size(min = 4, max = 25, message = "Username length must be between 4-25 characters.")
    @Column(columnDefinition = "varchar(25) not null unique")
    @Check(constraints = "length(username)>=4")
    private String username;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, max = 15, message = "Name length must be between 4-15 characters.")
    @Column(columnDefinition = "varchar(15) not null")
    @Check(constraints = "length(name)>=4")
    private String name;

    @NotEmpty(message = "City can't be empty.")
    @Size(min = 4, max = 25, message = "City length must be between 4-25 characters.")
    @Column(columnDefinition = "varchar(25) not null")
    @Check(constraints = "length(city)>=4")
    private String city;

    @NotEmpty(message = "Location can't be empty.")
    @Size(min = 4, max = 500, message = "Location length must be between 4-500 characters.")
    @Column(columnDefinition = "varchar(500) not null")
    @Check(constraints = "length(location)>=4")
    private String location;

    @NotEmpty(message = "License can't be empty.")
    @Size(min = 5, max = 30, message = "License length must be between 5-30 characters.")
    @Column(columnDefinition = "varchar(30) not null unique")
    @Check(constraints = "length(license)>=5")
    private String license;

    @NotNull(message = "License end date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private LocalDate licenseEndDate;

    @Column(columnDefinition = "varchar(10)")
    private String isActivated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arena")
    private Set<Offering> offerings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arena")
    private Set<EventHeldRequest> eventHeldRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arena")
    private Set<Event> events;
}