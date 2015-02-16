package com.example.jorge.gestionactividades;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Calendar;


public class AnadirActividad extends Activity {

    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";
    private ArrayList<Profesor> profesores = new ArrayList<Profesor>();
    private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
    private Spinner spProfesor;
    private Spinner spGrupo;
    private Spinner spTipo;
    private ArrayList<String> nomProfesor = new ArrayList<String>();
    private ArrayList<String> nomGrupos = new ArrayList<String>();
    private String tipos[] = {"Extraescolares","Complementarias"};
    private EditText etLSalida,etLRegreso,etDescripcion;

    public static TextView tvSetFechaD,tvSetFechaH;

    public static Boolean FECHA = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_anadir_actividad);

        etLSalida  = (EditText) findViewById(R.id.etLSalida);
        etLRegreso  = (EditText) findViewById(R.id.etLRegreso);
        etDescripcion  = (EditText) findViewById(R.id.etDescripcion);

        tvSetFechaD = (TextView) findViewById(R.id.tvSetFechaD);
        tvSetFechaH = (TextView) findViewById(R.id.tvSetFechaH);

        spProfesor = (Spinner) findViewById(R.id.spProfesor);


        spGrupo = (Spinner) findViewById(R.id.spGrupo);


        spTipo = (Spinner) findViewById(R.id.spTipo);
        ArrayAdapter<String> adapterT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        spTipo.setAdapter(adapterT);

        cargarProfesores();
        cargarGrupos();
    }

    //Obtener profesores
    private void cargarProfesores(){
        String peticiones = "profesor";
        GetProfesores get = new GetProfesores();
        get.execute(peticiones);
    }

    private class GetProfesores extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String[] params) {
            String[] r = new String[params.length];
            int contador = 0;
            for (String s : params){
                r[contador] = ClienteRestFul.get(URLBASE + s);
                contador++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] r) {
            super.onPostExecute(r);
            JSONTokener tokener = new JSONTokener(r[0]);
            try {
                JSONArray lista = new JSONArray(tokener);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject objeto = lista.getJSONObject(i);
                    String nombre = objeto.getString("nombre");
                    String id = objeto.getString("id");
                    Profesor p = new Profesor(id,nombre);
                    profesores.add(p);
                    nomProfesor.add(profesores.get(i).getNombre());
                    ArrayAdapter<String> adapterP = new ArrayAdapter<String>(AnadirActividad.this, android.R.layout.simple_spinner_item, nomProfesor);
                    spProfesor.setAdapter(adapterP);
                }
            } catch (JSONException e) {
            }
        }
    }

    //Obtener grupos
    private void cargarGrupos(){
        String peticiones = "grupo";
        GetGrupos get = new GetGrupos();
        get.execute(peticiones);
    }

    private class GetGrupos extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String[] params) {
            String[] r = new String[params.length];
            int contador = 0;
            for (String s : params){
                r[contador] = ClienteRestFul.get(URLBASE + s);
                contador++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] r) {
            super.onPostExecute(r);
            JSONTokener tokener = new JSONTokener(r[0]);
            try {
                JSONArray lista = new JSONArray(tokener);
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject objeto = lista.getJSONObject(i);
                    String id = objeto.getString("id");
                    String nombre = objeto.getString("grupo");
                    Grupo g = new Grupo(id,nombre);
                    grupos.add(g);
                    nomGrupos.add(grupos.get(i).getNombre());
                    ArrayAdapter<String> adapterG = new ArrayAdapter<String>(AnadirActividad.this, android.R.layout.simple_spinner_item, nomGrupos);
                    spGrupo.setAdapter(adapterG);
                }
            } catch (JSONException e) {
            }
        }
    }

    /* Agregar actividad */
    public void agregarActividad(View v){
        PostRESTFul post = new PostRESTFul();
        post.execute();
        Intent i = new Intent(this,Principal.class);
        startActivity(i);
    }

    private class PostRESTFul extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            JSONObject objetoJSON = new JSONObject();
            try {
                String idProfesor = profesores.get(nomProfesor.indexOf(spProfesor.getSelectedItem())).getId();
                objetoJSON.put("idprofesor", idProfesor);
                objetoJSON.put("tipo", String.valueOf(spTipo.getSelectedItem()));
                objetoJSON.put("fechai", tvSetFechaD.getText().toString());
                objetoJSON.put("fechaf", tvSetFechaH.getText().toString());
                objetoJSON.put("lugari", etLSalida.getText().toString());
                objetoJSON.put("lugarf", etLRegreso.getText().toString());
                objetoJSON.put("descripcion", etDescripcion.getText().toString());
                objetoJSON.put("alumno", "jorge");
                Log.v("JSON", objetoJSON+"");
                return ClienteRestFul.post(URLBASE+"actividad", objetoJSON);
            }catch (JSONException e){
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String r) {
            super.onPostExecute(r);
            try {
                JSONObject objeto = new JSONObject(r);
                if(!objeto.getString("r").equals("0")){
                    Toast.makeText(AnadirActividad.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AnadirActividad.this, "No se ha podido insertar", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /***************************************** DatePicker *********************************************************************/

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (FECHA == true){
                tvSetFechaD.setText(year + "-" + month + "-" + day);
            }else if(FECHA == false){
                tvSetFechaH.setText(year + "-" + month + "-" + day);
            }
        }
    }

    public void fechaDesde(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Fecha");
        FECHA = true;
    }


    public void fechaHasta(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Fecha");
        FECHA = false;
    }
}
