package com.example.pap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bienvenido extends AppCompatActivity {

    private Button next;
    private TextView emailtv, nombretv, usuariotv,procesotv,contratv;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);


        emailtv=findViewById(R.id.correoRegis);
        nombretv=findViewById(R.id.nameRegis);
        usuariotv=findViewById(R.id.userRegis);
        procesotv=findViewById(R.id.edadRegis);
        contratv=findViewById(R.id.contraRegis);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();


        next=findViewById(R.id.btnBienvenido);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bienvenido.this,Body.class);
                startActivity(intent);
                finish();
            }
        });

        obtenerInfo();
    }


    private void obtenerInfo(){

        String id= mAuth.getCurrentUser().getUid();
        mDatabase.child("user").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){
                String email = dataSnapshot.child("email").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String pass = dataSnapshot.child("pass").getValue().toString();
                String proceso = dataSnapshot.child("proceso").getValue().toString();
                String user = dataSnapshot.child("user").getValue().toString();


                emailtv.setText(email);
                nombretv.setText(name);
                usuariotv.setText(user);
                procesotv.setText(proceso);
                contratv.setText(pass);

            }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
