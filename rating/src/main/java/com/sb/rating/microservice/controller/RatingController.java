package com.sb.rating.microservice.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.rating.microservice.entities.Rating;
import com.sb.rating.microservice.service.RatingService;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {
    

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.saveUser(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }
    
    /**
     * Get all ratings
     * @return ResponseEntity with list of all ratings
     */
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRating();
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Get ratings by User ID
     * @param userId User ID to filter ratings
     * @return ResponseEntity with list of ratings for the user
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratings = ratingService.getRatingByUserId(userId);
        return ResponseEntity.ok(ratings);
    }
    
    /**
     * Get ratings by Hotel ID
     * @param hotelId Hotel ID to filter ratings
     * @return ResponseEntity with list of ratings for the hotel
     */
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<Rating> ratings = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.ok(ratings);
    }
}
