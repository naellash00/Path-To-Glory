package com.example.PathOfGlory.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
public class BookService {  //Renad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Start date end date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private Date startDate;

    @NotNull(message = "End date end date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private Date endDate;

    @Column(columnDefinition = "double")
    private Double bookingPrice;

    // status should be accepted - rejected - requested
    @Column(columnDefinition = "varchar(10)")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "timestamp")
    private LocalDateTime bookingTimestamp;

    @ManyToOne
    @JsonIgnore
    private Athlete athlete;

    @ManyToOne
    @JsonIgnore
    private Service service;
}