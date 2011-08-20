package com.zipwhip.events;

import com.zipwhip.executors.SimpleExecutor;
import com.zipwhip.lifecycle.DestroyableBase;
import com.zipwhip.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/1/11
 * Time: 4:31 PM
 * <p/>
 * A base class that simplifies the act of being observed
 */
public class ObservableHelper<T> extends DestroyableBase implements Observable<T> {

    private Executor executor;
    private List<Observer<T>> observers;

    public ObservableHelper() {
        this(new SimpleExecutor());
    }

    public ObservableHelper(Executor executor) {
        this.executor = executor;
    }

    @Override
    public synchronized void addObserver(Observer<T> observer) {
        if (observers == null) {
            observers = new LinkedList<Observer<T>>();
        }
        observers.add(observer);
    }

    @Override
    public synchronized void removeObserver(Observer<T> observer) {
        if (observers == null) {
            return;
        }
        observers.remove(observer);
    }

    /**
     * Notify all the observers that their thing occurred.
     *
     * @param sender who is notifying of the event!
     * @param item   the item that the observers will hear about.
     */
    public synchronized void notifyObservers(final Object sender, final T item) {
        if (observers == null || observers.isEmpty()) {
            return;
        }

        final Object[] asdf = observers.toArray();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (Object observer : asdf) {
                    ((Observer<T>)observer).notify(sender, item);
                }
            }
        });

    }

    @Override
    protected synchronized void onDestroy() {
        if (observers != null){
            observers.clear();
        }
    }
}
