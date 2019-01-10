package com.example.tidu.attendancemanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "MINIMUM";
    public static final String COL_4 = "PRESENTS";
    public static final String COL_5 = "ABSENTS";
    public static final String COL_6 = "CURRENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MINIMUM TEXT,PRESENTS TEXT,ABSENTS TEXT,CURRENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String minimum,String presents,String absents,String current ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,minimum);
        contentValues.put(COL_4,presents);
        contentValues.put(COL_5,absents);
        contentValues.put(COL_6,current);


        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String minimum,String presents,String absents) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,minimum);
        contentValues.put(COL_4,presents);
        contentValues.put(COL_5,absents);

        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }
     public Cursor getItemId(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        int id = Integer.parseInt(name);
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COL_1+"=" +id+ ";" ;
        Cursor data=db.rawQuery(query,null);
        return data;
     }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NAME = ?",new String[] {id});
    }
    public boolean alldelete (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        return true;
    }
    public  boolean updatepres(String sub,String p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put(COL_4,p);
        db.update(TABLE_NAME, data, "NAME = ?",new String[] { sub });
        return  true;
    }
    public  boolean updateabs(String sub,String a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put(COL_5,a);
        db.update(TABLE_NAME, data, "NAME = ?",new String[] { sub });
        return  true;

    }
}
