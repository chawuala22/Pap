package com.example.pap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Body extends AppCompatActivity {

    private EditText bCircuito, bDescargo, bDate, bPoste, bCantidad ;
    private int bYearini,bMonthini,bDayini, sYearini, sMonthini, sDayini;
    private Button nextdes;

    static final int DATE_ID=0;
    Calendar calendar = Calendar.getInstance();

    private TableLayout tableLayout;
    private Spinner spDescrip, spUnd, spType;

    private String[] header={"CODIGO|MATERIAL","UNIDAD","POSTE","CANTIDAD"};
    private ArrayList<String[]> rows=new ArrayList<>();
    private TableDynamicMR tableDynamicMR;
    private String unidad, descripcion,fechita,mspSelecionada ="",mspUndselect="",mspMaterial="";

    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        tableLayout=(TableLayout)findViewById(R.id.table);
        bDate=findViewById(R.id.date);
        sMonthini = calendar.get(Calendar.MONTH);
        sDayini = calendar.get(Calendar.DAY_OF_MONTH);
        sYearini= calendar.get(Calendar.YEAR);

        bCircuito=findViewById(R.id.circuito);
        bDescargo=findViewById(R.id.descargo);
        bCantidad=findViewById(R.id.cantidad);
        bPoste=findViewById(R.id.poste);
        spType=findViewById(R.id.typematerial);
        spDescrip=findViewById(R.id.spinnerdescrip);
        spUnd=findViewById(R.id.spinner);
        spType=findViewById(R.id.typematerial);
        nextdes=findViewById(R.id.next2);



        mDatabase= FirebaseDatabase.getInstance().getReference();
        loadDescription();
        loadUnd();
        loadtype();

        tableDynamicMR = new TableDynamicMR(tableLayout,getApplicationContext());
        tableDynamicMR.addHeader(header);
        tableDynamicMR.addData(getClients());

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });


     nextdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Body.this,Descripcion.class);
                startActivity(intent);
                finish();
            }
    });

    }



    public void save(View view){
        String[]item=new String[]{mspMaterial,mspUndselect,bPoste.getText().toString(),bCantidad.getText().toString()};
        tableDynamicMR.addItems(item);


        String circuito = bCircuito.getText().toString();
        fechita= bDate.getText().toString();
        String poste=bPoste.getText().toString();
        String descargo= bDescargo.getText().toString();
        int cantidad= Integer.parseInt(bCantidad.getText().toString());

        Map<String,Object> datosedittext = new HashMap<>();
        datosedittext.put("circuitos",circuito);
        datosedittext.put("fecha",fechita);
        datosedittext.put("poste",poste);
        datosedittext.put("descargo",descargo);
        datosedittext.put("cantidad",cantidad);
        mDatabase.child("circuito").push().setValue(datosedittext);

    }

    private ArrayList<String[]> getClients(){
        rows.add(new String[]{"","","",""});
        return rows;
    }


    //-------------------------------- PARA EL CALENDARIO

    private void colocar_fecha(){

        bDate.setText((bMonthini + 1)+ "-"+ bDayini + "-"+bYearini+ " ");
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            bYearini = year;
            bMonthini=month;
            bDayini=dayOfMonth;
            colocar_fecha();
        }
    };

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this,mDateSetListener,sYearini,sMonthini,sDayini);
        }
        return null;
    }


    //-----------------------------------------------------------------------

    public void loadtype(){


        final List<TypeM> typeMS = new ArrayList<>();
        mDatabase.child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String nombreType = ds.child("nombre").getValue().toString();

                        typeMS.add(new TypeM(nombreType));
                    }

                    ArrayAdapter<TypeM> arrayAdapter = new ArrayAdapter<>(Body.this,android.R.layout.simple_dropdown_item_1line,typeMS);
                    spType.setAdapter(arrayAdapter);
                    spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mspSelecionada=  parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    public void loadUnd(){


        final List<Und> unds = new ArrayList<>();
        mDatabase.child("undss").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        unidad = ds.child("nombre").getValue().toString();

                        unds.add(new Und(unidad));
                    }

                    ArrayAdapter<Und> arrayAdapter = new ArrayAdapter<>(Body.this,android.R.layout.simple_dropdown_item_1line,unds);
                    spUnd.setAdapter(arrayAdapter);
                    spUnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            mspUndselect=parent.getItemAtPosition(position).toString();



                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public  void loadDescription(){

        final List<Description> descriptions = new ArrayList<>();
        mDatabase.child("descripcion").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        descripcion = ds.child("nombre").getValue().toString();

                        descriptions.add(new Description(descripcion));
                    }

                  ArrayAdapter<Description> arrayAdapter = new ArrayAdapter<>(Body.this,android.R.layout.simple_dropdown_item_1line,descriptions);
                    spDescrip.setAdapter(arrayAdapter);
                    spDescrip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mspMaterial = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
