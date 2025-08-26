package com.econ.managify.dtos.responses;

import com.econ.managify.models.PlanType;

import java.time.LocalDate;

public class SubscriptionsUpdateUserSubscriptionResponseDto {
    private LocalDate subscriptionStartDate;
    private LocalDate getSubscriptionEndDate;    private PlanType planType;


    public void setSubscriptionStartDate(LocalDate subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDate getGetSubscriptionEndDate() {
        return getSubscriptionEndDate;
    }

    public void setGetSubscriptionEndDate(LocalDate getSubscriptionEndDate) {
        this.getSubscriptionEndDate = getSubscriptionEndDate;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }
}
