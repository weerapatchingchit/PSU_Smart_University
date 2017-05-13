package com.weerapat.psu_smart_university.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weerapat.psu_smart_university.Object.EventData;
import com.weerapat.psu_smart_university.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weerapat on 9/21/2016 AD.
 */
public class EventList extends BaseAdapter {

    private Context context;
    private ArrayList<EventData> data = new ArrayList<>();

    public EventList (Context context,ArrayList<EventData> data){

        ///กำหนดค่าเริ่มต้น


        this.data = data;
        this.context = context;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        //ดึงฟร้อน
        Typeface typeface  = Typeface.createFromAsset(context.getAssets(),"Cloud-Bold.otf");

        //สร้างออฟเจ็คสำหรับการดึงเลเอ้า
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        holder holder = null;


        //เงื่อนไขสำหรับสร้าง List

        if(convertView == null) {

            //เชื่อมโยงเลเอ้า
            convertView = layoutInflater.inflate(R.layout.event_list, null);


            //สร้างออฟเจ็คของเ Textview และ ImageView

            holder = new holder();


            //เชื่อมโยงไอดี
            holder.tv1 = (TextView)convertView.findViewById(R.id.textLine1);
            holder.tv2 = (TextView)convertView.findViewById(R.id.textLine2);
            holder.tv3 = (TextView)convertView.findViewById(R.id.textLine3);
            holder.tv4 = (TextView)convertView.findViewById(R.id.textLine4);
            holder.im1 = (ImageView)convertView.findViewById(R.id.ImageEvent);


            //ตั้งค่าฟร้อน
            holder.tv1.setTypeface(typeface);
            holder.tv2.setTypeface(typeface);
            holder.tv3.setTypeface(typeface);
            holder.tv4.setTypeface(typeface);


            //ดึงข้อมูลสำหรับการเข้าร่วมกิจกรรม

            DatabaseReference rootChild = FirebaseDatabase.getInstance().getReference().child("check").child(data.get(position).eventName);
            final EventList.holder finalHolder = holder;
            final EventList.holder finalHolder1 = holder;
            rootChild.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d("count ", " : -> " + dataSnapshot.getKey() + " : ");
                    finalHolder.count++;
                    finalHolder.tv4.setText(String.valueOf(finalHolder.count));


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.d("count Change", " : -> " + dataSnapshot.getKey() + " : ");
                    finalHolder.count++;
                    finalHolder.tv4.setText(String.valueOf(finalHolder.count));



                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d("count Remove ", " : -> " + dataSnapshot.getKey() + " : ");
                    if(finalHolder.count>0) {
                        finalHolder.count--;
                        finalHolder1.tv4.setText(String.valueOf(finalHolder.count));
                    }

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            convertView.setTag(holder);
        }
        else
        {
            //หากมีการสร้าาง Object ขึ้นมาแล้วให้ทำการใช้ Object ตัวเดิม
            holder = (holder)convertView.getTag();
        }


        //ตั้งค่ารูป

        if(data.get(position).faculty.equals("COE")){
            holder.im1.setImageResource(R.drawable.icon_coe);
        }
        else if(data.get(position).faculty.equals("FHT")){
            holder.im1.setImageResource(R.drawable.icon_fht);
        }
        else if(data.get(position).faculty.equals("FTE")){
            holder.im1.setImageResource(R.drawable.icon_fte);
        }
        else if(data.get(position).faculty.equals("FIS")){
            holder.im1.setImageResource(R.drawable.icon_fis);
        }
        else
        {
            holder.im1.setImageResource(R.drawable.unknow);
        }


        //ตั้งค่า Text
        holder.tv1.setText(data.get(position).eventName);
        holder.tv2.setText(data.get(position).place);
        holder.tv3.setText(data.get(position).date);






        return convertView;

    }

    //object ของ textview imageview

    public class holder
    {
        private TextView tv1,tv2,tv3,tv4;
        private ImageView im1;
        private int count = 0;

    }
}
