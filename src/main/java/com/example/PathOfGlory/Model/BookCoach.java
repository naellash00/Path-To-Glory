package com.example.PathOfGlory.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookCoach { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "start date cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @NotNull(message = "end date cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;

    // status should be accepted - rejected - requested
    @Column(columnDefinition = "varchar(10)")
    private String status;

    @Column(columnDefinition = "int") // set by the coach
    private Integer bookingPrice;

    @ManyToOne
    @JsonIgnore
    private Athlete athlete;

    @ManyToOne
    @JsonIgnore
    private Coach coach;
}