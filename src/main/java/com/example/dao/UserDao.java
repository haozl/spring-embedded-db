package com.example.dao;


import com.example.model.User;

import java.util.List;

public interface UserDao {

    User findByName(String name);

    List<User> findAll();

}
