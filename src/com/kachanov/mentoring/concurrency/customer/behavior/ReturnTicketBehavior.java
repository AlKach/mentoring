package com.kachanov.mentoring.concurrency.customer.behavior;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.ticket.Ticket;
import com.kachanov.mentoring.concurrency.ticket.TicketStorage;

import java.util.Random;

public class ReturnTicketBehavior extends AbstractCustomerBehavior {

    @Override
    public void performAction(Customer customer, TicketStorage ticketStorage) {
        if (!customer.getTickets().isEmpty()) {
            Ticket ticket = getRandomTicket(customer);
            System.out.println(customer + " wants to return " + ticket);
            ticketStorage.returnTicket(ticket);
            customer.getTickets().remove(ticket);
            System.out.println(customer + " returns " + ticket);
        } else {
            System.out.println(customer + " wants to return ticket but he doesn't have one");
        }
    }

    private Ticket getRandomTicket(Customer customer) {
        return customer.getTickets().get(new Random().nextInt(customer.getTickets().size()));
    }
}
