package com.kachanov.mentoring.concurrency.ticket;

import com.kachanov.mentoring.concurrency.Constants;

public class TicketStorageHolder {

    private TicketStorageHolder() {

    }

    private static class TicketStorageHolderInternal {
        private static final TicketStorage INSTANCE = TicketStorage.builder()
                .trains(Constants.TRAINS_COUNT)
                .seats(Constants.SEATS_PER_TRAIN)
                .build();
    }

    public static TicketStorage getInstance() {
        return TicketStorageHolderInternal.INSTANCE;
    }

}
