package com.weerapat.psu_smart_university.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Apple on 2/2/2017 AD.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<View> drawable;

    public ViewPagerAdapter(Context context, ArrayList<View> drawable) {

        this.context = context;
        this.drawable = drawable;
    }

    @Override
    public int getCount() {
        return drawable.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        ImageView imageView;

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = inflater.inflate(R.layout.item_pager, container, false);

//        imageView = (ImageView) itemView.findViewById(R.id.img_news);
        View imageView = drawable.get(position);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}