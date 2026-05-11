package com.luwd.user.service.services.impl;

import com.luwd.user.service.Exceptions.ResourceNotFoundException;
import com.luwd.user.service.entities.Hotel;
import com.luwd.user.service.entities.Rating;
import com.luwd.user.service.entities.User;
import com.luwd.user.service.external.services.HotelService;
import com.luwd.user.service.repositories.UserRepository;
import com.luwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        //it will generate UserId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from databases with the help of repository
        User user= userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User with given id is not found on server !! : " +userId));
        //fetch rating of the above user from RATING SERVICE
        //we want to call ratings for the given user
       // http://localhost:8083/ratings/users/8b323dac-7ddb-4f88-9108-17a9b0af4180

   Rating[] ratingOfUser = restTemplate.getForEntity("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class).getBody();



        logger.info("I am here");
       logger.info("{}",ratingOfUser);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating->{
           //api call to hotel service to get the hotel
        // ResponseEntity<Hotel> forEntity =   restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//         Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
        // logger.info("response status code:{}",forEntity.getStatusCode());

         //set the hotel to rating
           rating.setHotel(hotel);
           return rating;
       }).collect(Collectors.toList());


        user.setRatings(ratingList);
        return user;
    }
}
