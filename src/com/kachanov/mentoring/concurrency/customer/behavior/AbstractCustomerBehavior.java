package com.kachanov.mentoring.concurrency.customer.behavior;

import com.kachanov.mentoring.concurrency.Constants;
import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.ticket.TicketStorage;

import java.util.Random;

public abstract class AbstractCustomerBehavior {

    protected final int getRandomTrainNumber() {
        return new Random().nextInt(Constants.TRAINS_COUNT);
    }

    public abstract void performAction(Customer customer, TicketStorage ticketStorage);

}
