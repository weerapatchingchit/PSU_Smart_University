package com.weerapat.psu_smart_university;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ArrayList<LatLng> latlng  = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new getEvent().execute();

    }

    public class getEvent extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient ok = new OkHttpClient();
            //RequestBody rb = new FormBody.Builder()
            //      .add("num", "1")
            //    .build();
            Request request = new Request.Builder()
                    //.post(rb)
                    .url("https://maps.googleapis.com/maps/api/directions/json?origin=Trang&destination=Phuket=AIzaSyBlg4Wvm_r-ae7YWL_6qpHBusq4bNDlgoo")
                    .build();

            Response response = null;
            try {
                response = ok.newCall(request).execute();
                //Log.d("test",""+response.body().string());
                return "" + response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String a) {
            super.onPostExecute(a);

            Log.d("test",""+a);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(a);
                //for (int i = 0;i<jsonObject.getJSONArray("routes").length();i++){
                    Log.d("test",""+jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getString("lat"));
                Log.d("test1",""+jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(0).getJSONObject("start_location").getString("lng"));

                String lat1 = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getString("lat");
                String lng1 = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getString("lng");
                LatLng ll1 = new LatLng(Double.valueOf(lat1),Double.valueOf(lng1));
                latlng.add(ll1);

                for (int i = 0;i<jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").length();i++) {
                String lat = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(i).getJSONObject("end_location").getString("lat");
                    String lng = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(i).getJSONObject("end_location").getString("lng");
                LatLng ll = new LatLng(Double.valueOf(lat),Double.valueOf(lng));

                    latlng.add(ll);

                }
                ;
                //}

            } catch (JSONException e) {
                e.printStackTrace();
            }
            PolylineOptions polylineOptions = new PolylineOptions();

            for(int i = 0; i<latlng.size();i++){
                polylineOptions.add(latlng.get(i));
            }

            polylineOptions.color(Color.RED);
            polylineOptions.width(3);
            mMap.addPolyline(polylineOptions);


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        MarkerOptions markerOptions1 = new MarkerOptions();
        LatLng latLng1 = new LatLng(7.894816, 98.352095);
        markerOptions1.title("Building 1");
        markerOptions1.snippet("Faculty of Technology and Environment");
        //markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_icon));
        markerOptions1.position(latLng1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.addMarker(markerOptions1);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1,13));

        MarkerOptions markerOptions2 = new MarkerOptions();
        LatLng latLng2 = new LatLng(7.894070, 98.351871);
        markerOptions2.title("Building 2");
        markerOptions2.snippet("Faculty of Hospitality and Tourism");
        markerOptions2.position(latLng2);
        mMap.addMarker(markerOptions2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        LatLng latLng3 = new LatLng(7.893817, 98.352889);
        markerOptions3.title("Building 3");
        markerOptions3.snippet("Faculty of International Study");
        markerOptions3.position(latLng3);
        mMap.addMarker(markerOptions3);

        MarkerOptions markerOptions5 = new MarkerOptions();
        LatLng latLng5 = new LatLng(7.893698, 98.353422);
        markerOptions5.title("Building 5");
        markerOptions5.snippet("Library");
        markerOptions5.position(latLng5);
        mMap.addMarker(markerOptions5);

        MarkerOptions markerOptions6 = new MarkerOptions();
        LatLng latLng6 = new LatLng(7.893448, 98.352263);
        markerOptions6.title("Building 6");
        markerOptions6.snippet("Department of Computer Engineering");
        markerOptions6.position(latLng6);
        mMap.addMarker(markerOptions6);

        MarkerOptions markerOptions7 = new MarkerOptions();
        LatLng latLng7 = new LatLng(7.894508, 98.352910);
        markerOptions7.title("Building 7");
        markerOptions7.snippet("Administrator Building");
        markerOptions7.position(latLng7);
        mMap.addMarker(markerOptions7);





    }
}

//marker1 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.894816, 98.352095))
//        .title("Building 1").icon(BitmapDescriptorFactory.fromBitmap(mBitmap1))
//        .snippet("Faculty of Technology and Environment"));
//        marker2 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.894070, 98.351871))
//        .title("Building 2").icon(BitmapDescriptorFactory.fromBitmap(mBitmap2))
//        .snippet("Faculty of Hospitality and Tourism"));
//        marker3 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.893817, 98.352889))
//        .title("Building 3").icon(BitmapDescriptorFactory.fromBitmap(mBitmap3))
//        .snippet("Faculty of International Study"));
//        marker4 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.895026, 98.351283))
//        .title("Building 4").icon(BitmapDescriptorFactory.fromBitmap(mBitmap4)));
//        marker5 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.893698, 98.353422))
//        .title("Building 5").icon(BitmapDescriptorFactory.fromBitmap(mBitmap5))
//        .snippet("Library"));
////        marker5a = mGoogleMap.addMarker(new MarkerOptions()
////                .position(new LatLng(7.894033, 98.353492))
////                .title("Building 4"));
//        marker6 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.893448, 98.352263))
//        .title("Building 6").icon(BitmapDescriptorFactory.fromBitmap(mBitmap6))
//        .snippet("Department of Computer Engineering"));
//        marker7 = mGoogleMap.addMarker(new MarkerOptions()
//        .position(new LatLng(7.894508, 98.352910))
//        .title("Building 7").icon(BitmapDescriptorFactory.fromBitmap(mBitmap7))
//        .snippet("Administrator Building"));
