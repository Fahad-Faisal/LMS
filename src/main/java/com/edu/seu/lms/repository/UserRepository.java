package com.edu.seu.lms.repository;

import com.edu.seu.lms.dto.UserDto;
import com.edu.seu.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository< User , Long> {
    User findByEmailAndPassword(String email, String password);
}
