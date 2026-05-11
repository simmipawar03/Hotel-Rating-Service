package com.luwd.rating.service.services;


import com.luwd.rating.service.entities.Rating;

import java.util.List;

public interface RatingService {

    //create rating

    Rating create (Rating rating);

    //get all rating

    List<Rating> getAllRating();

    //get all by userId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotelId

    List<Rating> getRatingByHotelId(String hotelId);

}
