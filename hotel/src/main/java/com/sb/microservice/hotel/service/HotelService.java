package com.sb.microservice.hotel.service;

import com.sb.microservice.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {
    //create user
    Hotel create(Hotel hotel);
    //get all users
    List<Hotel> getAllHotel();
    //get user by id
    Hotel getHotelById(String hotelId);
}
