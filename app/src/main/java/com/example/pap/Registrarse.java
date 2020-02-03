package com.example.pap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registrarse extends AppCompatActivity {

    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        registrar=findViewById(R.id.btnRegis1);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrar =new Intent(Registrarse.this, MainActivity.class);
                startActivity(registrar);
                finish();
            }
        });


    }
}
