// Osama Alghamdi

package com.example.PathOfGlory.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SponsorShipDTO {

    private Integer sponsorShipAmount;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String  sponsorName;

    private String athleteName;
}
