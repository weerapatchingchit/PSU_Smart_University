package com.weerapat.psu_smart_university;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.provider.Settings.Secure;

/**
 * Created by weerapat on 9/24/2016 AD.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private String android_id ;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        android_id = Secure.getString(getBaseContext().getContentResolver(),
                Secure.ANDROID_ID);
        String strToken;
       strToken = FirebaseInstanceId.getInstance().getToken();

        Log.d("GCM : ", strToken + " " + android_id);

        new saveToken().execute(android_id,strToken);


    }

    public class saveToken extends AsyncTask<String, Void ,Void>
    {

        @Override
        protected Void doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("device_id",strings[0])
                    .add("fcm_id",strings[1])
                    .build();
            Request request = new Request.Builder()
                    .url("http://weerapatchingchit.esy.es/PSU_Smart_University/service/saveTokenID.php")
                    .post(requestBody)
                    .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("test",response.body().string());

                        String result = response.body().string();


                    }
                });

            return null;
        }
    }
}
