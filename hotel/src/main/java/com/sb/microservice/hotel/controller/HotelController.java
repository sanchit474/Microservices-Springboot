package com.sb.microservice.hotel.controller;

import com.sb.microservice.hotel.entities.Hotel;
import com.sb.microservice.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createUser(@RequestBody Hotel hotel){
        Hotel response = hotelService.create(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelByID(@PathVariable String hotelId){
        Hotel hotel = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotel(){
        List<Hotel> allHotels =hotelService.getAllHotel();
        return ResponseEntity.ok(allHotels);
    }
}
