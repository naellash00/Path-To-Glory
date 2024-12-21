package com.example.PathOfGlory.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Event {  //Renad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, max = 15, message = "Name length must be between 4-15 characters.")
    @Column(columnDefinition = "varchar(15) not null")
    @Check(constraints = "length(name)>=4")
    private String name;

    @NotNull(message = "Number can't be empty.")
    @Positive(message = "Number must be positive number large than zero.")
    @Column(columnDefinition = "int not null unique")
    @Check(constraints = "number>0")
    private Integer number;

    @NotEmpty(message = "Description can't be empty.")
    @Size(min = 20, max = 800, message = "Description length must be between 20-800 characters.")
    @Column(columnDefinition = "varchar(800) not null")
    @Check(constraints = "length(description)>=20")
    private String description;

    @NotEmpty(message = "City can't be empty.")
    @Size(min = 4, max = 500, message = "City length must be between 4-500 characters.")
    @Column(columnDefinition = "varchar(500) not null")
    @Check(constraints = "length(city)>=4")
    private String city;

    @NotEmpty(message = "Location can't be empty.")
    @Size(min = 4, max = 500, message = "Location length must be between 4-500 characters.")
    @Column(columnDefinition = "varchar(500) not null")
    @Check(constraints = "length(location)>=4")
    private String location;

    @NotNull(message = "Start date end date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private Date startDate;

    @NotNull(message = "End date end date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private Date endDate;

    // status should be accepted - rejected - pending
    @Column(columnDefinition = "varchar(10)")
    private String status = "Pending";

    @ManyToOne
    @JsonIgnore
    private Arena arena;

    @ManyToOne
    @JsonIgnore
    private Sponsor sponsor;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EventHeldRequest eventHeldRequest;
}