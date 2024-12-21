package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.Service.BookServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/bookService")
@RequiredArgsConstructor
public class BookServiceController { //Renad

    private final BookServiceService bookServiceService;

    @GetMapping("/get")
    public  ResponseEntity getAllBookings(){
        return ResponseEntity.status(200).body(bookServiceService.getAllBookServices());
    }
}