package com.sb.rating.microservice.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rating.microservice.entities.Rating;
import com.sb.rating.microservice.repository.RatingRepository;
import com.sb.rating.microservice.service.RatingService;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    

    private final RatingRepository ratingRepository;
    
    @Override
    public Rating saveUser(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
