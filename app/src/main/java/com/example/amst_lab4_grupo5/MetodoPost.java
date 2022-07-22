package com.example.amst_lab4_grupo5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;

public class MetodoPost extends AppCompatActivity {
    EditText temperatura;
    Button btnPOST;
    private RequestQueue request = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_post);
        request = Volley.newRequestQueue(this);
        temperatura = (EditText) findViewById(R.id.txt_tempPOST);

        btnPOST = (Button) findViewById(R.id.buttonPOST);
        btnPOST.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                agregarTemperatura();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void agregarTemperatura(){
        String temp =temperatura.getText().toString();
        Double tempNum = Double.parseDouble(temp);
        String urlPOST = "https://amstdb-lab.herokuapp.com/db/logTres";
        HashMap<String,Object> parametros = new HashMap<>();
        LocalDate fechaActual = LocalDate.now();
        parametros.put("date_created",fechaActual.toString());
        parametros.put("key","temperatura");
        parametros.put("value",tempNum);
        JSONObject params = new JSONObject(parametros);
        JsonObjectRequest requestPOST =new JsonObjectRequest(Request.Method.POST,urlPOST,params,
                response ->{
                    Toast toast = Toast.makeText(getApplicationContext(),"Se ha realizado el POST con exito",Toast.LENGTH_SHORT);
                    toast.show();
                    System.out.println(response);
                },error -> System.err.println(error)
        );
        request.add(requestPOST);
        temperatura.setText("");
    }
    public void volverMenuPrincipal(View view){
        finish();
        Intent intent = new Intent(MetodoPost.this,MainActivity.class);
        startActivity(intent);
    }
}