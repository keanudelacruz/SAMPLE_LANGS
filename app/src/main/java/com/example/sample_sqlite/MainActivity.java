package com.example.sample_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.system.ErrnoException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample_sqlite.Class.CustomAdapter;
import com.example.sample_sqlite.Class.Student;
import com.example.sample_sqlite.db.DatabaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button button_save,button_import,button_update,buttone_delete,button_show;
    private EditText editText_id,editText_lname,editText_fname,editText_mname;


    Cursor cursor = null;
    ListView listView;
   // ListAdapter listAdapter;
    ArrayList<Student> arrayList;
    CustomAdapter mylistAdapter;

    //Call DatabaseHelper.java Method
DatabaseHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        listView = (ListView) findViewById(R.id.list_item1);
        myDbHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        viewData();


        ImportDatabase();
       InsertData();
      DeleteData();
        updateData();
        viewAllData();



        viewData();
    }

    private void initView(){

        editText_id = (EditText) findViewById(R.id.editText_id);
        editText_lname = (EditText) findViewById(R.id.editText_lname);
        editText_fname = (EditText) findViewById(R.id.editText_fname);
        editText_mname = (EditText) findViewById(R.id.editText_mname);

        button_update = (Button) findViewById(R.id.button_Update);
        button_import = (Button) findViewById(R.id.button_import);
        buttone_delete = (Button) findViewById(R.id.button_delete);
        button_save = (Button) findViewById(R.id.button_save);
        button_show = (Button) findViewById(R.id.button_show);







    // listAdapter = new com.example.sample_sqlite.Class.ListAdapter(MainActivity.this,R.id.list_item1);
     //listView.setAdapter(listAdapter);


    }

    //Create method Import SQLite database using DB Browser for SQLite
    public void ImportDatabase(){


        button_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
                try {
                    myDbHelper.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }

                try {
                    myDbHelper.openDataBase();
                }  catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                ShowMessage("Database","Successfully Imported");
//                c = myDbHelper.query("tbl_name", null, null, null, null, null, null);
//                if (c.moveToFirst()) {
//                    do {
//                        Toast.makeText(MainActivity.this,
//                                "_id: " + c.getString(0) + "\n" +
//                                        "lname: " + c.getString(1) + "\n" +
//                                        "fname: " + c.getString(2) + "\n" +
//                                        "mname:  " + c.getString(3),
//                                Toast.LENGTH_LONG).show();
//                    } while (c.moveToNext());
//                }
            }
        });
    }
    //Create method to Insert data to database
    public void InsertData(){

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
                try {
                    myDbHelper.openDataBase();
                    boolean isInsert = myDbHelper.insertData(editText_lname.getText().toString(),editText_fname.getText().toString(),editText_mname.getText().toString());
                    if(isInsert == true){
                        ShowMessage("Student Data","Successfully Saved");
                    }else{
                        ShowMessage("Student Data","Failed to Save");
                    }

                    myDbHelper.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    //Create method to View all data using Listview
    public void viewData(){


        try{

            myDbHelper.openDataBase();
            arrayList = myDbHelper.getAllData();
            mylistAdapter = new CustomAdapter(MainActivity.this ,arrayList);
            listView.setAdapter(mylistAdapter);
            mylistAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    //Create method to view all data using ShowMessage
    public void viewAllData(){

        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
                try{
                    myDbHelper.openDataBase();
                    Cursor res = myDbHelper.selectAllData();
                    if(res.getCount()==0){
//                        Toast.makeText(MainActivity.this,"No Data", Toast.LENGTH_LONG).show();
                        ShowMessage("Error","No Student Data");
                        return;
                    }else{
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("\nID:" + res.getString(0));
                            buffer.append("\nLastName: " + res.getString(1));
                            buffer.append("\nFirstName: " + res.getString(2));
                            buffer.append("\nMiddleName: " + res.getString(3) + "\n");
                        }
                        ShowMessage("Student Data",buffer.toString());
                        myDbHelper.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
    //Create method to create AlertDialog
    public void ShowMessage(String title,String messasge){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(messasge);
        builder.show();
    }
    //Create method to Update data to database
    public void updateData(){

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
                try {
                    myDbHelper.openDataBase();
                    boolean isUpdate = myDbHelper.updateData(editText_id.getText().toString(),editText_lname.getText().toString(),editText_fname.getText().toString(),editText_mname.getText().toString());
                    if (isUpdate == true){
                        ShowMessage("Student Data","Successfully Updated.");
                    }else{
                        ShowMessage("Student Data","Update failed");
                    }
                    myDbHelper.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    //Create method to Delete data to database
    public void DeleteData(){

        buttone_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Delete");
            builder.setMessage("Are you sure want to delete?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
                            try {
                                myDbHelper.openDataBase();
                                myDbHelper.deleteData(editText_id.getText().toString());
                                ShowMessage("Student Data","Successfully Deleted");
                                myDbHelper.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
}
