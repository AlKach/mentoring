package com.kachanov.mentoring.concurrency;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.customer.behavior.CustomerPerformer;
import com.kachanov.mentoring.concurrency.ticket.TicketStorageValidator;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < Constants.CUSTOMERS_COUNT; i++) {
            Customer customer = new Customer(i);
            customers.add(customer);
            new CustomerPerformer(customer).start();
        }

        new TicketStorageValidator(customers).start();
    }

}
