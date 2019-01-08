package com.example.tidu.attendancemanager2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.tidu.attendancemanager2.R.layout.subject_details;

public class detailsSubject extends AppCompatActivity{
    DatabaseHelper mydb;
    String SName,SMin,SPres,SAbs,SID,STot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(subject_details);
        getIncomingIntent();
        mydb = new DatabaseHelper(this);
        Cursor res =mydb.getAllData();
        while(res.moveToNext()) {
            int id = res.getInt(0);
            if(id == Integer.parseInt(SID));
            {
                String name = res.getString(1);
                String min = res.getString(2);
                String pres = res.getString(3);
                String abs = res.getString(4);
                String tot = String.valueOf(Integer.parseInt(pres) + Integer.parseInt(abs));
                SetText(name, min, pres, abs, tot, SID);
                break;
            }
        }
        Button E =  (Button) findViewById(R.id.edit);
        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent EDIT = new Intent(detailsSubject.this,EditData.class);
                EDIT.putExtra("Id",SID);
                EDIT.putExtra("name",SName);
                EDIT.putExtra("Min",SMin);
                EDIT.putExtra("P",SPres);
                EDIT.putExtra("A",SAbs);
                EDIT.putExtra("ID",SID);
                startActivity(EDIT);
            }

        }); }
    private void getIncomingIntent() {
        if (getIntent().hasExtra("IName") && getIntent().hasExtra("IMin") && getIntent().hasExtra("Ipres") && getIntent().hasExtra("ITot")) {
            SName = getIntent().getStringExtra("IName");
            SMin = getIntent().getStringExtra("IMin");
            SPres = getIntent().getStringExtra("Ipres");
            STot= getIntent().getStringExtra("ITot");
            SID=getIntent().getStringExtra("Id");
            SAbs= String.valueOf(Integer.parseInt(STot) - Integer.parseInt(SPres));

        }
    }
    private void SetText(String SName, String SMin, String SPres, String SAbs, String STot,String SID) {
        TextView N= findViewById(R.id.sub);
        N.setText(SName);
        TextView M=findViewById(R.id.min);
        M.setText(SMin);
        TextView P= findViewById(R.id.pres);
        P.setText(SPres);
        TextView T=findViewById(R.id.T);
        T.setText(STot);
        TextView A= findViewById(R.id.ab);
        A.setText(SAbs);
        TextView I=findViewById(R.id.id1);
        I.setText(SID);

    }

}

