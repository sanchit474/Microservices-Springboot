package com.sb.user.microservices.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sb.user.microservices.entity.Hotel;
import com.sb.user.microservices.entity.Rating;
import com.sb.user.microservices.entity.User;
import com.sb.user.microservices.exceptions.ResourceNotFoundException;
import com.sb.user.microservices.repositories.UserRepository;
import com.sb.user.microservices.services.UserServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private Logger logger  = LoggerFactory.getLogger(UserServices.class);

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
            String ratingServiceUrl = "http://RATING-SERVICE/rating/users/" + user.getUserId();
            logger.info("Rating Service URL: {}", ratingServiceUrl);
            
            Rating[] ratingOfUser = restTemplate.getForObject(ratingServiceUrl, Rating[].class);
            logger.info("Ratings received from service: {}", (Object) ratingOfUser);
            
            if (ratingOfUser != null && ratingOfUser.length > 0) {
                logger.info("Processing {} ratings", ratingOfUser.length);
                List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
                
                List<Rating> ratingList = ratings.stream().map( rating -> {
                    try {
                        //Api call to hotelservice to fetch hotel details for this rating
                        logger.info("Fetching hotel for hotelId: {}", rating.getHotelId());
                        ResponseEntity<Hotel> forObject = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                        //set the hotel to rating
                        Hotel hotel = forObject.getBody();
                        rating.setHotel(hotel);
                        logger.info("Hotel fetched for rating: {} with hotel: {}", rating.getRatingId(), hotel);
                    } catch (HttpClientErrorException e) {
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
        } catch (HttpClientErrorException e) {
            logger.warn("No ratings found for userId: {}, HTTP Status: {}, Error: {}", userId, e.getStatusCode(), e.getMessage());
            user.setRating(new ArrayList<>());
        } catch (Exception e) {
            logger.error("Unexpected error while fetching ratings for userId: {}, Error: {}", userId, e.getMessage(), e);
            user.setRating(new ArrayList<>());
        }
        
        return user;
    }
}
