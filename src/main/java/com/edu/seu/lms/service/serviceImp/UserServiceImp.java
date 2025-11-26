package com.edu.seu.lms.service.serviceImp;

import com.edu.seu.lms.entity.User;
import com.edu.seu.lms.repository.UserRepository;
import com.edu.seu.lms.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User validateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }
}
