package com.example.pap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Descripcion extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText resena;
    private Button logout, send;
    String nombre, fecha1,descargo,circuito, sptype, h11;

    private ArrayList<String> cantidades = new ArrayList<String>();
    private ArrayList<String> postes = new ArrayList<String>();
    private ArrayList<String> tipomaterial = new ArrayList<String>();
    private ArrayList<String> spunidad = new ArrayList<String>();

    String correo1 = "antoniovb22@gmail.com";
    String corre2 ="soymigue15@gmail.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        resena=findViewById(R.id.txtDescripcion);
        send=findViewById(R.id.btnEnviar);
        logout=findViewById(R.id.btnCerrar);

        obtenerInfo();
        loadtable();

//Explicar ahora
        fecha1=getIntent().getStringExtra("fecha");
        descargo=getIntent().getStringExtra("descargo");
        circuito=getIntent().getStringExtra("circuito");
        sptype=getIntent().getStringExtra("seleccion");
        h11=getIntent().getStringExtra("material");



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Descripcion.this,MainActivity.class ));
            }
        });


    }

    public void send(View view) {

        String resena2=resena.getText().toString();

        int size=postes.size();
        String c1="";
        String p1="";
        String u1="";
        String m1="";
        String w="";

        for(int i=0;i<size;i++ ){

            c1+= cantidades.get(i)+"  ";
            w="\t";
            p1+=postes.get(i)+"  ";
            u1+=spunidad.get(i)+"  ";
            m1+=tipomaterial.get(i)+"  ";

        }

                String[] to = { correo1, corre2 };
        Send(to, "INVENTARIO MATERIAL POSTE A POSTE OBRAS EN MANTENIMIENTO POR "+nombre,

                "FECHA: "+fecha1+"\n"+
                         "DESCARGO O INCIDENCIA: "+descargo+"\n" +
                         "CIRCUITO O LINEA: "+circuito+"\n"
                        +"TIPO DE MATERIAL: "+sptype+"\n\n"+
                        "CODIGO | MATERIAL: \t"+ m1 +"\n" +"UNIDAD: \t"+u1 +"\n"+ "POSTE: \t"+p1 +"\n"+ "CANTIDAD: \t"+c1 +"\n"+
                "RESEÑA: \n"+resena2);

    }


    private void obtenerInfo(){
        String id= mAuth.getCurrentUser().getUid();
            mDatabase.child("user").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    nombre = dataSnapshot.child("name").getValue().toString();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void Send(String[] to, String asunto, String mensaje){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");

        try{
            startActivity(emailIntent);
            finish();
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(Descripcion.this,"No Email",Toast.LENGTH_SHORT);
        }

    }


    private void loadtable(){
        mDatabase.child("circuito").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){

                     cantidades.add(dataSnapshot.child(snapshot.getKey()).child("cantidad").getValue().toString());
                     postes.add (dataSnapshot.child(snapshot.getKey()).child("poste").getValue().toString());
                     spunidad.add (dataSnapshot.child(snapshot.getKey()).child("unidad").getValue().toString());
                     tipomaterial.add (dataSnapshot.child(snapshot.getKey()).child("material").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}



