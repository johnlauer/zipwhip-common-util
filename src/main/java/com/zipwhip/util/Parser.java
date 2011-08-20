package com.zipwhip.util;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 7/6/11
 * Time: 3:43 PM
 * <p/>
 * Represents a parser that can parse from 1 type to another
 */
public interface Parser<TSource, TDestination> {

    TDestination parse(TSource source) throws Exception;

}
