package com.example.jorge.gestionactividades;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Jorge on 15/02/2015.
 */
public class ClienteRestFul {

    public static String delete(String url){
        HttpClient clienteHttp = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(url);
        delete.setHeader("content-type", "application/json");
        try {
            HttpResponse respuestaHttp = clienteHttp.execute(delete);
            String respuesta = EntityUtils.toString(respuestaHttp.getEntity());
            return respuesta;
        }catch (Exception e){
            return e.toString();
        }
    }

    public static String get(String url){
        HttpClient clienteHttp = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.setHeader("content-type", "application/json");
        try{
            HttpResponse respuestaHttp = clienteHttp.execute(get);
            String respuesta = EntityUtils.toString(respuestaHttp.getEntity());
            return respuesta;
        }catch (Exception e){
            return e.toString();
        }
    }

    public static String post(String url,JSONObject objectoJSON){
        HttpClient clienteHttp = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("content-type", "application/json");
        try{
            StringEntity entidad = new StringEntity(objectoJSON.toString());
            post.setEntity(entidad);
            HttpResponse respuestaHttp = clienteHttp.execute(post);
            String respuesta = EntityUtils.toString(respuestaHttp.getEntity());
            return respuesta;
        }catch (Exception e){
            return e.toString();
        }
    }
}
