package com.github.jlcarveth.databaseproject.util;

/**
 * Created by John on 11/2/2017.
 */

public interface DatabaseListener {
    /**
     * Called whenever changes are made to the Database,
     * so any classes using DB data can update
     */
    abstract void dataChanged();
}
