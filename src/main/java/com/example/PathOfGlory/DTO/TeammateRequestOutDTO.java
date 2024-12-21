package com.example.PathOfGlory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class TeammateRequestOutDTO { // Naelah
    private String senderName;

    private String senderUsername;

    private String receiverName;

    private String receiverUsername;

    private String status; // requested - accepted - rejected

    private LocalDateTime requestDate;

    private LocalDateTime responseDate;
}
