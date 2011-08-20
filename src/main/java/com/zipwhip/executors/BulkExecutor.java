package com.zipwhip.executors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/17/11
 * Time: 11:49 AM
 *
 * Some enhancements to the executor
 */
public interface BulkExecutor extends Executor {

    /**
     * Run this and give me the result
     *
     * @param callable something you want to run
     * @return a future that tells you when the callable was completed
     */
    public <V> Future<V> execute(Callable<V> callable);

    /**
     * Run these things in parallel.
     *
     * @param runnables
     * @return a future that tells you when the runnables are completed
     */
    public Future<Void> execute(List<Runnable> runnables);


}
