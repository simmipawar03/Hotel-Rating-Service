package com.luwd.hotel.service.services.impl;

import com.luwd.hotel.service.entites.Hotel;
import com.luwd.hotel.service.exceptions.ResourceNotFoundException;
import com.luwd.hotel.service.repositories.HotelRepository;
import com.luwd.hotel.service.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel) {

        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getSingleHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel with given is not found!!"));
    }
}
