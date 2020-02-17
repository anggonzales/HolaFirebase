package com.example.holafirebaserelease;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.btnSave)
    Button btnSave;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterProducto adaptador;
    private ArrayList<Producto> misdatos;

    private static final String PATH_PRODUCTO = "PRODUCTOS";
    FirebaseDatabase database;
    DatabaseReference reference;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView = findViewById(R.id.recycler_view);
        //database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference(PATH_PRODUCTO);
        misdatos = new ArrayList<>();
        adaptador = new AdapterProducto(this,
                misdatos);
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        query = FirebaseDatabase.getInstance().getReference("PRODUCTOS")
                .orderByChild("precio")
                .startAt(100.00);

        Consultaquery();
        //AddItems();
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        Producto producto = new Producto(etName.getText().toString().trim(), Double.valueOf(etPrice.getText().toString()));
        //reference.push().setValue(producto);

        Producto productoupdate = getProducto(producto.getNombre());
        if(productoupdate != null){
            reference.child(productoupdate.getId()).setValue(producto);
        } else {
            reference.push().setValue(producto);
        }
        etName.setText("");
        etPrice.setText("");

    }

    Producto getProducto(String nombre){
        for (Producto prod : misdatos) {
            if (prod.getNombre().equals(nombre)){
                return prod;
            }
        }
        return null;
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            misdatos.clear();
            if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                   Producto producto = snapshot.getValue(Producto.class);
                   misdatos.add(producto);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    void Consultaquery(){
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                producto.setId(dataSnapshot.getKey());
                if (!misdatos.contains(producto)) {
                    misdatos.add(producto);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                producto.setId(dataSnapshot.getKey());
                int index = -1;
                for (Producto prod : misdatos) {
                    Log.i("iteracion", prod.getId() + " = " + producto.getId());
                    index++;
                    if (prod.getId().equals(producto.getId())) {
                        misdatos.set(index, producto);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                producto.setId(dataSnapshot.getKey());
                int index = -1;
                for (Producto prod : misdatos) {
                    Log.i("iteracion", prod.getId() + " = " + producto.getId());
                    index++;
                    if (prod.getId().equals(producto.getId())) {
                        misdatos.remove(index);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    void AddItems() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                producto.setId(dataSnapshot.getKey());
                if (!misdatos.contains(producto)) {
                    misdatos.add(producto);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                producto.setId(dataSnapshot.getKey());
                int index = -1;
                for (Producto prod : misdatos) {
                    Log.i("iteracion", prod.getId() + " = " + producto.getId());
                    index++;
                    if (prod.getId().equals(producto.getId())) {
                        misdatos.set(index, producto);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                producto.setId(dataSnapshot.getKey());
                int index = -1;
                for (Producto prod : misdatos) {
                    Log.i("iteracion", prod.getId() + " = " + producto.getId());
                    index++;
                    if (prod.getId().equals(producto.getId())) {
                        misdatos.remove(index);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(MainActivity.this, "Se movio el producto", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Transaccion cancelada", Toast.LENGTH_LONG).show();
            }
        });
    }
}
