package com.kachanov.mentoring.concurrency.customer.executor;

import com.kachanov.mentoring.concurrency.Constants;

public class PausableExecutorHolder {

    private static class PausableExecutorHolderInternal {
        private static final PausableExecutor INSTANCE = new PausableExecutor(Constants.CUSTOMERS_COUNT);
    }

    public static PausableExecutor getInstance() {
        return PausableExecutorHolderInternal.INSTANCE;
    }

}
