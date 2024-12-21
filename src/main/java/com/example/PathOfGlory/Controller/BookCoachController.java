package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.Service.BookCoachService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/book/coach")
@AllArgsConstructor
public class BookCoachController { // Naelah
    private final BookCoachService bookCoachService;

    @GetMapping("/get")
    public ResponseEntity getAllBookings(){
        return ResponseEntity.status(200).body(bookCoachService.getAllBookings());
    }

    @GetMapping("/get/all-accepted/bookings")
    public ResponseEntity getAllAcceptedBookings(){
        return ResponseEntity.status(200).body(bookCoachService.getAllAcceptedBookings());
    }
}
