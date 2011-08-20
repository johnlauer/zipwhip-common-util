package com.zipwhip.util;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: Oct 12, 2010
 * Time: 7:26:11 PM
 * <p/>
 * An interface for generic factory
 */
public interface Factory<T> {

    /**
     * Create an item via our factory strategy.
     *
     * @return the new item that u just created
     * @throws Exception if there was a problem creating/validating the item
     */
    T create() throws Exception;

}
