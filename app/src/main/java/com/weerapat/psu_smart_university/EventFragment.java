package com.weerapat.psu_smart_university;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weerapat.psu_smart_university.Adapter.EventList;
import com.weerapat.psu_smart_university.Object.EventData;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements AdapterView.OnItemClickListener, LocationListener {


    private ListView listView;
    private View fragmentView;
    private LocationManager locationManager;
    private ArrayList<EventData> data = new ArrayList<>();
    private String android_id,studentId,studentName,studentFaculty;
    private Map<String,String> checkList = new HashMap<>();

    public EventFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_event, container, false);


        SharedPreferences pref = getActivity().getSharedPreferences("SAVEVALUE", Context.MODE_PRIVATE);
        studentId = pref.getString("student_id", "0");
        studentName = pref.getString("student_name", "0");
        studentFaculty = pref.getString("student_faculty", "0");



        listView = (ListView) fragmentView.findViewById(R.id.eventListView);

        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if(studentId.length() != 10)
        {
            studentId = android_id;
        }


        new getEvent().execute();

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        } else {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);


        return fragmentView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {

            //location
        } else if (requestCode == 1) {
            ///wakelock
        }
    }



    @Override
    public void onLocationChanged(Location location) {

        final double latitudeCurrent = location.getLatitude();
        final double longitudeCurrent = location.getLongitude();


        Log.d("XXXXXX", latitudeCurrent + " : " + longitudeCurrent);


        for (int i = 0; i < data.size(); i++) {
            final double latitude = Double.valueOf(data.get(i).latitude);
            final double longitude = Double.valueOf(data.get(i).longitude);


            Log.d("GGG", data.get(i).eventName);
            DatabaseReference rootAcept = FirebaseDatabase.getInstance().getReference().child("acept").child(data.get(i).eventName);
            final int finalI = i;
            rootAcept.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Log.d("TRE", dataSnapshot.getKey() + " " + studentId);
                    String user = dataSnapshot.getKey();

                    if (user.equals(studentId))
                    {

                        Log.d("vcvcv","fdfsdg");
                        double distanceLocation = distance(latitudeCurrent, longitudeCurrent, latitude, longitude, "m");

                        DatabaseReference rootAdd = FirebaseDatabase.getInstance().getReference().child("check").child(data.get(finalI).eventName);


                        Date dateTime = new Date();

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String dateNow = dateFormat.format(dateTime);

                        DateFormat timeFormat = new SimpleDateFormat("HHmm");
                        String timeNow =timeFormat.format(dateTime);


                        String st[] =  data.get(finalI).startTime.split(":");
                        String en[] =  data.get(finalI).endTime.split(":");

                        int stNum = Integer.valueOf(st[0]+st[1]);
                        int enNum = Integer.valueOf(en[0]+en[1]);

                        Log.d("GGYTT",dateNow + " : " + data.get(finalI).date + " : " + timeNow + " : " + stNum + " : " + enNum);
                        if(data.get(finalI).date.equals(dateNow))
                        {

                            if(Integer.valueOf(timeNow) >= stNum && Integer.valueOf(timeNow) <= enNum) {
                                if (distanceLocation <= 100) {


//                                    checkList.put(data.get(finalI).eventName,"1");



                                    Map<String, Object> dataZ = new HashMap<>();
                                    dataZ.put(studentId, "");
                                    rootAdd.updateChildren(dataZ);



                                    DatabaseReference rootCheck = FirebaseDatabase.getInstance().getReference().child("count_check").child(data.get(finalI).eventName);

                                    if(checkList.get(data.get(finalI).eventName).equals("0"))
                                    {
                                        DatabaseReference rootChildNew = rootCheck.child(studentId);
                                        Map<String, Object> datas = new HashMap<>();
                                        datas.put("student_id",studentId);
                                        datas.put("student_name",studentName);
                                        datas.put("date_in", timeNow + " " + dateNow);
                                        rootChildNew.updateChildren(datas);
                                    }

                                    checkList.put(data.get(finalI).eventName,"1");

                                    DatabaseReference rootChildNew = rootCheck.child(studentId);
                                    Map<String, Object> datas = new HashMap<>();
                                    datas.put("date_last", timeNow + " " + dateNow);
                                    rootChildNew.updateChildren(datas);



                                } else {
                                    rootAdd.child(studentId).removeValue();
                                }
                            }
                            Log.d("KKKK", "" + distance(latitudeCurrent, longitudeCurrent, latitude, longitude, "m") + " m");
                        }
                    }


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
            });
        }


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


    public void finishload() {

        EventList eventList = new EventList(fragmentView.getContext(), data);

        listView.setAdapter(eventList);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("xxx", "xxxx");
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        intent.putExtra("pri_key", data.get(position).priKey);
        intent.putExtra("event_name", data.get(position).eventName);
        intent.putExtra("detail", data.get(position).detail);
        intent.putExtra("faculty", data.get(position).faculty);
        intent.putExtra("place", data.get(position).place);
        intent.putExtra("date", data.get(position).date);
        intent.putExtra("start_time", data.get(position).startTime);
        intent.putExtra("end_time", data.get(position).endTime);
        intent.putExtra("ext_link", data.get(position).extLink);
        intent.putExtra("phone_no", data.get(position).phone);
        intent.putExtra("latitude", data.get(position).latitude);
        intent.putExtra("longitude", data.get(position).longitude);
        intent.putExtra("add_id", data.get(position).addId);
        startActivity(intent);
    }

    public class getEvent extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient ok = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://weerapatchingchit.esy.es/PSU_Smart_University/service/db_get_event.php")
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

            Log.d("test", "" + a);

            try {
                JSONArray json = new JSONArray(a);

                for (int i = 0; i < json.length(); i++) {


                    String pri_key = json.getJSONObject(i).getString("pri_key");
                    String event_name = json.getJSONObject(i).getString("event_name");
                    String detail = json.getJSONObject(i).getString("detail");
                    String faculty = json.getJSONObject(i).getString("faculty");
                    String place = json.getJSONObject(i).getString("place");
                    String date = json.getJSONObject(i).getString("date");
                    String start_time = json.getJSONObject(i).getString("start_time");
                    String end_time = json.getJSONObject(i).getString("end_time");
                    String phone_no = json.getJSONObject(i).getString("phone_no");
                    String ext_link = json.getJSONObject(i).getString("ext_link");
                    String latitude = json.getJSONObject(i).getString("latitude");
                    String longitude = json.getJSONObject(i).getString("longitude");
                    String add_id = json.getJSONObject(i).getString("add_id");

                    EventData eventData = new EventData(pri_key, event_name, detail, faculty, place, date, start_time, end_time, phone_no, ext_link, latitude, longitude, add_id);

                    checkList.put(event_name,"0");
                    data.add(eventData);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            finishload();
        }
    }


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "m") {
            dist = dist * 1.609344 * 1000;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
