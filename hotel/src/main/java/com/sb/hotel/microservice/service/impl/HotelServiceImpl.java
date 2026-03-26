package com.sb.hotel.microservice.service.impl;

import com.sb.hotel.microservice.entities.Hotel;
import com.sb.hotel.microservice.exceptions.ResourceNotFoundException;
import com.sb.hotel.microservice.repository.HotelRepository;
import com.sb.hotel.microservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        String uuid = UUID.randomUUID().toString();
        hotel.setHotelId(uuid);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with the given id" + hotelId));
    }
}
