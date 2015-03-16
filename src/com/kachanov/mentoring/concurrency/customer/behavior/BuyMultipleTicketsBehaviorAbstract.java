package com.kachanov.mentoring.concurrency.customer.behavior;

import com.kachanov.mentoring.concurrency.Constants;
import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.ticket.Ticket;
import com.kachanov.mentoring.concurrency.ticket.TicketStorage;

import java.util.List;
import java.util.Random;

public class BuyMultipleTicketsBehaviorAbstract extends AbstractCustomerBehavior {

    @Override
    public void performAction(Customer customer, TicketStorage ticketStorage) {
        int trainNumber = getRandomTrainNumber();
        int ticketsNumber = getRandomTicketsNumber();
        System.out.println(customer + " tries to buy " + ticketsNumber + " tickets for train #" + trainNumber);
        List<Ticket> tickets = ticketStorage.sellTickets(trainNumber, ticketsNumber);
        customer.getTickets().addAll(tickets);
        System.out.println(customer + " bought " + ticketsNumber + " tickets for train #" + trainNumber);
    }

    private int getRandomTicketsNumber() {
        return new Random().nextInt(Constants.SEATS_PER_TRAIN / 10);
    }
}
