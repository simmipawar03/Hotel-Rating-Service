package com.luwd.user.service.services;

import com.luwd.user.service.entities.User;

import java.util.List;

public interface UserService {
    //user operations

    //user creation

    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given user
    User getUser(String userId);

    //updation

    //deletion

}
