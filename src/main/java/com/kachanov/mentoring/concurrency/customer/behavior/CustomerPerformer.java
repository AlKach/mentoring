package com.kachanov.mentoring.concurrency.customer.behavior;

import com.kachanov.mentoring.concurrency.Constants;
import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.ticket.TicketStorageHolder;

import java.util.Random;

public class CustomerPerformer extends Thread {

    private final Customer customer;

    public CustomerPerformer(Customer customer) {
        this.customer = customer;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            AbstractCustomerBehavior behavior;
            do {
                behavior = CustomerBehaviorFactory.getRandomCustomerBehavior();
            } while (!behavior.appliesTo(customer));

            behavior.performAction(customer, TicketStorageHolder.getInstance());

            try {
                Thread.sleep(getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getSleepTime() {
        return Constants.CUSTOMER_ACTION_INTERVAL
                + new Random().nextInt(Constants.CUSTOMER_ACTION_INTERVAL_VARIATION)
                - Constants.CUSTOMER_ACTION_INTERVAL_VARIATION / 2;
    }

}
