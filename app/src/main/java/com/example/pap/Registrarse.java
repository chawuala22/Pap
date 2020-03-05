package com.example.pap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Registrarse extends AppCompatActivity {

    Button registrar,devolver;
    EditText editTextUser,edipass,edimail,ediname,ediprocess;


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
       // -----------------------------------------------------------//

        ediname=findViewById(R.id.nameRegis);
        editTextUser=findViewById(R.id.userRegis);
        edipass=findViewById(R.id.contraRegis);
        edimail= findViewById(R.id.correoRegis);
        ediprocess=findViewById(R.id.procesoRegis);
        registrar=findViewById(R.id.btnRegis1);
        devolver=findViewById(R.id.exitaccount);
//----------------------------------------//

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CREAMOS VARIABLES PARA GUARDAR LA INFO INGRESADA

                final String name = ediname.getText().toString().trim();
                final String user = editTextUser.getText().toString().trim();
                final String password = edipass.getText().toString().trim();
                final String email = edimail.getText().toString().trim();
                final String processo = ediprocess.getText().toString().trim();


                //VALIDAMOS QUE EL USUARIO COMPLETE TODOS LOS CAMPOS


                if(!name.isEmpty() && !user.isEmpty() && !processo.isEmpty()){

                    Toast.makeText(Registrarse.this, "Go!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Registrarse.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }

                //--------------------------------/

                if (TextUtils.isEmpty(email)) {
                    edimail.setError("Email requerido");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edipass.setError("Password requerida");
                    return;
                }

                if (password.length() < 6) {
                    edipass.setError("La contraseña debe ser mayor a 6 caracteres");
                }


                //REGISTRAR USUARIO

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Registrarse.this, "Usuario Creado", Toast.LENGTH_SHORT).show();

                            String id = mAuth.getCurrentUser().getUid();
                            Map<String, Object> map  = new HashMap<>();
                            map.put("name",name);
                            map.put("user",user);
                            map.put("pass",password);
                            map.put("email",email);
                            map.put("proceso",processo);

                            mDatabase.child("user").child(id).setValue(map);

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Registrarse.this, "Negativo el civil", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }
        });


        devolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrarse.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }




}
