package com.weerapat.psu_smart_university;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by weerapat on 9/26/2016 AD.
 */
public class room_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_detail);

        Bundle bundle = getIntent().getExtras();
        bundle.getString("building");
        bundle.getString("floor");
        bundle.getString("link");


        TextView tv1 = (TextView) findViewById(R.id.building);

        tv1.setText(bundle.getString("building"));

        TextView tv2 = (TextView) findViewById(R.id.floor);
        tv2.setText(bundle.getString("floor"));

        ImageView iv1 = (ImageView)findViewById(R.id.imageShow);
        iv1.setImageDrawable(Drawable.createFromPath(bundle.getString("link")));


    }
}
