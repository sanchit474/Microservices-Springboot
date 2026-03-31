package com.sb.microservices.user.services.externalService;

import com.sb.microservices.user.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServiceFein {
    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable String hotelId);
}
