package com.example.pap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    TextView registro;
    EditText edimail, edipass,edinmae;
    FirebaseAuth mAuth;
    ImageButton siguiente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        edimail=findViewById(R.id.user);
        edinmae=findViewById(R.id.nameRegis);
        edipass=findViewById(R.id.pass);
        siguiente=findViewById(R.id.imageButton);
        registro=findViewById(R.id.textView3);



        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = edipass.getText().toString().trim();
                final String email = edimail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edimail.setError("Email requerido");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edipass.setError("Contraseña requerida");
                    return;
                }

                if (password.length() < 6) {
                    edipass.setError("La contraseña debe ser mayor a 6 caracteres");
                }


                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Bienvenido "+ email , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Bienvenido.class));
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, "Usuario y/o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }
        });







        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this, Registrarse.class);
                startActivity(registro);
                finish();
            }
        });



        }



}
