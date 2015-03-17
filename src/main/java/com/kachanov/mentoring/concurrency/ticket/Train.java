package com.kachanov.mentoring.concurrency.ticket;

public class Train implements Comparable<Train> {

    private final int id;

    public Train(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        return id == train.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Train #" + id;
    }

    @Override
    public int compareTo(Train train) {
        return this.id > train.id ? 1 : this.id < train.id ? -1 : 0;
    }
}
