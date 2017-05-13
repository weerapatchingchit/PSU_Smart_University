package com.weerapat.psu_smart_university;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by weerapat on 9/21/2016 AD.
 */
public class custom_List extends BaseAdapter {
    private final Context context;
    private String[] str;

    public custom_List(Context con, String[] strs){

        this.context = con;
        this.str = strs;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        convertView = layoutInflater.inflate(R.layout.custom_list,null);

        TextView tv = (TextView)convertView.findViewById(R.id.textViewList);

        tv.setText(str[position]);



        return convertView;
    }
}


