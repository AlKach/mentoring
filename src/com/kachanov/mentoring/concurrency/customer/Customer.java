package com.kachanov.mentoring.concurrency.customer;

import com.kachanov.mentoring.concurrency.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private int id;

    private List<Ticket> tickets = new ArrayList<>();

    public Customer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;

        return true;
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
