package com.sb.rating.microservice.service;

import com.sb.rating.microservice.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating saveUser(Rating rating);

    List<Rating> getAllRating();

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);
}
