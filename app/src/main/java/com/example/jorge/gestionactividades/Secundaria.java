package com.example.jorge.gestionactividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class Secundaria extends Activity {
    private ArrayList<Actividad> alActividad = new ArrayList<Actividad>();
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_secundaria);
        alActividad = getIntent().getExtras().getParcelableArrayList("actividades");
        posicion = getIntent().getExtras().getInt("posicion");
        final FragmentoDetalle fdetalle = (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fragmentoSecundaria);
        fdetalle.setActividad(getApplicationContext(),alActividad,posicion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        finish();
    }
}
