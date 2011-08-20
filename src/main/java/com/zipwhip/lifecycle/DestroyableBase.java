package com.zipwhip.lifecycle;

import com.zipwhip.util.CollectionUtil;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 12/16/10
 * Time: 8:22 PM
 * <p/>
 * An easy to use class that is Destroyable, that you can extend.
 */
public abstract class DestroyableBase implements Destroyable {

    boolean destroyed;

    protected List<Destroyable> destroyables = null;

    public void link(Destroyable destroyable) {
        if (destroyable == null) {
            return;
        }

        destroyables = (List<Destroyable>) CollectionUtil.add(destroyables, destroyable);
    }

    public void unlink(Destroyable destroyable) {
        if (destroyable == null) {
            return;
        }
        if (CollectionUtil.isNullOrEmpty(destroyables)) {
            return;
        }
        destroyables.remove(destroyable);
    }

    public void destroy() {
        if (this.destroyed) {
            return;
        }
        this.destroyed = true;

        if (destroyables != null) {
            for (Destroyable destroyable : destroyables) {
                destroyable.destroy();
            }
            destroyables.clear();
            destroyables = null;
        }

        this.onDestroy();
    }

    protected abstract void onDestroy();

    public boolean isDestroyed() {
        return this.destroyed;
    }

}
