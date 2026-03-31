package com.sb.microservice.hotel.repository;

import com.sb.microservice.hotel.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,String> {
}
