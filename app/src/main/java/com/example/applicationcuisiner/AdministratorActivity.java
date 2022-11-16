package com.example.applicationcuisiner;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class AdministratorActivity extends AppCompatActivity {
    DatabaseReference databaseProducts;

    EditText editTextName;
    EditText editTextPlainte;
    Button buttonAddProduct;
    ListView listViewPlaintes;

    List<Plainte> plaintes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        databaseProducts = FirebaseDatabase.getInstance().getReference("plaintes");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPlainte = (EditText) findViewById(R.id.editTextPrice);
        listViewPlaintes = (ListView) findViewById(R.id.listViewProducts);
        buttonAddProduct = (Button) findViewById(R.id.addButton);

        plaintes = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

        listViewPlaintes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plainte product = plaintes.get(i);
                showUpdateDeleteDialog(product.getId(), product.getCooksname());
                return true;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount());
                System.out.println(dataSnapshot.getKey());

                plaintes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Plainte product = postSnapshot.getValue(Plainte.class);
                    plaintes.add(product);
                }
                PlainteList productsAdapter = new PlainteList(AdministratorActivity.this, plaintes);
                listViewPlaintes.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDeleteDialog(final String productId, String productName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
                if (!TextUtils.isEmpty(name)) {
                    updateProduct(productId, name, price);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(productId);
                b.dismiss();
            }
        });
    }

    private void updateProduct(String id, String name, double price) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("plaintes").child(id);
        Plainte product  = new Plainte(id,name,price);
        dR.setValue(product);
        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_SHORT).show();
    }

    private boolean deleteProduct(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("plaintes").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(),"Product Deleted", Toast.LENGTH_LONG).show();
        return true;

    }

    private void addProduct() {

        String name = editTextName.getText().toString().trim();
        double price = Double.parseDouble(String.valueOf(editTextPlainte.getText().toString()));
        if (!TextUtils.isEmpty(name)){

            String id = databaseProducts.push().getKey();
            Plainte product = new Plainte(id,name,price);
            databaseProducts.child(id).setValue(product);
            editTextName.setText("");
            editTextPlainte.setText("");
            Toast.makeText(this,"Product added",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Please enter a name",Toast.LENGTH_LONG).show();
        }

    }
}