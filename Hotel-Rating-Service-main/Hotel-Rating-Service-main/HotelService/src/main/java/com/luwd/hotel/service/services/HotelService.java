package com.luwd.hotel.service.services;

import com.luwd.hotel.service.entites.Hotel;

import java.util.List;

public interface HotelService {

    //create

    Hotel create (Hotel hotel);


    //getall

    List<Hotel> getAll();


    //getsingle

    Hotel getSingleHotel(String Id);
}
