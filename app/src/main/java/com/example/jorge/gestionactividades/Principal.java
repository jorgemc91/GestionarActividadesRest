package com.example.jorge.gestionactividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;


public class Principal extends Activity {

    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";
    private ListView lv;
    private AdaptadorActividad ada;
    private ArrayList<Actividad> actividades = new ArrayList<Actividad>();

    private final int ACTIVIDADDOS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        lv = (ListView)findViewById(R.id.listView);
        ada = new AdaptadorActividad(this, R.layout.detalle, actividades);
        lv.setAdapter(ada);
        registerForContextMenu(lv);
        cargarActividades();

        //Para saber en que orientación estamos
        final FragmentoDetalle fdetalle = (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.FDetalle);
        final boolean horizontal = fdetalle != null && fdetalle.isInLayout();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (horizontal){
                    fdetalle.setActividad(getApplicationContext(),actividades,i);
                }else {
                    Intent intent = new Intent(Principal.this, Secundaria.class);
                    intent.putExtra("actividades",actividades);
                    intent.putExtra("posicion",i);
                    startActivityForResult(intent, ACTIVIDADDOS);
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.accion_anadir) {
            Intent i = new Intent(this,AnadirActividad.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        if (id == R.id.action_borrar) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(Principal.this);
            dialogo.setTitle("Borrar");
            dialogo.setMessage("¿Desea borrar la actividad?");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    String peticion = "actividad/"+actividades.get(index).getId();
                    actividades.remove(index);
                    ada.notifyDataSetChanged();
                    DeleteActividad del = new DeleteActividad();
                    del.execute(peticion);
                }
            });
            dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    dialogo.cancel();
                }
            });
            dialogo.show();
        }
        return super.onContextItemSelected(item);
    }

    /* Cargar actividades */
    private void cargarActividades(){
        String peticiones = "actividad/jorge";
        GetRESTFul get = new GetRESTFul();
        get.execute(peticiones);
    }

    private class GetRESTFul extends AsyncTask<String, Void, String[]> {

        //Llegan varias urls de consultas, y devuelve un array de respuestas.
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
                    String idProfesor = objeto.getString("idprofesor");
                    String tipo = objeto.getString("tipo");
                    String fechaI = objeto.getString("fechai");
                    String fechaF = objeto.getString("fechaf");
                    String lugarI = objeto.getString("lugari");
                    String lugarF = objeto.getString("lugarf");
                    String descripcion = objeto.getString("descripcion");
                    Actividad a = new Actividad(id,idProfesor,tipo,fechaI,fechaF,lugarI,lugarF,descripcion,"jorge");
                    actividades.add(a);
                }
                ada.notifyDataSetChanged();
            } catch (JSONException e) {
            }
        }
    }

    /* Eliminar actividades */
    private class DeleteActividad extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... s) {
            String r = ClienteRestFul.delete(URLBASE + s[0]);
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject objeto = new JSONObject(s);
                if(objeto.getString("r").equals("1")){
                    Toast.makeText(Principal.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Principal.this, "No se ha podido borrar", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
