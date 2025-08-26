package com.econ.managify.controllers;

import com.econ.managify.dtos.responses.SubscriptionGetUserSubscriptionResponseDto;
import com.econ.managify.dtos.responses.SubscriptionsUpdateUserSubscriptionResponseDto;
import com.econ.managify.exceptions.SubscriptionException;
import com.econ.managify.interfaces.SubscriptionService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.PlanType;
import com.econ.managify.models.Subscription;
import com.econ.managify.models.User;
import com.econ.managify.services.SubscriptionServiceImp;
import com.econ.managify.services.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionServiceImp subscriptionService;

    public SubscriptionController(SubscriptionServiceImp subscriptionService, UserServiceImp userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    private final UserServiceImp userService;



    @GetMapping("/user")
    public ResponseEntity<SubscriptionGetUserSubscriptionResponseDto> getUserSubscription(@RequestHeader("Authorization") String jwt) throws SubscriptionException {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        SubscriptionGetUserSubscriptionResponseDto subscriptionGetUserSubscriptionResponseDto = new SubscriptionGetUserSubscriptionResponseDto();

        subscriptionGetUserSubscriptionResponseDto.setGetSubscriptionEndDate(subscription.getGetSubscriptionEndDate());
        subscriptionGetUserSubscriptionResponseDto.setPlanType(subscription.getPlanType());

        return new ResponseEntity<>(subscriptionGetUserSubscriptionResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<SubscriptionsUpdateUserSubscriptionResponseDto> updateUserSubscription(@RequestHeader("Authorization") String jwt, @RequestParam PlanType planType) throws SubscriptionException {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.updateSubscription(user.getId(),planType);

        SubscriptionsUpdateUserSubscriptionResponseDto subscriptionsUpdateUserSubscriptionResponseDto = new SubscriptionsUpdateUserSubscriptionResponseDto();
        subscriptionsUpdateUserSubscriptionResponseDto.setGetSubscriptionEndDate(subscription.getGetSubscriptionEndDate());
        subscriptionsUpdateUserSubscriptionResponseDto.setPlanType(subscription.getPlanType());

        return new ResponseEntity<>(subscriptionsUpdateUserSubscriptionResponseDto, HttpStatus.OK);
    }
}
