package com.weerapat.psu_smart_university;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.weerapat.psu_smart_university.R.id.map;

public class BuildMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;


    private int[] build1 = new int[]{R.drawable.b11, R.drawable.b12, R.drawable.b13};
    private int[] build1a = new int[]{R.drawable.b11a, R.drawable.b12a, R.drawable.b13a, R.drawable.b14a};
    private int[] build2 = new int[]{R.drawable.b11, R.drawable.b12, R.drawable.b12, R.drawable.b13};
    private int[] build3 = new int[]{R.drawable.b31, R.drawable.b32, R.drawable.b33, R.drawable.b34};
    private int[] build5 = new int[]{R.drawable.b11, R.drawable.b12, R.drawable.b12, R.drawable.b13};
    private int[] build6 = new int[]{R.drawable.b61, R.drawable.b62, R.drawable.b63, R.drawable.b64, R.drawable.b65, R.drawable.b66, R.drawable.b67};
    private int[] build7 = new int[]{R.drawable.b71, R.drawable.b72, R.drawable.b73, R.drawable.b74, R.drawable.b75};
    private int sizeBuilding[] = {3,4,4,4,4,7,5};

    private LinearLayout layoutBt;
    private GroundOverlay imageOverlay;
    private String building;
    private Boolean checkFirst = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);



        Bundle bundle = getIntent().getExtras();
        building = bundle.getString("building");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        layoutBt = (LinearLayout) findViewById(R.id.linearButton);


        Log.d("ffff", " gg " + building);
        if(building.equals("1"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.894814, 98.352132),18));

            for(int i = build1.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.894516, 98.351810),       // South west corner
                                new LatLng(7.895053, 98.352478));      // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build1[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }
        else if(building.equals("1A"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.894814, 98.352132),18));

            for(int i = build1a.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.894516, 98.351810),       // South west corner
                                new LatLng(7.895053, 98.352478));     // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build1a[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }
        else if(building.equals("2"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.893822, 98.352885),18));
            for(int i = build2.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.893223, 98.351793),       // South west corner
                                new LatLng(7.893600, 98.352684));      // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build2[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }
        else if(building.equals("3"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.893822, 98.352885),18));

            for(int i = build3.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.893573, 98.352558),       // South west corner
                                new LatLng(7.894072, 98.353205));      // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build3[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }
        else if(building.equals("5"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.894030, 98.353435),18));


            for(int i = build5.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.893857, 98.353154),       // South west corner
                                new LatLng(7.894242, 98.353752));      // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build5[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }
        else if(building.equals("6"))
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.893404, 98.352239),18));


            for(int i = build6.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.893122, 98.351804),       // South west corner
                                new LatLng(7.893770, 98.352678));      // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build6[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }
        else if(building.equals("7"))
        {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.893523, 98.353424),18));

            for(int i = build7.length-1; i >= 0; i--)
            {
                Button bt = new Button(getBaseContext());
                bt.setBackgroundColor(Color.parseColor("#D9FFFFFF"));
                bt.setTextColor(Color.GRAY);
                bt.setText(""+(i+1));
                final int finalI = i;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkFirst != false)
                            imageOverlay.remove();

                        LatLngBounds newarkBounds = new LatLngBounds(
                                new LatLng(7.893247, 98.353084),       // South west corner
                                new LatLng(7.893818, 98.353690));      // North east corner
                        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(build7[finalI]))
                                .positionFromBounds(newarkBounds);

                        imageOverlay = mMap.addGroundOverlay(newarkMap);


                        checkFirst = true;
                    }
                });

                layoutBt.addView(bt);
            }
        }

    }
}
