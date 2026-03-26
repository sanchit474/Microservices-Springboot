package com.sb.hotel.microservice.repository;

import com.sb.hotel.microservice.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,String> {
}
