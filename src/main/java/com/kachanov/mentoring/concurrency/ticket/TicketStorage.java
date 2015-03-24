package com.kachanov.mentoring.concurrency.ticket;

import com.kachanov.mentoring.concurrency.customer.Customer;
import com.kachanov.mentoring.concurrency.util.Pair;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketStorage {

    private final List<Train> trains = new ArrayList<>();

    private final Map<Train, Pair<Lock, List<Ticket>>> ticketsPool = new HashMap<>();

    private TicketStorage() {

    }

    public static TicketStorageBuilder builder() {
        return new TicketStorageBuilder();
    }

    public List<Ticket> sellTickets(int trainNumber, int ticketsCount) {
        Train train = trains.get(trainNumber);
        List<Ticket> soldTickets = new ArrayList<>();
        Pair<Lock, List<Ticket>> lockPair = ticketsPool.get(train);
        lockPair.getFirst().lock();
        try {
            List<Ticket> ticketsForTrain = lockPair.getSecond();
            if (ticketsForTrain.size() >= ticketsCount) {
                soldTickets.addAll(ticketsForTrain.subList(0, ticketsCount));
                ticketsForTrain.removeAll(soldTickets);
                System.out.println("Sold " + soldTickets.size() + " tickets for "
                        + train + " (" + ticketsForTrain.size() + " remaining)");
            } else {
                System.out.println("Can't sell " + ticketsCount + " tickets for " + train);
            }
        } finally {
            lockPair.getFirst().unlock();
        }

        return soldTickets;
    }

    public void returnTickets(List<Ticket> returnedTickets) {
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

            Pair<Lock, List<Ticket>> lockPair = ticketsPool.get(train);

            lockPair.getFirst().lock();
            try {
                List<Ticket> ticketsForTrain = lockPair.getSecond();
                ticketsForTrain.addAll(tickets);

                System.out.println("Returned " + tickets.size() + " tickets for " + train + " (" + ticketsForTrain.size() + " remaining)");
            } finally {
                lockPair.getFirst().unlock();
            }
        }
    }

    public boolean validateState(List<Customer> customers, int totalTickets) {
        try {
            for (Train train : ticketsPool.keySet()) {
                System.out.print("Waiting for " + train + " to be unlocked... ");
                Pair<Lock, List<Ticket>> lockPair = ticketsPool.get(train);
                lockPair.getFirst().lock();
                System.out.println("done");
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

            for (Pair<Lock, List<Ticket>> lockPair : ticketsPool.values()) {
                for (Ticket ticket : lockPair.getSecond()) {
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
        } finally {
            for (Train train : ticketsPool.keySet()) {
                Pair<Lock, List<Ticket>> lockPair = ticketsPool.get(train);
                lockPair.getFirst().unlock();
            }
        }

        return true;
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

                Pair<Lock, List<Ticket>> lockPair = new Pair<Lock, List<Ticket>>(new ReentrantLock(), tickets);
                ticketStorage.ticketsPool.put(train, lockPair);
            }
            return ticketStorage;
        }

    }
}
