package com.zipwhip.executors;

import java.util.concurrent.Executor;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/2/11
 * Time: 2:42 PM
 * <p/>
 * Executes something synchronously
 */
public class SimpleExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }

}
