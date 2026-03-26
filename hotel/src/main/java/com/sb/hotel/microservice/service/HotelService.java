package com.sb.hotel.microservice.service;

import com.sb.hotel.microservice.entities.Hotel;

import java.util.List;

public interface HotelService {
    //create user
    Hotel create(Hotel hotel);
    //get all users
    List<Hotel> getAllHotel();
    //get user by id
    Hotel getHotelById(String hotelId);
}
