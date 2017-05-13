package com.weerapat.psu_smart_university;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weerapat.psu_smart_university.Object.EventData;
import android.provider.Settings.Secure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DetailEventActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<EventData> data = new ArrayList<>();

    private Bundle bundle;
    private Button regisBt;
    private DatabaseReference rootCheck;
    private String android_id,studentId,studentName,studentFaculty ;
    private Boolean checkJoin= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        android_id = Secure.getString(getBaseContext().getContentResolver(),
                Secure.ANDROID_ID);

        SharedPreferences pref = getSharedPreferences("SAVEVALUE", Context.MODE_PRIVATE);
        studentId = pref.getString("student_id", "0");
        studentName = pref.getString("student_name", "0");
        studentFaculty = pref.getString("student_faculty", "0");

        if(studentId.length() < 5)
        {
            studentId = android_id;
        }


        setWidget();

        setCheckJoin();

    }

    private void setCheckJoin()
    {
        Log.d("GGG",bundle.getString("event_name"));
        rootCheck = FirebaseDatabase.getInstance().getReference().child("acept").child(bundle.getString("event_name"));

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                checkJoin(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                checkJoin(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        rootCheck.addChildEventListener(listener);
    }


    public void checkJoin(DataSnapshot dataSnapshot)
    {
        Log.d("xxxA",dataSnapshot.getKey());
        String key = dataSnapshot.getKey();
        if(key.equals(studentId))
        {
            regisBt.setText("ยกเลิกเข้าร่วม");
            checkJoin = true;
        }
        else
        {
            regisBt.setText("เข้าร่วม");
            checkJoin = false;
        }

//        Iterator<DataSnapshot> val = dataSnapshot.getChildren().iterator();
//        while (val.hasNext()) {
//
//            String value = (String) val.next().getValue();
//            Log.d("AFFF",value);
//        }
    }

    private void setWidget()
    {

        bundle = getIntent().getExtras();

        TextView tv1 = (TextView)findViewById(R.id.textname);
        tv1.setText(bundle.getString("event_name"));

        TextView tv2 = (TextView)findViewById(R.id.textdetail);
        tv2.setText(bundle.getString("detail"));

        TextView tv3 = (TextView)findViewById(R.id.textfaculty);
        tv3.setText(bundle.getString("faculty"));

        TextView tv4 = (TextView)findViewById(R.id.textplace);
        tv4.setText(bundle.getString("place"));

        TextView tv5 = (TextView)findViewById(R.id.textphone);
        tv5.setText(bundle.getString("phone_no"));

        TextView tv6 = (TextView)findViewById(R.id.textlink);
        tv6.setText(bundle.getString("ext_link"));

        TextView tv7 = (TextView)findViewById(R.id.textdate);
        tv7.setText(bundle.getString("date"));

        TextView tv8 = (TextView)findViewById(R.id.textstart);
        tv8.setText(bundle.getString("start_time"));

        TextView tv9 = (TextView)findViewById(R.id.textend);
        tv9.setText(bundle.getString("end_time"));

        regisBt = (Button)findViewById(R.id.btn_regis);
        regisBt.setOnClickListener(this);
        Button directBt = (Button)findViewById(R.id.btn_direction);
        directBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_regis:


                if(checkJoin == false) {
                    Map<String, Object> data = new HashMap<>();
                    data.put(studentId, "");

                    rootCheck.updateChildren(data);
                }
                else
                {
                    DatabaseReference rootChlidDelete = rootCheck.child(studentId);
                    rootChlidDelete.removeValue();
                    regisBt.setText("เข้าร่วม");
                    checkJoin = false;
                }
                break;
            case R.id.btn_direction:
                Intent intent = new Intent(this, DirectionMapActivity.class);
                intent.putExtra("latitude",bundle.getString("latitude"));
                intent.putExtra("longitude",bundle.getString("longitude"));
                intent.putExtra("event_name",bundle.getString("event_name"));
                startActivity(intent);
                break;
        }


    }
}
