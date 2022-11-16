package com.example.applicationcuisiner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlainteList extends ArrayAdapter<Plainte> {
    private Activity context;
    List<Plainte> plaintes;

    public PlainteList(Activity context, List<Plainte> plaintes) {
        super(context, R.layout.plainte_list, plaintes);
        this.context = context;
        this.plaintes = plaintes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.plainte_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPlainte = (TextView) listViewItem.findViewById(R.id.textViewPlainte);
        TextView textViewTemp = (TextView) listViewItem.findViewById(R.id.textViewName);

        Plainte plainte = plaintes.get(position);
        textViewName.setText(plainte.getCooksname());
        textViewPlainte.setText(String.valueOf(plainte.getPlainte()));
        textViewTemp.setText(plainte.getTemp());
        return listViewItem;
    }
}
