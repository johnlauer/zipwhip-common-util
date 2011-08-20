package com.zipwhip.util;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 8/9/11
 * Time: 2:37 PM
 *
 * A Callable that returns a value and takes input.
 *
 */
public interface InputCallable<K,V> {

    K call(V input);

}
