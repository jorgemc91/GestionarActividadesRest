package com.example.jorge.gestionactividades;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentoDetalle extends Fragment {
    private ArrayList<Actividad> alActividades = new ArrayList<Actividad>();
    private View v;
    private TextView tvLugarS,tvFecha,tvDescripcion,tvTipo;


    public FragmentoDetalle() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragmento_detalle, container, false);
        return v;
    }

    public void setActividad(Context context,ArrayList al, int position){
        alActividades = al;
        tvLugarS = (TextView) v.findViewById(R.id.tvLugarS);
        tvFecha = (TextView) v.findViewById(R.id.tvFecha);
        tvDescripcion = (TextView) v.findViewById(R.id.tvDescripcion);
        tvTipo = (TextView) v.findViewById(R.id.tvTipo);
        tvLugarS.setText(alActividades.get(position).getLugarI());
        tvFecha.setText(alActividades.get(position).getFechaI());
        tvDescripcion.setText(alActividades.get(position).getDescripcion());
        tvTipo.setText(alActividades.get(position).getTipo());
    }

}
