package com.kachanov.mentoring.concurrency.ticket;

import com.kachanov.mentoring.concurrency.customer.Customer;

import java.util.*;

public class TicketStorage {

    private final List<Train> trains = new ArrayList<>();

    private final Map<Train, List<Ticket>> ticketsPool = new HashMap<>();

    private final Object workLock = new Object();

    private volatile boolean isWorking = true;

    private TicketStorage() {

    }

    public static TicketStorageBuilder builder() {
        return new TicketStorageBuilder();
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public Object getWorkLock() {
        return workLock;
    }

    public List<Ticket> sellTickets(int trainNumber, int ticketsCount) {
        waitValidation();
        Train train = trains.get(trainNumber);
        List<Ticket> soldTickets = new ArrayList<>();
        synchronized (train) {
            List<Ticket> ticketsForTrain = ticketsPool.get(train);
            if (ticketsForTrain.size() >= ticketsCount) {
                soldTickets.addAll(ticketsForTrain.subList(0, ticketsCount));
                ticketsForTrain.removeAll(soldTickets);
                System.out.println("Sold " + soldTickets.size() + " tickets for "
                        + train + " (" + ticketsForTrain.size() + " remaining)");
            } else {
                System.out.println("Can't sell " + ticketsCount + " tickets for " + train);
            }
        }

        return soldTickets;
    }

    public void returnTickets(List<Ticket> returnedTickets) {
        waitValidation();
        Map<Train, List<Ticket>> returnedTicketsMap = new HashMap<>();
        for (Ticket ticket : returnedTickets) {
            Train train = ticket.getTrain();
            if (!returnedTicketsMap.containsKey(train)) {
                returnedTicketsMap.put(train, new ArrayList<Ticket>());
            }

            returnedTicketsMap.get(train).add(ticket);
        }

        for (Map.Entry<Train, List<Ticket>> entry : returnedTicketsMap.entrySet()) {
            Train train = entry.getKey();
            List<Ticket> tickets = entry.getValue();

            synchronized (train) {
                List<Ticket> ticketsForTrain = ticketsPool.get(train);
                ticketsForTrain.addAll(tickets);

                System.out.println("Returned " + tickets.size() + " tickets for " + train + " (" + ticketsForTrain.size() + " remaining)");
            }
        }
    }

    public boolean validateState(List<Customer> customers, int totalTickets) {
        for (Train train : ticketsPool.keySet()) {
            System.out.print("Waiting for " + train + " to be unlocked... ");
            synchronized (train) {
                System.out.println("done");
            }
        }

        Set<Ticket> checkedTickets = new HashSet<>();
        for (Customer customer : customers) {
            for (Ticket ticket : customer.getTickets()) {
                if (checkedTickets.contains(ticket)) {
                    System.out.println(ticket + " sold to two different customers!");
                    return false;
                } else {
                    checkedTickets.add(ticket);
                }
            }
        }

        for (List<Ticket> tickets : ticketsPool.values()) {
            for (Ticket ticket : tickets) {
                if (checkedTickets.contains(ticket)) {
                    System.out.println(ticket + " sold to customer but not removed from pool");
                    return false;
                } else {
                    checkedTickets.add(ticket);
                }
            }
        }

        if (totalTickets != checkedTickets.size()) {
            System.out.println("Tickets number mismatch. Expected " + totalTickets + ", got " + checkedTickets.size());
            return false;
        }

        return true;
    }

    private void waitValidation() {
        while (!isWorking) {
            synchronized (workLock) {
                if (!isWorking) {
                    try {
                        System.out.println("Awaiting validation to complete...");
                        workLock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }

    public static class TicketStorageBuilder {

        private int trainsCount = 1;
        private int seatsPerTrain = 10;

        private TicketStorageBuilder() {

        }

        public TicketStorageBuilder trains(int trainsCount) {
            this.trainsCount = trainsCount;
            return this;
        }

        public TicketStorageBuilder seats(int seatsPerTrain) {
            this.seatsPerTrain = seatsPerTrain;
            return this;
        }

        public TicketStorage build() {
            TicketStorage ticketStorage = new TicketStorage();
            for (int i = 0; i < trainsCount; i++) {
                Train train = new Train(i);
                ticketStorage.trains.add(train);

                List<Ticket> tickets = new ArrayList<>();
                for (int j = 0; j < seatsPerTrain; j++) {
                    Ticket ticket = new Ticket(train, j);
                    tickets.add(ticket);
                }
                ticketStorage.ticketsPool.put(train, tickets);
            }
            return ticketStorage;
        }

    }
}
