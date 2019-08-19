package com.example.sample_sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sample_sqlite.Class.Student;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "Sample1";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("tbl_name", null, null, null, null, null, null);
    }


    public Cursor selectAllData(){
        myDataBase = this.getWritableDatabase();
        Cursor res = myDataBase.rawQuery("Select * from tbl_name",null);
        return res;
    }

    public Cursor selectData(String id){
        myDataBase = this.getWritableDatabase();
        Cursor res = myDataBase.rawQuery("Select * from tbl_name where _id=" + id ,null);
        return res;
    }
    public boolean insertData(String lname, String fname, String mname){
        try {
            myDataBase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("lname", lname);
            contentValues.put("fname", fname);
            contentValues.put("mname", mname);
            myDataBase.insert("tbl_name", null, contentValues);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateData(String id, String lname, String fname, String mname){
        try {
            myDataBase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", id);
            contentValues.put("lname", lname);
            contentValues.put("fname", fname);
            contentValues.put("mname", mname);
            myDataBase.update("tbl_name",contentValues,"_id= ?",new String[]{id});
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public void deleteData(String id){
        try {
            myDataBase = this.getWritableDatabase();
            myDataBase.delete("tbl_name","_id= ?",new String[]{id});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Student> getAllData(){
        ArrayList<Student> arrayList = new ArrayList<>();
        myDataBase = this.getWritableDatabase();
        Cursor cursor = myDataBase.rawQuery("Select * from tbl_name",null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String lname = cursor.getString(1);
            String fname = cursor.getString(2);
            String mname = cursor.getString(3);
            Student student = new Student(id,lname,fname,mname);
            arrayList.add(student);
        }
        return arrayList;
    }
}
