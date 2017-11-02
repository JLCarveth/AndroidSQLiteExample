package com.github.jlcarveth.databaseproject.model;

import android.provider.BaseColumns;

/**
 * Created by John on 11/2/2017.
 */

public class ProductContract {
    private ProductContract() {}

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SKU = "sku";
        public static final String COLUMN_COST = "cost";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                        ProductEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        ProductEntry.COLUMN_NAME + " TEXT," +
                        ProductEntry.COLUMN_SKU + " INT," +
                        ProductEntry.COLUMN_COST + " INT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME;
    }
}
