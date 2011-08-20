package com.zipwhip.executors;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/17/11
 * Time: 2:58 PM
 *
 * A future that precomputes the result
 */
public class FakeFuture<T> extends FutureTask<T> implements Future<T> {

    private static final Runnable NULL_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            // fake NOOP
        }
    };

    public FakeFuture(T result) {
        super(NULL_RUNNABLE, result);
    }

}
