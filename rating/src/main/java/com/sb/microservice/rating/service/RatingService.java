package com.sb.microservice.rating.service;

import com.sb.microservice.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating saveUser(Rating rating);

    List<Rating> getAllRating();

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);
}
