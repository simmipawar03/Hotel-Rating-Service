package com.luwd.hotel.service.repositories;

import com.luwd.hotel.service.entites.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
