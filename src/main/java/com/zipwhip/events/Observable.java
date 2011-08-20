package com.zipwhip.events;

import com.zipwhip.lifecycle.Destroyable;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/1/11
 * Time: 4:22 PM
 * <p/>
 * Something that can be observed
 */
public interface Observable<T> extends Destroyable {

    /**
     * Add an observer to this object.
     *
     * @param observer the callback to add
     */
    void addObserver(Observer<T> observer);

    /**
     * Remove an observer from this object.
     *
     * @param observer the callback to remove
     */
    void removeObserver(Observer<T> observer);

}
