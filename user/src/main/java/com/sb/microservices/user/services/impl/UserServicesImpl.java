package com.sb.microservices.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sb.microservices.user.entity.Hotel;
import com.sb.microservices.user.entity.Rating;
import com.sb.microservices.user.entity.User;
import com.sb.microservices.user.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sb.microservices.user.exceptions.ResourceNotFoundException;
import com.sb.microservices.user.repositories.UserRepository;
import com.sb.microservices.user.services.externalService.HotelServiceFein;
import com.sb.microservices.user.services.externalService.RatingServiceFeign;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final HotelServiceFein hotelService;
    private final RatingServiceFeign ratingServiceFeign;
    private final Logger logger  = LoggerFactory.getLogger(UserServices.class);

    @Override
    public User saveUser(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setUserId(uuid);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        User user  = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("user not found with the given id" + userId)
        );
        
        try {
            //fetch rating from RATING_SERVICE - endpoint: http://localhost:8083/rating/users/{userId}
            logger.info("Fetching ratings for userId: {}", userId);
            List<Rating> ratings = ratingServiceFeign.getRatingsByUserId(user.getUserId());
            logger.info("Ratings received from service: {}", ratings.size());

            if (!ratings.isEmpty()) {
                logger.info("Processing {} ratings", ratings.size());
                
                List<Rating> ratingList = ratings.stream().map( rating -> {
                    try {
                        //Api call to hotelservice to fetch hotel details for this rating
                        //set the hotel to rating
                        Hotel hotel = hotelService.getHotel(rating.getHotelId());
                        rating.setHotel(hotel);
                    } catch (FeignException e) {
                        logger.warn("Could not fetch hotel for hotelId: {}, Error: {}", rating.getHotelId(), e.getMessage());
                    }
                    //return rating with hotel info
                    return rating;
                }).toList();
                
                logger.info("Setting {} enriched ratings for user", ratingList.size());
                user.setRating(ratingList);
            } else {
                logger.info("No ratings found for userId: {}", userId);
                user.setRating(new ArrayList<>());
            }
        } catch (FeignException e) {
            logger.warn("No ratings found for userId: {}, HTTP Status: {}, Error: {}", userId, e.status(), e.getMessage());
            user.setRating(new ArrayList<>());
        }
        
        return user;
    }
}
