package com.zipwhip.executors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/17/11
 * Time: 11:57 AM
 *
 * Execute them synchronously.
 */
public class SimpleBulkExecutor extends SimpleExecutor implements BulkExecutor {

    private static final Callable<Void> EMPTY_CALLABLE = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            return null;
        }
    };

    @Override
    public <V> Future<V> execute(Callable<V> callable) {
        FutureTask<V> task = new FutureTask<V>(callable);

        task.run();

        return task;
    }

    @Override
    public Future<Void> execute(List<Runnable> runnables) {
        FutureTask<Void> task = new FutureTask<Void>(EMPTY_CALLABLE);

        for(Runnable runnable : runnables) {
            runnable.run();
        }

        task.run();

        return task;
    }

}
