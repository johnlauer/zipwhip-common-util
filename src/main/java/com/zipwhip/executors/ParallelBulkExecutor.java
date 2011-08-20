package com.zipwhip.executors;

import com.zipwhip.lifecycle.DestroyableBase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 6/15/11
 * Time: 4:31 PM
 * <p/>
 * Executes them in parallel, but one "chunk" at a time.
 */
public class ParallelBulkExecutor extends DestroyableBase implements BulkExecutor {

    private static ParallelBulkExecutor instance = null;

    private ExecutorService executor;

    public ParallelBulkExecutor(String name) {
        // todo: figure out a way for this pool
        executor = Executors.newCachedThreadPool(new DefaultThreadFactory(name));
    }

    public ParallelBulkExecutor(Class<?> clazz) {
        this(clazz.getName());
    }

    public ParallelBulkExecutor() {
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void execute(Runnable command) {
        execute(Arrays.asList(command));
    }

    public <T> Future<T> execute(Callable<T> callable) {
        FutureTask<T> task = new FutureTask<T>(callable);

        executor.execute(task);

        return task;
    }

    public Future<Void> execute(List<Runnable> commands) {
        final CountDownLatch latch = new CountDownLatch(commands.size());

        for (final Runnable command : commands) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }

        return execute(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                latch.await(45, TimeUnit.SECONDS);
                return null;
            }
        });

    }

    public synchronized static ParallelBulkExecutor getInstance() {
        if (instance == null) {
            instance = new ParallelBulkExecutor();
        }
        return instance;
    }

    @Override
    public void destroy() {
        if (instance == this){
            throw new RuntimeException("Wow you tried to kill the singleton. you're bad.");
        }

        super.destroy();
    }

    @Override
    protected void onDestroy() {
        executor.shutdownNow();
    }

    /**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        DefaultThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + name +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
