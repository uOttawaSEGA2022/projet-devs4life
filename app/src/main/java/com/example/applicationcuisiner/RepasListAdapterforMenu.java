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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepasListAdapterforMenu extends ArrayAdapter<Repas> {

    private View listitemView;
    private ArrayList RepasArrayList;
    private  FirebaseFirestore db;


    // constructor for our list view adapter.
    public RepasListAdapterforMenu(@NonNull Context context, ArrayList<Repas> RepasArrayList) {
        super(context, 0, RepasArrayList);
        this.RepasArrayList = RepasArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.menulist, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Repas repas = getItem(position);

        // initializing our UI components of list view item.
        TextView name = listitemView.findViewById(R.id.tv_menuName);
        TextView description = listitemView.findViewById(R.id.tv_menuDescription);
        TextView price = listitemView.findViewById(R.id.tv_price);
        TextView cook = listitemView.findViewById(R.id.tv_cookName);
        TextView typeDeRepas = listitemView.findViewById(R.id.tv_repasType);
        TextView typeDeCuisine = listitemView.findViewById(R.id.tv_cuisineType);
        TextView tvError = listitemView.findViewById(R.id.tv_errorDeleteFromMenu);



        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
         name.setText("Nom: " + repas.getName());
         description.setText("Description: " + repas.getDescription());
         price.setText("Prix: " +repas.getPrice());
         cook.setText("Cuisinier: " +repas.getCook());
         typeDeRepas.setText("Repas: " +repas.getTypeDeRepas());
         typeDeCuisine.setText("Cuisine: " +repas.getTypeDeCuisine());


        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                //Toast.makeText(getContext(), "Item clicked is : " + repas.getName(), Toast.LENGTH_SHORT).show();
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(getContext(), listitemView);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_repas_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        // menu item select listener

                        db = FirebaseFirestore.getInstance();

                            if (menuItem.getTitle().equals("Delete")) {
                                System.out.println("j'ai cliquer sur del");
                                System.out.println(repas.getName());
                                DocumentReference docRef = db.collection("repasProp").document(repas.getName()+repas.getCook());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                System.out.println("ledocument est propose");
                                                System.out.println("we cannot delete");

                                                //would like to change a TextView here
                                                tvError.setText("Can't delete if it's in repas proposés");
                                                //Toast.makeText(getContext(), "We cannot delete something that is in repas Proposes", Toast.LENGTH_SHORT).show();



                                            } else {
                                                Log.d(TAG, "No such document");
                                                db = FirebaseFirestore.getInstance();
                                                db.collection("menu").document(repas.getName()+repas.getCook())
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                // notifyDataSetChanged();
                                                                RepasArrayList.remove(position);
                                                                listitemView.refreshDrawableState();
                                                                notifyDataSetChanged();

                                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error deleting document", e);
                                                            }
                                                        });
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());

                                        }
                                    }
                                });


                            } else if (menuItem.getTitle().equals("Add to repas propose")) { //ajouter a firestore
                                System.out.println("j'ai cliquer sur add");

                                System.out.println("le nom du repas cliquer est " + repas.getName());

                                db = FirebaseFirestore.getInstance();

                                String name = repas.getName();
                                String description = repas.getDescription();
                                String price = repas.getPrice();
                                String typeFood = repas.getTypeDeCuisine();
                                String typeRepas = repas.getTypeDeRepas();
                                String cook = repas.getCook();
                                Map<String, Object> repasProp = new HashMap<>();
                                repasProp.put("name", name);
                                repasProp.put("description", description);
                                repasProp.put("price", price);
                                repasProp.put("typeDeCuisine", typeFood);
                                repasProp.put("typeDeRepas", typeRepas);
                                repasProp.put("cook", cook);

                                db.collection("repasProp").document(name+cook)
                                        .set(repasProp)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });





                            }

                       // Toast.makeText(getContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
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

