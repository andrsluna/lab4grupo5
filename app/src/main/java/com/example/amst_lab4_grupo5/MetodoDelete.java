package com.example.amst_lab4_grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetodoDelete extends AppCompatActivity {
    Button btnDelete;
    Spinner spn_temps;
    String delete="";
    int posicion;
    private RequestQueue request = null;
    List<String> listaTemperaturas;
    List<String> listaID;
    List<String> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_delete);
        request = Volley.newRequestQueue(this);
        btnDelete = (Button) findViewById(R.id.buttonDELETE);
        spn_temps = (Spinner) findViewById(R.id.spn_temperaturas);
        listaTemperaturas = new ArrayList<String>();
        listaID = new ArrayList<String>();
        obtenerTemperaturas();
        System.out.println("este es el delete "+delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(delete);
                String url_borrar = "https://amstdb-lab.herokuapp.com/api/logTres/"+delete;
                JsonObjectRequest requestDelete = new JsonObjectRequest(Request.Method.DELETE,url_borrar,null,
                        response ->{
                            Toast toast = Toast.makeText(getApplicationContext(),"Se ha realizado el DELETE con exito",Toast.LENGTH_SHORT);
                            toast.show();
                            System.out.println("Este es el response: "+ response);

                        }, error -> System.out.println(error)
                );
                request.add(requestDelete);
            }
        });
    }
    public void obtenerTemperaturas(){
        String url_registros = "https://amstdb-lab.herokuapp.com/db/logTres";
        ArrayList<String> registros = new ArrayList<String>();
        JsonArrayRequest requestRegistros = new JsonArrayRequest(
                Request.Method.GET, url_registros, null,
                response -> {
                    try{
                        JSONObject registroTemp;
                        for (int i=0;i<response.length();i++){
                            registroTemp =response.getJSONObject(i);
                            String id = registroTemp.getString("id");
                            String temperatura = registroTemp.getString("value");
                            listaTemperaturas.add(temperatura);
                            listaID.add(id);

                        }
                        String iDelete=null;
                        ArrayAdapter<String> adapter_temp =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listaTemperaturas);
                        adapter_temp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spn_temps.setAdapter(adapter_temp);
                        spn_temps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String idDelete = listaID.get(position);
                                delete =listaID.get(position);
                                System.out.println(idDelete);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        System.out.println(listaTemperaturas);
                        System.out.println(listaID);


                    }catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("error");
                    }

                }, error -> System.out.println(error)
        );
        request.add(requestRegistros);

    }
    public void volverMenuPrincipal(View view){
        finish();
        Intent intent = new Intent(MetodoDelete.this,MainActivity.class);
        startActivity(intent);
    }

}