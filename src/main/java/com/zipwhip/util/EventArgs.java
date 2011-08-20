package com.zipwhip.util;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 6/15/11
 * Time: 5:28 PM
 * <p/>
 * A future placeholder.
 */
public class EventArgs<TArgs> {

    /**
     * Determines if the request was successful or not.
     */
    Throwable error;

    /**
     * The result
     */
    TArgs result;

    public boolean isSuccess() {
        return error == null;
    }

    public TArgs getResult() {
        return result;
    }

    public void setResult(TArgs result) {
        this.result = result;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
