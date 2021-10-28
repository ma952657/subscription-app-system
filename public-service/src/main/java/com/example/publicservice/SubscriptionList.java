package com.example.publicservice;

import com.example.publicservice.model.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionList {
    private List<Subscription> subscriptionList;

    public SubscriptionList(){
        subscriptionList = new ArrayList<>();
    }

    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }
}
