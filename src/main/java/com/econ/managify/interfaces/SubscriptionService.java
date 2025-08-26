package com.econ.managify.interfaces;

import com.econ.managify.exceptions.SubscriptionException;
import com.econ.managify.models.PlanType;
import com.econ.managify.models.Subscription;
import com.econ.managify.models.User;

public interface SubscriptionService {
        Subscription createSubscription(User user);
        Subscription getUserSubscription(Long userId) throws SubscriptionException;
        Subscription updateSubscription(Long userId, PlanType planType);
        boolean isValid(Subscription subscription);
}
