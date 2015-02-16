package com.example.jorge.gestionactividades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Jorge on 15/02/2015.
 */
public class AdaptadorActividad extends ArrayAdapter<Actividad> {
    private Context contexto;
    private ArrayList<Actividad> lista;
    private int recurso;
    public static LayoutInflater i;

    public AdaptadorActividad(Context context, int resource, ArrayList<Actividad> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("LOG", "" + lista.size());
        viewHolder vh = null;
        if (convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new viewHolder();
            vh.tv1 = (TextView) convertView.findViewById(R.id.tvDetalle);
            vh.tv2 = (TextView) convertView.findViewById(R.id.tvLugar);
            vh.tv3 = (TextView) convertView.findViewById(R.id.tvFecha);
            vh.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(vh);;
        }else {
            vh = (viewHolder) convertView.getTag();
        }
        vh.posicion = position;
        vh.tv1.setText(lista.get(position).getDescripcion());
        vh.tv2.setText(lista.get(position).getLugarI());
        vh.tv3.setText(lista.get(position).getFechaI());
        vh.iv.setTag(position);
        return convertView;
    }

    static class viewHolder{
        public ImageView iv;
        public TextView tv1,tv2,tv3;
        public int posicion;
    }
}
