package com.kachanov.mentoring.concurrency.customer.behavior;

import java.util.Random;

public class CustomerBehaviorFactory {

    private CustomerBehaviorFactory() {

    }

    private static final AbstractCustomerBehavior[] BEHAVIORS = {
            new BuyTicketsBehavior(),
            new ReturnTicketsBehavior()
    };

    public static AbstractCustomerBehavior getRandomCustomerBehavior() {
        return BEHAVIORS[new Random().nextInt(BEHAVIORS.length)];
    }

}
