package com.example.applicationcuisiner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RepasListAdapterforClientOrder extends ArrayAdapter<Commande> {

    private View listitemView;
    private ArrayList OrderArrayList;
    private FirebaseFirestore db;


    // constructor for our list view adapter.
    public RepasListAdapterforClientOrder(@NonNull Context context, ArrayList<Commande> OrderArrayList) {
        super(context, 0, OrderArrayList);
        this.OrderArrayList = OrderArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.menulistforclientorder, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Commande order = getItem(position);
        //Repas repas = getItem(position);

        // initializing our UI components of list view item.
        TextView orderName = listitemView.findViewById(R.id.tv_orderNameOC);
        TextView cook = listitemView.findViewById(R.id.tv_cookNameOC);
        TextView rating = listitemView.findViewById(R.id.tv_ratingOC);
        TextView orderStatus= listitemView.findViewById(R.id.tv_statusOC);



        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        orderName.setText("Nom: " + order.getName());
        cook.setText("Cook: "+ order.getClient());
        rating.setText("Rating: " + order.getRating());
        orderStatus.setText("Status: " + order.getStatus());


        return listitemView;
    }



}