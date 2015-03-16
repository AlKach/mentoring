package com.kachanov.mentoring.concurrency.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketStorage {

    private List<Train> trains = new ArrayList<>();

    private Map<Train, List<Ticket>> tickets = new HashMap<>();

    private TicketStorage() {

    }

    public static TicketStorageBuilder builder() {
        return new TicketStorageBuilder();
    }

    public int getTrainsCount() {
        return trains.size();
    }

    public Ticket sellTicket(int trainNumber) {
        return null;
    }

    public Ticket sellTicketImmediate(int trainNumber) {
        return null;
    }

    public List<Ticket> sellTickets(int trainNumber, int ticketsCount) {
        return null;
    }

    public List<Ticket> sellTicketsImmediate(int trainNumber, int ticketsCount) {
        return null;
    }

    public void returnTicket(Ticket ticket) {

    }

    public void returnTickets(List<Ticket> tickets) {

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
                ticketStorage.tickets.put(train, tickets);
            }
            return ticketStorage;
        }

    }
}
