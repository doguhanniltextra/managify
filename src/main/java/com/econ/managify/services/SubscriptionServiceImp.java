package com.econ.managify.services;

import com.econ.managify.exceptions.SubscriptionException;
import com.econ.managify.interfaces.SubscriptionService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.PlanType;
import com.econ.managify.models.Subscription;
import com.econ.managify.models.User;
import com.econ.managify.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImp   {

    private final UserServiceImp userService;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImp(UserServiceImp userService, SubscriptionRepository subscriptionRepository) {
        this.userService = userService;
        this.subscriptionRepository = subscriptionRepository;
    }


    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        return subscriptionRepository.save(subscription);
    }


    public Subscription getUserSubscription(Long userId) throws SubscriptionException {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (!isValid(subscription)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }


    public Subscription updateSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.getSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)) {subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));}
        else {subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));}
        return subscriptionRepository.save(subscription);
    }


    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)){return true;}
        LocalDate endDate = subscription.getGetSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
