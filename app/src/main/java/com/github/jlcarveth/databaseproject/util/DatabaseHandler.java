package com.github.jlcarveth.databaseproject.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.jlcarveth.databaseproject.model.Product;
import com.github.jlcarveth.databaseproject.model.ProductContract;

import java.util.ArrayList;

/**
 * Created by John on 11/2/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "testsqlite";
    private static final int DB_VERSION = 1;

    public DatabaseHandler (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductContract.ProductEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProductContract.ProductEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<Product> getProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                ProductContract.ProductEntry.TABLE_NAME,
                null);
        ArrayList al = new ArrayList<Product>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(
                ProductContract.ProductEntry.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(
                    ProductContract.ProductEntry.COLUMN_NAME));
            String sku = cursor.getString(cursor.getColumnIndexOrThrow(
                    ProductContract.ProductEntry.COLUMN_SKU));
            String cost = cursor.getString(cursor.getColumnIndexOrThrow(
                    ProductContract.ProductEntry.COLUMN_COST
            ));

            Product p = new Product(name,
                    Integer.parseInt(sku),
                    Integer.parseInt(cost),
                    Integer.parseInt(id));

            al.add(p);
        }

        return al;
    }

    /**
     * Deletes the specified product from the DB
     * @param p the product to delete
     * @return true if the product was removed
     */
    public boolean deleteProduct(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Cannot be null");
        }

        long row = this.getWritableDatabase().delete(ProductContract.ProductEntry.TABLE_NAME,
                ProductContract.ProductEntry.COLUMN_ID + "=" + p.getId(), null);

        return row == 1;
    }

    public boolean addProduct(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Cannot be null.");
        }

        ContentValues cv = new ContentValues();

        cv.put(ProductContract.ProductEntry.COLUMN_NAME, p.getName());
        cv.put(ProductContract.ProductEntry.COLUMN_SKU, String.valueOf(p.getSku()));
        cv.put(ProductContract.ProductEntry.COLUMN_COST, String.valueOf(p.getCost()));

        long result = this.getWritableDatabase()
                .insert(ProductContract.ProductEntry.TABLE_NAME,
                        null,
                        cv);

        return (result > 0);
    }
}
