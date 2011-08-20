package com.zipwhip.util;

import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/19/11
 * Time: 4:22 PM
 *
 * Serialize something to a string
 */
public interface Serializer<T> {

    /**
     * Serialise your object to this output
     *
     * @param item the thing you want to serialize
     * @return
     */
    String serialize(T item);

}
