package com.edu.seu.lms.repository;

import com.edu.seu.lms.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findBySubType(String subType);
}