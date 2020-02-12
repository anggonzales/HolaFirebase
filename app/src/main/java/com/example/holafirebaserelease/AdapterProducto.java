package com.example.holafirebaserelease;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> {
    private LayoutInflater inflador;
    ArrayList<Producto> datos;
    Context micontext;

    public AdapterProducto(Context context, ArrayList<Producto> datos) {
        this.datos = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.mi_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.miid.setText(datos.get(i).getId());
        holder.nombreproducto.setText(datos.get(i).getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, DetalleProducto.class);
                intent.putExtra("miid", datos.get(i).getId());
                intent.putExtra("nombreproducto", datos.get(i).getNombre());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView miid, nombreproducto;

        ViewHolder(View itemView) {
            super(itemView);
            miid = (TextView) itemView.findViewById(R.id.id_text);
            nombreproducto = (TextView) itemView.findViewById(R.id.nombre);
        }
    }
}
