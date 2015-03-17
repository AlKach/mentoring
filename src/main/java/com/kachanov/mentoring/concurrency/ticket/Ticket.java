package com.kachanov.mentoring.concurrency.ticket;

public class Ticket implements Comparable<Ticket> {

    private Train train;

    private int seat;

    public Ticket(Train train, int seat) {
        this.train = train;
        this.seat = seat;
    }

    public Train getTrain() {
        return train;
    }

    public int getSeat() {
        return seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (seat != ticket.seat) return false;
        return train.equals(ticket.train);

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

    @Override
    public int compareTo(Ticket ticket) {
        int trainComparison = this.train.compareTo(ticket.train);
        int seatsComparison = this.seat > ticket.seat ? 1 : this.seat < ticket.seat ? -1 : 0;
        return trainComparison != 0 ? trainComparison : seatsComparison;
    }
}
