package com.example.pap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TableLayout;

import java.util.ArrayList;

public class Body extends AppCompatActivity {


    private TableLayout tableLayout;
    private EditText txtcodigo;
    private EditText txtelemnto;
    private String[] header={"Codigo","Elemento","Unidad","Poste","Cantidad"};
    private ArrayList<String[]> rows=new ArrayList<>();
   // private TableDynamic tableDynamic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

       // tableLayout=findViewById(R.id.table);
        txtcodigo=findViewById(R.id.codigo);
        txtelemnto=findViewById(R.id.elemento);


       // tableDynamic = new TableDynamic(tableLayout,getApplicationContext());
        //tableDynamic.addHeader(header);
        //tableDynamic.addData(getClients());

    }

   /* public void save(View view){
        String[]item=new String[]{txtcodigo.getText().toString(),txtelemnto.getText().toString()};
        tableDynamic.addItems(item);
    }
    private ArrayList<String[]> getClients(){
    rows.add(new String[]{"1","Pedro"});
    return rows;
    }*/

}
