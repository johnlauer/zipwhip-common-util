package com.zipwhip.lifecycle;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 12/16/10
 * Time: 8:11 PM
 * <p/>
 * Indicates that an object can be actively destroyed.
 */
public interface Destroyable {

    /**
     * When you destroy this object, cascade the destruction it to the "destroyable" passed in.
     *
     * @param destroyable
     */
    void link(Destroyable destroyable);

    /**
     * Prevent the cascading destruction.
     *
     * @param destroyable
     */
    void unlink(Destroyable destroyable);

    /**
     * Destroy this item (and anything that it is linked to).
     */
    void destroy();

    /**
     * See if it's already been destroyed (since we can't control garbage collection).
     *
     * @return
     */
    boolean isDestroyed();

}
