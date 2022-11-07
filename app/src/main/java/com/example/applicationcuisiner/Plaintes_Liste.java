package com.example.applicationcuisiner;

/**
 * Created by Miguel Garzón on 2017-05-09.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Plaintes_Liste extends ArrayAdapter<Plainte> {
    private Activity context;
    List<Plainte> plaintes;

    public Plaintes_Liste(Activity context, List<Plainte> products) {
        super(context, R.layout.plaintes_liste, products);
        this.context = context;
        this.plaintes = plaintes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.plaintes_liste, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Plainte product = plaintes.get(position);
        textViewName.setText(product.getCuisinierName());
        textViewPrice.setText(String.valueOf(product.getPlainte()));
        return listViewItem;
    }
}
