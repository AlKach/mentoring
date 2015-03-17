package com.kachanov.mentoring.concurrency.customer.behavior;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.ticket.Ticket;
import com.kachanov.mentoring.concurrency.ticket.TicketStorage;

import java.util.*;

public class ReturnTicketsBehavior extends AbstractCustomerBehavior {

    @Override
    public void performAction(Customer customer, TicketStorage ticketStorage) {
        if (!customer.getTickets().isEmpty()) {
            List<Ticket> tickets = getRandomTickets(customer);
            System.out.println(customer + " wants to return " + tickets.size() + " tickets");
            ticketStorage.returnTickets(tickets);
            customer.getTickets().removeAll(tickets);
            System.out.println(customer + " returns " + tickets.size() + " tickets");
        } else {
            System.out.println(customer + " wants to return tickets but he doesn't have ones");
        }
    }

    @Override
    public boolean appliesTo(Customer customer) {
        return !customer.getTickets().isEmpty();
    }

    private List<Ticket> getRandomTickets(Customer customer) {
        Set<Ticket> result = new HashSet<>();
        Random random = new Random();
        int totalTicketsNumber = customer.getTickets().size();
        int ticketsNumber = random.nextInt(totalTicketsNumber) + 1;
        while (result.size() < ticketsNumber) {
            result.add(customer.getTickets().get(random.nextInt(totalTicketsNumber)));
        }
        return new ArrayList<>(result);
    }
}
