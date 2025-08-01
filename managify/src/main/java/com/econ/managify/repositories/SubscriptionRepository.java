package com.econ.managify.repositories;

import com.econ.managify.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> { Subscription findByUserId(Long userId);}
