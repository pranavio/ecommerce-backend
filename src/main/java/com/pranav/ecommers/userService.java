package com.pranav.ecommers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class userService {
    List<User> userList = new ArrayList<>();
    private Long nextId = 1L;
    public List<User> getAllUsers() {
        return userList;
    }
    public void addUser(User user) {
        user.setId(nextId++);
        userList.add(user);
    }

    public User getUserById(Long id) {
       for(User user : userList) {
           if(user.getId().equals(id)) {
               return user;
           }
       }
        return null;
    }
}
