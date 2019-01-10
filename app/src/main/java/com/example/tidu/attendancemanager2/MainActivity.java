package com.example.tidu.attendancemanager2;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import android.widget.ListAdapter;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    Cursor c;
   // EditText editName,editmin,editabs,editpre ,editTextId;
   // Button btnAddData;
   //Button btnviewAll;
   // Button btnDelete;
    private DrawerLayout DrawerLayout1;
   // Button btnviewUpdate;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar mToolbar;
    private List<SubjectInfo> itemList;
    private LinearLayoutManager mlayoutManager;
    private listAdapter mlistAd;
    private com.github.clans.fab.FloatingActionButton Fab_add;
    private com.github.clans.fab.FloatingActionButton Fab_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_act);
        setSupportActionBar(mToolbar);
        DrawerLayout1 = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fab_add = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.add_subject);
        Fab_delete = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.delete_subject);

        mToggle=new ActionBarDrawerToggle(this,DrawerLayout1,R.string.open,R.string.close);
        DrawerLayout1.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecyclerView subList = (RecyclerView) findViewById(R.id.subList);
        itemList = new ArrayList<>();


        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getAllData();
        while(res.moveToNext())
        {
            itemList.add(new SubjectInfo(res.getInt(0),res.getString(1),res.getString(2),
                    res.getString(3),res.getString(4),res.getString(5)));
        }
        mlistAd = new listAdapter(this, itemList, new onClickListnerPlusMinus() {
            @Override
            public void onClickedMinus(int position,String sub,int a) {
                String ab = Integer.toString(a);
                myDb.updateabs(sub,ab);
            }

            @Override
            public void onClickedPlus(int position,String sub,int p) {
                String pr = Integer.toString(p);
                myDb.updatepres(sub,pr);
            }
        });
        mlayoutManager = new LinearLayoutManager(this);
        subList.setAdapter(mlistAd);
        subList.setLayoutManager(mlayoutManager);



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        DrawerLayout1.closeDrawers();
                        if(menuItem.getTitle().equals("Delete All")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_delete_all,null);
                            builder.setView(view);
                            final AlertDialog delete_all_dialog = builder.create();
                            delete_all_dialog.show();
                            Button Yes =  (Button)view.findViewById(R.id.YesBtn);
                            Button No = (Button)view.findViewById(R.id.NoBtn);
                            Yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    boolean isDeleted=myDb.alldelete();
                                    if(isDeleted)
                                        Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MainActivity.this,"Some Error Occured",Toast.LENGTH_SHORT).show();
                                    itemList = new ArrayList<>();
                                    Cursor res = myDb.getAllData();
                                    while(res.moveToNext())
                                    {
                                        itemList.add(new SubjectInfo(res.getInt(0),res.getString(1),res.getString(2),
                                                res.getString(3),res.getString(4),res.getString(5)));
                                    }
                                    mlistAd = new listAdapter(MainActivity.this, itemList, new onClickListnerPlusMinus() {
                                        @Override
                                        public void onClickedMinus(int position,String sub, int a) {
                                            String ab = Integer.toString(a);
                                            myDb.updateabs(sub,ab);
                                        }

                                        @Override
                                        public void onClickedPlus(int position,String sub, int p) {
                                            String pr = Integer.toString(p);
                                            myDb.updatepres(sub,pr);
                                        }
                                    });
                                    subList.setAdapter(mlistAd);

                                    delete_all_dialog.dismiss();
                                }
                            });
                            No.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delete_all_dialog.dismiss();
                                }
                            });
                            return true;

                        }
                        else if(menuItem.getTitle().equals("Edit User Name")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_edit,null);
                            builder.setView(view);
                            final AlertDialog edit_dialog = builder.create();
                            edit_dialog.show();
                            final Button Save =  (Button)view.findViewById(R.id.S);
                            Button Cancel = (Button)view.findViewById(R.id.C);
                            final EditText name = (EditText)view.findViewById(R.id.U);
                            Save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String NU = name.getText().toString();
                                    TextView UName=(TextView)findViewById(R.id.user);
                                    UName.setText(NU);
                                    edit_dialog.dismiss();

                                }
                            });
                            Cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    edit_dialog.dismiss();
                                }
                            });
                        }
                        else if(menuItem.getTitle().equals("Report Bug")){
                            String email="iamme.medha@gmail.com";
                            String email1="ayush.singh.222k@gmail.com";
                            String email2="uditdasari19@gmail.com";
                            Intent intent=new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"+email+","+email1+","+email2));
                            intent.putExtra(Intent.EXTRA_SUBJECT,"Reporting Bugs");
                            if(intent.resolveActivity(getPackageManager())!=null){
                                startActivity(intent);

                            }

                        }
                        else if (menuItem.getTitle().equals("Contact Us")){
                            Intent Contact=new Intent(MainActivity.this,contact.class);
                            startActivity(Contact);
                        }
                       /* if(menuItem.getTitle().equals("Add Subject"))   //when "Add Subject" item is selected
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_add,null);
                            builder.setView(view);
                            final AlertDialog add_dialog = builder.create();
                            add_dialog.show(); // add dialogbox is shown

                            final EditText sub = (EditText)view.findViewById(R.id.subjectAdd);
                            final EditText min =  (EditText)view.findViewById(R.id.minAdd);
                            Button add_Btn =  (Button)view.findViewById(R.id.AddBtn);
                            Button cancel_Btna = (Button)view.findViewById(R.id.CancelBtna);

                            add_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {                      //intructions to be performed if add button is clicked
                                    String subject = sub.getText().toString().trim();//stores the value of subject typed
                                    String minimum = min.getText().toString().trim();//stores the minimum attendence
                                    if (subject.isEmpty() || minimum.isEmpty()) {
                                        Toast.makeText(MainActivity.this, "Subject Not added. Fill all the fields", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                    boolean isinserted = myDb.insertData(subject, minimum, "0", "0", "0");//inserts data
                                    if (isinserted)
                                        Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();

                                    itemList = new ArrayList<>();
                                    Cursor res = myDb.getAllData();
                                    while (res.moveToNext()) {
                                        itemList.add(new SubjectInfo(res.getInt(0), res.getString(1), res.getString(2),
                                                res.getString(3), res.getString(4), res.getString(5)));
                                    }
                                    mlistAd = new listAdapter(MainActivity.this, itemList, new onClickListnerPlusMinus() {
                                        @Override
                                        public void onClickedMinus(int position, String sub, int a) {
                                            String ab = Integer.toString(a);
                                            myDb.updateabs(sub, ab);
                                        }

                                        @Override
                                        public void onClickedPlus(int position, String sub, int p) {
                                            String pr = Integer.toString(p);
                                            myDb.updatepres(sub, pr);
                                        }
                                    });
                                    subList.setAdapter(mlistAd);

                                    add_dialog.dismiss();
                                }
                                }
                            });

                            cancel_Btna.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {////intructions to be performed if cancel button is clicked
                                    
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

                            builder2.setView(view2);
                            final AlertDialog add_dialog2 = builder2.create();
                            add_dialog2.show();     //delete dialogbox is shown

                            delete_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {   ////intruction to be performed if delete button is clicked
                                    String subject = deleteSub.getText().toString().trim();  //stores the value of subject enter for deletion
                                    if (subject.isEmpty()) {
                                        Toast.makeText(MainActivity.this, "Subject not deleted. Fill the subject", Toast.LENGTH_LONG).show();
                                    }
                                    else{

                                    Integer deletedRows = myDb.deleteData(subject);
                                    if (deletedRows > 0)
                                        Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                                    itemList = new ArrayList<>();
                                    Cursor res = myDb.getAllData();
                                    while (res.moveToNext()) {
                                        itemList.add(new SubjectInfo(res.getInt(0), res.getString(1), res.getString(2),
                                                res.getString(3), res.getString(4), res.getString(5)));
                                    }
                                    mlistAd = new listAdapter(MainActivity.this, itemList, new onClickListnerPlusMinus() {
                                        @Override
                                        public void onClickedMinus(int position, String sub, int a) {
                                            String ab = Integer.toString(a);
                                            myDb.updateabs(sub, ab);
                                        }

                                        @Override
                                        public void onClickedPlus(int position, String sub, int p) {
                                            String pr = Integer.toString(p);
                                            myDb.updatepres(sub, pr);
                                        }
                                    });
                                    subList.setAdapter(mlistAd);

                                    add_dialog2.dismiss();
                                }

                                }
                            });

                            cancel_Btnd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { ////intruction to be performed if cancel button is clicked

                                    add_dialog2.dismiss();
                                }
                            });

                        } */
                        return true;
                    }
                });



        Fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add,null);
                builder.setView(view);
                final AlertDialog add_dialog = builder.create();
                add_dialog.show(); // add dialogbox is shown

                final EditText sub = (EditText)view.findViewById(R.id.subjectAdd);
                final EditText min =  (EditText)view.findViewById(R.id.minAdd);
                Button add_Btn =  (Button)view.findViewById(R.id.AddBtn);
                Button cancel_Btna = (Button)view.findViewById(R.id.CancelBtna);

                add_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {                      //intructions to be performed if add button is clicked
                        String subject = sub.getText().toString().trim();//stores the value of subject typed
                        String minimum = min.getText().toString().trim();//stores the minimum attendence
                        if (subject.isEmpty() || minimum.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Subject Not added. Fill all the fields", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                        if ((subject.trim().length() != 0) && (minimum.trim().length() != 0)) {
                            boolean isinserted = myDb.insertData(subject, minimum, "0", "0", "0");//inserts data
                            if (isinserted)
                                Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();

                            itemList = new ArrayList<>();
                            Cursor res = myDb.getAllData();
                            while (res.moveToNext()) {
                                itemList.add(new SubjectInfo(res.getInt(0), res.getString(1), res.getString(2),
                                        res.getString(3), res.getString(4), res.getString(5)));
                            }
                            mlistAd = new listAdapter(MainActivity.this, itemList, new onClickListnerPlusMinus() {
                                @Override
                                public void onClickedMinus(int position, String sub, int a) {
                                    String ab = Integer.toString(a);
                                    myDb.updateabs(sub, ab);
                                }

                                @Override
                                public void onClickedPlus(int position, String sub, int p) {
                                    String pr = Integer.toString(p);
                                    myDb.updatepres(sub, pr);
                                }
                            });
                            subList.setAdapter(mlistAd);

                            add_dialog.dismiss();
                        }
                    }
                    }
                });

                cancel_Btna.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {////intructions to be performed if cancel button is clicked

                        add_dialog.dismiss();
                    }
                });
            }
        });


        Fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater2 = MainActivity.this.getLayoutInflater();
                View view2 = inflater2.inflate(R.layout.dialog_delete,null);

                final EditText deleteSub = (EditText)view2.findViewById(R.id.deleteSubject);

                Button delete_Btn =  (Button)view2.findViewById(R.id.Deletebtn);
                Button cancel_Btnd = (Button)view2.findViewById(R.id.Cancelbtnd);

                builder2.setView(view2);
                final AlertDialog add_dialog2 = builder2.create();
                add_dialog2.show();     //delete dialogbox is shown

                delete_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {   ////intruction to be performed if delete button is clicked
                        String subject = deleteSub.getText().toString().trim();  //stores the value of subject enter for deletion
                        if (subject.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Subject not deleted. Fill the subject", Toast.LENGTH_LONG).show();
                        }
                        else {
                        Integer deletedRows = myDb.deleteData(subject);
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                        itemList = new ArrayList<>();
                        Cursor res = myDb.getAllData();
                        while (res.moveToNext()) {
                            itemList.add(new SubjectInfo(res.getInt(0), res.getString(1), res.getString(2),
                                    res.getString(3), res.getString(4), res.getString(5)));
                        }
                        mlistAd = new listAdapter(MainActivity.this, itemList, new onClickListnerPlusMinus() {
                            @Override
                            public void onClickedMinus(int position, String sub, int a) {
                                String ab = Integer.toString(a);
                                myDb.updateabs(sub, ab);
                            }

                            @Override
                            public void onClickedPlus(int position, String sub, int p) {
                                String pr = Integer.toString(p);
                                myDb.updatepres(sub, pr);
                            }
                        });
                        subList.setAdapter(mlistAd);

                        add_dialog2.dismiss();
                    }

                    }
                });

                cancel_Btnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { ////intruction to be performed if cancel button is clicked

                        add_dialog2.dismiss();
                    }
                });

            }
        });
    }


@Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }return super.onOptionsItemSelected(item);
}

}
