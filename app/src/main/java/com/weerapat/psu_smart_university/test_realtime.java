package com.weerapat.psu_smart_university;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class test_realtime extends AppCompatActivity {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_realtime);


        Button button = (Button)findViewById(R.id.btn_real);

        final DatabaseReference child = root.child("Event");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> val = new HashMap<String,Object>();

                val.put("iddevice","1");

                child.updateChildren(val);
//                root.updateChildren(val);



            }
        });

child.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Iterator i =  dataSnapshot.getChildren().iterator();

        while(i.hasNext())
        {
            String a = (String) ((DataSnapshot)i.next()).getValue();

            Log.d("hjhj",a);
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i =  dataSnapshot.getChildren().iterator();

                while(i.hasNext())
                {
                    //String a = (String) ((DataSnapshot)i.next()).getValue();

                    //Log.d("xczcdsff",a);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void click()
    {
        Log.d("xx","xxx");
    }
}
