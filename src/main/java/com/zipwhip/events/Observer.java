package com.zipwhip.events;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/1/11
 * Time: 3:51 PM
 * <p/>
 * A callback interface for when something occurs.
 */
public interface Observer<T> {

    /**
     * A generic signal notification.
     *
     * @param sender The sender might not be the same object every time, so we'll let it just be object, rather than generics.
     * @param item   - Rich object representing the notification.
     */
    void notify(Object sender, T item);

}
