package com.edu.seu.lms.service;

import com.edu.seu.lms.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User save(User user);
    User validateUser(String email,String password);
}
