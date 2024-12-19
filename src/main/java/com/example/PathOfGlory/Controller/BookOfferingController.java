package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.Service.BookOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/bookOffering")
@RequiredArgsConstructor
public class BookOfferingController { //Renad

    private final BookOfferingService bookOfferingService;

    @GetMapping("/get")
    public  ResponseEntity getAllBookOffering(){
        return ResponseEntity.status(200).body(bookOfferingService.getAllBookOffering());
    }

    @PostMapping("/bookOffering/offeringId/{offeringId}/athleteId/{athleteId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity bookOffering(@PathVariable Integer offeringId, @PathVariable Integer athleteId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate) {
        bookOfferingService.bookOffering(offeringId, athleteId, startDate, endDate);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Completed."));
    }
}