package com.github.jlcarveth.databaseproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jlcarveth.databaseproject.model.Product;
import com.github.jlcarveth.databaseproject.model.ProductContract;
import com.github.jlcarveth.databaseproject.util.DatabaseHandler;
import com.github.jlcarveth.databaseproject.util.DatabaseListener;
import com.github.jlcarveth.databaseproject.util.ProductAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements DatabaseListener {

    private EditText nameField, skuField, costField;
    private TextView warning;
    private ListView lv;

    private DatabaseHandler dbHandler;

    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = (EditText) findViewById(R.id.pname_field);
        skuField = (EditText) findViewById(R.id.psku_field);
        costField = (EditText) findViewById(R.id.pcost_field);
        warning = (TextView) findViewById(R.id.warningText);

        dbHandler = new DatabaseHandler(getApplicationContext());

        ArrayList<Product> products = dbHandler.getProducts();
        adapter = new ProductAdapter(this, products);

        adapter.setDatabaseListener(this);

        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
    }

    public void addProduct(View v) {
        if (!validate()) {
            warning.setText("Input not valid");
        }

        warning.setText("");

        String name = nameField.getText().toString();
        String sku = skuField.getText().toString();
        String cost = costField.getText().toString();

        Product p = new Product(name, Integer.parseInt(sku), Integer.parseInt(cost), -99);
        dbHandler.addProduct(p);

        adapter.clear();
        adapter.addAll(dbHandler.getProducts());

        nameField.setText("");
        skuField.setText("");
        costField.setText("");
    }

    /**
     * Validates the text fields before sending to DB
     * @return true if the fields are valid
     */
    private boolean validate() {
        boolean valid = true;

        if (nameField.getText().toString().length() == 0) {
            valid = false;
            return valid;
        }

        if (skuField.getText().toString().length() == 0) {
            valid = false;
            return valid;
        }

        if (costField.getText().toString().length() == 0) {
            valid = false;
            return valid;
        }

        try {
            int sku = Integer.parseInt(skuField.getText().toString());
            int cost = Integer.parseInt(costField.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }

        return valid;
    }

    @Override
    public void dataChanged() {
        adapter.clear();
        adapter.addAll(dbHandler.getProducts());
    }
}
