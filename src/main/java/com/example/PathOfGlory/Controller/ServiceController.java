package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.DTO.ServiceDTO;
import com.example.PathOfGlory.Model.Service;
import com.example.PathOfGlory.Service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController { //Renad
    // 1. Declare a dependency using Dependency Injection
    private final ServiceService serviceService;

    // 2. CRUD
    // 2.1 GET
    @GetMapping("/get")
    public ResponseEntity getAllServices() {
        return ResponseEntity.status(200).body(serviceService.getAllServices());
    }

    // 2.2 POST
    @PostMapping("/add/arenaId/{arenaId}")
    public ResponseEntity addService(@RequestBody @Valid Service service, @PathVariable Integer arenaId) {
        serviceService.addService(service,arenaId);
        return ResponseEntity.status(200).body(new ApiResponse("New Service Added."));
    }

    // 2.3 UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity updateService(@PathVariable Integer id, @RequestBody @Valid Service service) {
        serviceService.updateService(id, service);
        return ResponseEntity.status(200).body(new ApiResponse("Service Updated."));
    }

    // Extra endpoint:
    @GetMapping("/getServicesByPriceRange/{minPrice}/{maxPrice}")
    public ResponseEntity getServicesByPriceRange(@PathVariable Double minPrice, @PathVariable Double maxPrice) {
        List<ServiceDTO> servicesByPriceRange = serviceService.getServicesByPriceRange(minPrice,maxPrice);
        return ResponseEntity.status(200).body(servicesByPriceRange);
    }
}