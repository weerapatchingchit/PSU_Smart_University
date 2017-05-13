package com.weerapat.psu_smart_university;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.weerapat.psu_smart_university.Object.EventData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;
    private EditText studentIdEdit;
    private EditText passwordEdit;
    private ProgressDialog dialog;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        setWidget();


        return fragmentView;
    }
    private void setWidget() {





        studentIdEdit = (EditText)fragmentView.findViewById(R.id.studentId);
        passwordEdit = (EditText)fragmentView.findViewById(R.id.password);

        ImageButton submitBt = (ImageButton)fragmentView.findViewById(R.id.submit);
        submitBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.submit:


                new checkLogin().execute(studentIdEdit.getText().toString(),passwordEdit.getText().toString());

                break;
        }
    }


    public class checkLogin extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "",
                    "Loading. Please wait...", false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient ok = new OkHttpClient();

            RequestBody rb = new FormBody.Builder()
                  .add("student_id",params[0])
                    .add("password",params[1])
                .build();

            Request request = new Request.Builder()
                    .url("http://weerapatchingchit.esy.es/PSU_Smart_University/check_login_student.php")
                    .post(rb)
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

            dialog.dismiss();
            Log.d("test", "" + a);

            if(a.trim().equals("correct"))
            {
                Toast.makeText(getActivity(),"ไม่สามารถเข้าระบบได้กรุณาตรวจสอบรหัสและพาสเวิร์ด",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getActivity(),"เข้าสู่ระบบสำเร็จ",Toast.LENGTH_LONG).show();
                try {
                    JSONObject json = new JSONObject(a);

                    String studentIdGet = json.getString("student_id");
                    String studentNameGet = json.getString("student_name");

                    String studentFaculty = json.getString("faculty");


                    Log.d("VVVV", studentIdGet + " " + studentNameGet + " " + studentFaculty);

                    SharedPreferences pref = getActivity().getSharedPreferences("SAVEVALUE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("student_id",studentIdGet);
                    editor.putString("student_name",studentNameGet);
                    editor.putString("student_faculty",studentFaculty);
                    editor.commit();

                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
