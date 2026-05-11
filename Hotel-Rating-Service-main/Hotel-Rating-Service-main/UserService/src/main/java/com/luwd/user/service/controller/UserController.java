package com.luwd.user.service.controller;


import com.luwd.user.service.entities.User;
import com.luwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
   private UserService userService;

    //private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)  {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
  int retryCount=0;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
   // @Retry(name="ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name="userRateLimiter", fallbackMethod = "ratingHotelFallback" )
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        System.out.println("Retry Count: "+retryCount++);
      User user = userService.getUser(userId);

      return ResponseEntity.ok(user);
    }
    //creating fall back method or circuitbreker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
     User dummy =   User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some services down")
                .userId("232424")
                .build();
    return new ResponseEntity<>(dummy,HttpStatus.OK);
    }

    //all user get


    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){

        return ResponseEntity.ok(userService.getAllUser());
    }

}
