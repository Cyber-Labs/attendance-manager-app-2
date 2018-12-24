package com.example.tidu.attendancemanager2;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editmin,editabs,editpre ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    private DrawerLayout DrawerLayout1;
    Button btnviewUpdate;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_act);
        setSupportActionBar(mToolbar);
        DrawerLayout1 = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this,DrawerLayout1,R.string.open,R.string.close);
        DrawerLayout1.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        DrawerLayout1.closeDrawers();

                        if(menuItem.getTitle().equals("Add Subject"))   //when "Add Subject" item is selected
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_add,null);

                            final EditText sub = (EditText)view.findViewById(R.id.subjectAdd);
                            final EditText min =  (EditText)view.findViewById(R.id.minAdd);
                            Button add_Btn =  (Button)view.findViewById(R.id.AddBtn);
                            Button cancel_Btna = (Button)view.findViewById(R.id.CancelBtna);
                            final String subject = sub.getText().toString();//stores the value of subject typed
                            final String minimum = min.getText().toString();//stores the minimum attendence
                            builder.setView(view);
                            final AlertDialog add_dialog = builder.create();
                            add_dialog.show(); // add dialogbox is shown

                            add_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {                      //intructions to be performed if add button is clicked

                                    boolean isinserted=myDb.insertData(subject,minimum,"0","0","0");//inserts data
                                    if(isinserted)
                                        Toast.makeText(MainActivity.this,"Data added",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MainActivity.this,"Some Error Occured",Toast.LENGTH_SHORT).show();

                                    add_dialog.dismiss();
                                }
                            });

                            cancel_Btna.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {    ////intructions to be performed if cancel button is clicked
                                    add_dialog.dismiss();
                                }
                            });




                        }
                        else if (menuItem.getTitle().equals("Delete Subject"))     //when "Delete Subject" is selected
                        {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);

                            LayoutInflater inflater2 = MainActivity.this.getLayoutInflater();
                            View view2 = inflater2.inflate(R.layout.dialog_delete,null);

                            final EditText deleteSub = (EditText)view2.findViewById(R.id.deleteSubject);

                            Button delete_Btn =  (Button)view2.findViewById(R.id.Deletebtn);
                            Button cancel_Btnd = (Button)view2.findViewById(R.id.Cancelbtnd);
                            final String subject = deleteSub.getText().toString();  //stores the value of subject enter for deletion

                            builder2.setView(view2);
                            final AlertDialog add_dialog2 = builder2.create();
                            add_dialog2.show();     //delete dialogbox is shown

                            delete_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {   ////intruction to be performed if delete button is clicked
                                    Integer deletedRows = myDb.deleteData(subject);
                                    if(deletedRows > 0)
                                        Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();


                                    add_dialog2.dismiss();
                                }
                            });

                            cancel_Btnd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { ////intruction to be performed if cancel button is clicked
                                    add_dialog2.dismiss();
                                }
                            });

                        }


                        return true;
                    }
                });


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

@Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }return super.onOptionsItemSelected(item);
}

}
