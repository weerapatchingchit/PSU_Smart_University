package com.weerapat.psu_smart_university;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


///**
// * A simple {@link Fragment} subclass.
// */
public class RoomFragment extends Fragment {

    ArrayList<String> building = new ArrayList<String>();
    ArrayList<String> floor = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();

    View roomView;
    ListView listView;


    public RoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment



        roomView = inflater.inflate(R.layout.fragment_room, container, false);

        String[] str = {"Building 1","Building 1A","Building 2","Building 3","Building 5","Building 6","Building 7"} ;
        custom_List customList  = new custom_List(getContext(),str);

        listView = (ListView)roomView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("row",""+position);

                Intent intent = new Intent(getActivity(),BuildMapActivity.class);

                if(position == 0) {

                    intent.putExtra("building", "1");
                }
                else if(position == 1) {
                    intent.putExtra("building", "1A");
                }
                else if(position == 2) {
                    intent.putExtra("building", "2");
                }
                else if(position == 3) {
                    intent.putExtra("building", "3");
                }
                else if(position == 4) {
                    intent.putExtra("building", "5");
                }
                else if(position == 5) {
                    intent.putExtra("building", "6");
                }
                else if(position == 6) {
                    intent.putExtra("building", "7");
                }
                startActivity(intent);

            }
        });

        listView.setAdapter(customList);

        new getEvent().execute();

        return roomView;
    }




    public class getEvent extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

           // OkHttpClient okHttpClient = new OkHttpClient();

//            Request request = new Request.Builder()
//                    .url("http://weerapatchingchit.esy.es/PSU_Smart_University/db_get_event.php")
//                    .build();
            OkHttpClient ok = new OkHttpClient();
            //RequestBody rb = new FormBody.Builder()
              //      .add("num", "1")
                //    .build();
            Request request = new Request.Builder()
                    //.post(rb)
                    .url("http://weerapatchingchit.esy.es/PSU_Smart_University/service/room.php")
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
            try {
                JSONObject jsonObject = new JSONObject(a);

                for (int i = 0;i<jsonObject.getJSONArray("room").length();i++) {

                    building.add(jsonObject.getJSONArray("room").getJSONObject(i).getString("building"));
                    floor.add(jsonObject.getJSONArray("room").getJSONObject(i).getString("floor"));
                    link.add(jsonObject.getJSONArray("room").getJSONObject(i).getString("link"));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
