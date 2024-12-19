package com.example.PathOfGlory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class BookOfferingDTO {  //Renad
    private Date startDate;

    private Date endDate;

    private String status;

    private LocalDateTime bookingTimestamp;
}