package com.example.applicationcuisiner;
import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RepasListAdapter extends ArrayAdapter<Repas> {

    // constructor for our list view adapter.
    public RepasListAdapter(@NonNull Context context, ArrayList<Repas> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
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



        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
         name.setText(repas.getName());
         description.setText(repas.getDescription());
         price.setText(repas.getPrice());
         cook.setText(repas.getCook());
         typeDeRepas.setText(repas.getTypeDeRepas());
         typeDeCuisine.setText(repas.getTypeDeCuisine());


        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + repas.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}