package com.example.PathOfGlory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class BookServiceDTO {  //Renad
    private Date startDate;

    private Date endDate;

    private Double bookingPrice;

    private String status;

    private LocalDateTime bookingTimestamp;
}