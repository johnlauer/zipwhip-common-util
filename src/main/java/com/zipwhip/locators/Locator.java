package com.zipwhip.locators;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: Nov 10, 2010
 * Time: 11:18:24 PM
 * <p/>
 * Locate an item by key.
 */
public interface Locator<T> {

    T locate(String key);

}
