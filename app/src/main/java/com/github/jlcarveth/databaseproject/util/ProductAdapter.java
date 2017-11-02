package com.github.jlcarveth.databaseproject.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.jlcarveth.databaseproject.MainActivity;
import com.github.jlcarveth.databaseproject.R;
import com.github.jlcarveth.databaseproject.model.Product;
import java.util.ArrayList;

/**
 * Created by John on 11/2/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    /**
     * Since this class can edit DB data (via Dialog deletion)
     * it needs a dbListener. This is set in the MainActivity
     */
    private DatabaseListener dbListener;

    public ProductAdapter(@NonNull Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Product p = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.lvName);
        TextView sku = (TextView) convertView.findViewById(R.id.lvSKU);
        TextView cost = (TextView) convertView.findViewById(R.id.lvCOST);

        name.setText("Name: "+ p.getName());
        sku.setText("SKU: " + String.valueOf(p.getSku()));
        cost.setText("Cost: $"+p.getCost());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Delete This Entry?");
                builder.setTitle("Confirm Deletion:");

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        db.deleteProduct(p);
                        dbListener.dataChanged();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        return convertView;
    }

    public void setDatabaseListener(DatabaseListener dbl) {
        dbListener = dbl;
    }

}
