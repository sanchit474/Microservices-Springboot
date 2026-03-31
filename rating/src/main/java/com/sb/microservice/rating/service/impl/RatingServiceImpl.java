package com.sb.microservice.rating.service.impl;

import java.util.List;

import com.sb.microservice.rating.entities.Rating;
import com.sb.microservice.rating.repository.RatingRepository;
import com.sb.microservice.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
