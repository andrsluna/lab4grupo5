package com.example.amst_lab4_grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void abrirMetodoPOST(View view){
        Intent intent = new Intent(this,MetodoPost.class);
        startActivity(intent);
    }

    public void abrirMetodoDELETE(View view){
        Intent intent = new Intent(this,MetodoDelete.class);
        startActivity(intent);
    }

    public void abrirVistaRegistros(View view) {
        Intent intent = new Intent(this, Registros.class);
        startActivity(intent);
    }
}