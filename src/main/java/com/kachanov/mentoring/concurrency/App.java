package com.kachanov.mentoring.concurrency;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.customer.executor.CustomerPerformer;
import com.kachanov.mentoring.concurrency.customer.executor.PausableExecutorHolder;
import com.kachanov.mentoring.concurrency.ticket.TicketStorageValidator;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < Constants.CUSTOMERS_COUNT; i++) {
            Customer customer = new Customer(i);
            customers.add(customer);
            PausableExecutorHolder.getInstance().execute(new CustomerPerformer(customer));
        }

        new Thread(new TicketStorageValidator(customers)).start();
    }

}
