package com.example.sample_sqlite.Class;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sample_sqlite.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    private ArrayList<Student> arrayList;
    private Context context;

    public CustomAdapter( Context cont,ArrayList<Student> arrayList){

        this.context = cont;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

             LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inf.inflate(R.layout.mycustomlayout, null);

             TextView txt_id = (TextView) convertView.findViewById(R.id.TextView_id);
             TextView txt_lname = (TextView)convertView.findViewById(R.id.TextView_lname);
             TextView txt_fname = (TextView) convertView.findViewById(R.id.TextView_fname);
             TextView txt_mname = (TextView) convertView.findViewById(R.id.TextView_mname);

             Student student = arrayList.get(position);
             txt_id.setText(String.valueOf(student.getId()));
             txt_lname.setText(student.getLname());
             txt_fname.setText(student.getFname());
             txt_mname.setText(student.getMname());

            return convertView;

    }

//    public static class ViewHolder{
//    public TextView id;
//    public TextView lname;
//    public TextView fname;
//    public TextView mname;
//
//    }

//    public ArrayList<Student> getAllData(){
//        ArrayList<Student> studentlist = new ArrayList<>();
//        myDataBase = this.getWritableDatabase();
//        Cursor res = myDataBase.rawQuery("Select * from tbl_name",null);
//
//        while(res.moveToNext()){
//            String id = res.getString(0);
//            String lname = res.getString(1);
//            String fname = res.getString(2);
//            String mname = res.getString(3);
//            Student newStudent = new Student("","","","");
//
//        }
//
//        return studentlist;
//    }


//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder holder = null;
//
//        if(convertView == null){
//            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inf.inflate(R.layout.mycustomlayout, null);
//
//            holder = new ViewHolder();
//
//            holder.id = (TextView) convertView.findViewById(R.id.TextView_id);
//            holder.lname = (TextView)convertView.findViewById(R.id.TextView_lname);
//            holder.fname = (TextView) convertView.findViewById(R.id.TextView_fname);
//            holder.mname = (TextView) convertView.findViewById(R.id.TextView_mname);
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder)convertView.getTag();
//        }
//
//        Student stu = arrayList.get(position);
//        holder.id.setText(String.valueOf(stu.getId()));
//        holder.lname.setText(stu.getLname());
//        holder.fname.setText(stu.getFname());
//        holder.mname.setText(stu.getMname());
//
//        return convertView;
//
//    }
}
