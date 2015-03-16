package com.kachanov.mentoring.concurrency;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.customer.behavior.CustomerPerformer;

public class App {

    public static void main(String[] args) {
        for (int i = 0; i < Constants.CUSTOMERS_COUNT; i++) {
            new CustomerPerformer(new Customer(i)).start();
        }
    }

}
