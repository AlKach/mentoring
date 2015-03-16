package com.kachanov.mentoring.concurrency.customer.behavior;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.ticket.Ticket;
import com.kachanov.mentoring.concurrency.ticket.TicketStorage;

public class BuyOneTicketBehaviorAbstract extends AbstractCustomerBehavior {

    @Override
    public void performAction(Customer customer, TicketStorage ticketStorage) {
        int trainNumber = getRandomTrainNumber();
        System.out.println(customer + " tries to buy ticket for train #" + trainNumber);
        Ticket ticket = ticketStorage.sellTicket(trainNumber);
        customer.getTickets().add(ticket);
        System.out.println(customer + " bought ticket for train #" + trainNumber);
    }
}
