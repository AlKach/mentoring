package com.kachanov.mentoring.concurrency.ticket;

public class Ticket {

    private Train train;

    private int seat;

    public Ticket(Train train, int seat) {
        this.train = train;
        this.seat = seat;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (seat != ticket.seat) return false;
        if (!train.equals(ticket.train)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = train.hashCode();
        result = 31 * result + seat;
        return result;
    }

    @Override
    public String toString() {
        return "Ticket for " + train +
                ", seat #" + seat;
    }
}
