package com.kachanov.mentoring.concurrency.customer;

import com.kachanov.mentoring.concurrency.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final int id;

    private final List<Ticket> tickets = new ArrayList<>();

    public Customer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id == customer.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer #" + id;
    }
}
