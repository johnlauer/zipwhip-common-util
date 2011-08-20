package com.zipwhip.events;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/17/11
 * Time: 4:22 PM
 * <p/>
 * A simple object for thread use.
 */
public class SimpleWait {

    private boolean released = false;
    private final Object object = new Object();

    public InterruptedException block() {
        synchronized (object) {
            try {
                if (released) {
                    return null;
                }
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return e;
            }
        }
        return null;
    }

    public void release() {
        synchronized (object) {
            released = true;
            object.notifyAll();
        }
    }

}
