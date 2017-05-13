package com.weerapat.psu_smart_university;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DirectionMapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;

    private LocationManager locationManager;
    private String latitudeCurrent;
    private String longitudeCurrent;
    private String latitude;
    private String longitude;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");
        eventName = bundle.getString("event_name");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }
        return decoded;
    }

    @Override
    public void onLocationChanged(Location location) {


        latitudeCurrent = String.valueOf(location.getLatitude());
        longitudeCurrent = String.valueOf(location.getLongitude());

        String latlngEnd = latitude + "," + longitude;
        String latlngStart = latitudeCurrent + "," + longitudeCurrent;

        new getDirection().execute(latlngStart, latlngEnd);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public class getDirection extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + params[0] + "&destination=" + params[1] + "&sensor=false=AIzaSyDrycepE_8zMq2r11jQaz4aIHGJa_T5y5o";

            OkHttpClient ok = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            try {
                response = ok.newCall(request).execute();
                return "" + response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String a) {
            super.onPostExecute(a);

            mMap.clear();


            if (ActivityCompat.checkSelfPermission(DirectionMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DirectionMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

            LatLng locationEvent = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
            mMap.addMarker(new MarkerOptions().position(locationEvent).title(eventName));

            LatLng currentLocation = new LatLng(Double.valueOf(latitudeCurrent), Double.valueOf(longitudeCurrent));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 19));


            JSONObject jsonObject = null;
            String polyLine = "";
            try {
                jsonObject = new JSONObject(a);
                polyLine = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            PolylineOptions polylineOptions = new PolylineOptions();

            List<LatLng> latLngPoly = decodePolyLine(polyLine);

            for (int i = 0; i < latLngPoly.size(); i++) {
                polylineOptions.add(latLngPoly.get(i));
            }

            polylineOptions.color(Color.BLUE);
            polylineOptions.width(9);
            mMap.addPolyline(polylineOptions);


        }
    }
}
