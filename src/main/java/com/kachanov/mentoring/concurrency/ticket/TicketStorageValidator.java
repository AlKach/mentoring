package com.kachanov.mentoring.concurrency.ticket;

import com.kachanov.mentoring.concurrency.Constants;
import com.kachanov.mentoring.concurrency.customer.Customer;

import java.util.List;

public class TicketStorageValidator extends Thread {

    private final List<Customer> customers;

    public TicketStorageValidator(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        TicketStorage ticketStorage = TicketStorageHolder.getInstance();
        while (true) {
            try {
                Thread.sleep(Constants.TICKET_STORAGE_VALIDATION_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ticketStorage.setWorking(false);
            System.out.println("Validating tickets storage...");
            if (!ticketStorage.validateState(customers, Constants.TRAINS_COUNT * Constants.SEATS_PER_TRAIN)) {
                System.exit(0);
            }
            System.out.println("Validation passed!");
            ticketStorage.setWorking(true);
            synchronized (ticketStorage.getWorkLock()) {
                ticketStorage.getWorkLock().notifyAll();
            }
        }
    }
}
