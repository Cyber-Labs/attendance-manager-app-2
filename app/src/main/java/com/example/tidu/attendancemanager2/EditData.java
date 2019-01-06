package com.example.tidu.attendancemanager2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.tidu.attendancemanager2.R.layout.subject_details;

public class EditData extends AppCompatActivity {

    private Button btnS, btnC;
    private EditText E1,E2,E3,E4;
    DatabaseHelper mDb;
    private String N,M,P,A,ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_edit);
        btnS=(Button)findViewById(R.id.save);
        btnC=(Button)findViewById(R.id.cancel);
        E1=(EditText)findViewById(R.id.sub1);
        E2=(EditText)findViewById(R.id.min1);
        E3=(EditText)findViewById(R.id.pres1);
        E4=(EditText)findViewById(R.id.ab1);
        mDb=new DatabaseHelper(this);
        Intent receivintent=getIntent();
        N=receivintent.getStringExtra("name");
        M=receivintent.getStringExtra("Min");
        P=receivintent.getStringExtra("P");
        A=receivintent.getStringExtra("A");
        ID=receivintent.getStringExtra("ID");


        E1.setText(N);
        E2.setText(M);
        E3.setText(P);
        E4.setText(A);
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item1=E1.getText().toString();
                String item2=E2.getText().toString();
                String item3=E3.getText().toString();
                String item4=E4.getText().toString();


                if(!item2.equals("") && !item1.equals("") && !item3.equals("") && !item4.equals("")) {
                    mDb.updateData(ID,item1,item2,item3,item4);
                    Toast.makeText(EditData.this,"Data saved.",Toast.LENGTH_SHORT).show();

                    finish();
                }
                else{
                    Toast.makeText(EditData.this, "Field cannot be left empty.", Toast.LENGTH_SHORT).show();
                }

                }
            });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }
}