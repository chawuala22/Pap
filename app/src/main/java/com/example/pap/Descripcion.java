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

import java.util.HashMap;
import java.util.Map;

public class Descripcion extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText resena;
    private Button logout, send;
    private TextView dfecha;
    String nombre, fecha1,descargo,circuito;

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
        dfecha=findViewById(R.id.dfechas);
        obtenerInfo();
        loadbody();





    }

    public void send(View view) {

        String reseñaa = resena.getText().toString();
        Map<String,Object> datosedittext1= new HashMap<>();
        datosedittext1.put("reseña",reseñaa);
        mDatabase.child("circuito").push().setValue(datosedittext1);


        String[] to = { correo1, corre2 };
        Send(to, "INVENTARIO MATERIAL POSTE A POSTE OBRAS EN MANTENIMIENTO POR "+nombre,
                "FECHA: "+dfecha+"\n"+"DESCARGO O INCIDENCIA: "+descargo+"\n"+"CIRCUITO O LINEA: "+circuito+"\n\n");

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



    private void loadbody(){

        mDatabase.child("circuito").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    infopojo user = dataSnapshot.getValue(infopojo.class);
                    fecha1=user.getFecha();
                      Log.e("fecha:", "" + fecha1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void Send(String[] to, String asunto, String mensaje){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        //String[] to = direccionesEmail;
        //String[] cc = copias;
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

}



