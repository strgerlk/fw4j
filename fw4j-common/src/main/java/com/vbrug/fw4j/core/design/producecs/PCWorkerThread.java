package com.vbrug.fw4j.core.design.producecs;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class PCWorkerThread extends Thread {

    final PCPool pool;                // the pool this thread works in

    protected PCWorkerThread(PCPool pool) {
        super("aPCWorkerThread");
        this.pool = pool;
    }

    /**
     * Returns the pool hosting this thread.
     * @return the pool
     */
    public PCPool getPool() {
        return pool;
    }

}

