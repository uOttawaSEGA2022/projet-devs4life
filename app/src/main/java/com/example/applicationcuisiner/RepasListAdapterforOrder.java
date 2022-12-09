package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class RepasListAdapterforOrder  extends ArrayAdapter<Commande> {

    private View listitemView;
    private ArrayList OrderArrayList;
    private FirebaseFirestore db;


    // constructor for our list view adapter.
    public RepasListAdapterforOrder(@NonNull Context context, ArrayList<Commande> OrderArrayList) {
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
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.menulistfororder, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Commande order = getItem(position);
        //Repas repas = getItem(position);

        // initializing our UI components of list view item.
        TextView orderName = listitemView.findViewById(R.id.tv_menuNameO);
        TextView client = listitemView.findViewById(R.id.tv_clientNameO);
        TextView orderStatus= listitemView.findViewById(R.id.tv_orderNumberO);



        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        orderName.setText("Nom: " + order.getName());
        client.setText("Client: "+ order.getClient());
        orderStatus.setText("Status: " + order.getStatus());


        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                // Toast.makeText(getContext(), "Item clicked is : " + repas.getName(), Toast.LENGTH_SHORT).show();
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(getContext(), listitemView);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_repas_order, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {


                        db = FirebaseFirestore.getInstance();

                        if (menuItem.getTitle().equals("Accept")) {
                            System.out.println("j'ai cliquer sur accept");
                            System.out.println(order.getId());
                            DocumentReference docRef = db.collection("repasOrdered").document(order.getId());

                            if(order.getStatus().equals("Attente"))
                            {
                                docRef.update("status", "Accepted")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                                            Commande newOrder = new Commande(order.getName(), order.getDescription(), order.getPrice(), order.getCook(), order.getTypeDeCuisine(), order.getTypeDeRepas(), order.getRating(), order.getClient(), "Accepted", order.getId());
                                            OrderArrayList.remove(position);
                                            OrderArrayList.add(position, newOrder);

                                            listitemView.refreshDrawableState();
                                            notifyDataSetChanged();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating document", e);
                                        }
                                    });
                        } else {
                                //TODO: Message d'erreur
                                System.out.println("order has already been refused can't change it");
                            }


                        } else if(menuItem.getTitle().equals("Refuse")) {
                            System.out.println("j'ai cliquer sur refuse");
                            System.out.println(order.getId());
                            DocumentReference docRef = db.collection("repasOrdered").document(order.getId());
                            if (order.getStatus().equals("Attente")) {
                                docRef.update("status", "Refused")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                Commande newOrder = new Commande(order.getName(), order.getDescription(), order.getPrice(), order.getCook(), order.getTypeDeCuisine(), order.getTypeDeRepas(), order.getRating(), order.getClient(), "Refused", order.getId());
                                                OrderArrayList.remove(position);
                                                OrderArrayList.add(position, newOrder);

                                                listitemView.refreshDrawableState();
                                                notifyDataSetChanged();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });

                                //maybe the client will delete the document
                            }
                            else {
                                //TODO: Message d'erreur
                                System.out.println("order has already been accepted can't change it");
                            }
                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        return listitemView;
    }



}
