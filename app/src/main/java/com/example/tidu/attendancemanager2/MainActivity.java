package com.example.tidu.attendancemanager2;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editmin,editabs,editpre ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;

    Button btnviewUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editmin = (EditText)findViewById(R.id.editText_minimum);
        editpre = (EditText)findViewById(R.id.editText_pre);
        editTextId = (EditText)findViewById(R.id.editText_id);
        editabs = (EditText)findViewById(R.id.editText_abs);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate= (Button)findViewById(R.id.button_update);
        btnDelete= (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double presents = Double.parseDouble(editpre.getText().toString());
                        double absents = Double.parseDouble(editabs.getText().toString());
                        double ans;
                        if(presents==0 && absents==0)
                        {
                            Toast.makeText(MainActivity.this,"plz fill the form ",Toast.LENGTH_LONG).show();
                        }
                        else {
                            ans = presents / (presents + absents);
                            String a = Double.toString(ans * 100);
                            boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                    editName.getText().toString(),
                                    editmin.getText().toString(), editpre.getText().toString(), editabs.getText().toString(), a);
                            if (isUpdate == true)
                                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double presents = Double.parseDouble(editpre.getText().toString());
                        double absents = Double.parseDouble(editabs.getText().toString());
                         double ans;
                        if(presents == 0 && absents==0)
                        {
                            Toast.makeText(MainActivity.this,"plz fill the form ",Toast.LENGTH_LONG).show();
                        }
                        else {
                            ans = presents / (presents + absents);


                            String a = Double.toString(ans * 100);
                            boolean isInserted = myDb.insertData(editName.getText().toString(),
                                    editmin.getText().toString(),
                                    editpre.getText().toString(), editabs.getText().toString(), a);
                            if (isInserted == true)
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double presents = Double.parseDouble(editpre.getText().toString());
                        double absents = Double.parseDouble(editabs.getText().toString());
                        double ans ;
                        if(presents==0 && absents==0) //for eliminating 0/0 error
                        {
                            Toast.makeText(MainActivity.this,"plz fill the form ",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            ans = presents / (presents + absents);
                            String a= Double.toString(ans*100);
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("minimum % :"+ res.getString(2)+"\n");
                            buffer.append("presents :"+ res.getString(3)+"\n");
                            buffer.append("absents :"+ res.getString(4)+"\n");
                            buffer.append("current % :"+ a+"\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());}
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



}
