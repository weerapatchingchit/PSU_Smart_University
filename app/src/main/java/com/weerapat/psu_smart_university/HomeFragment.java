package com.weerapat.psu_smart_university;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;
import com.weerapat.psu_smart_university.Adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View fragmentView;
    private ViewPager pager;
    private CirclePageIndicator indicator;
    private PagerAdapter adapter;
    private ArrayList<View> imageList = new ArrayList<>();

    private int pageSize = 6;
    private int currentPage = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentView  = inflater.inflate(R.layout.fragment_home, container, false);

        setWidget();


        return fragmentView;
    }


    private void setWidget()
    {

        ImageView im1 = new ImageView(getActivity());
        im1.setImageResource(R.drawable.pageview1);

        ImageView im2 = new ImageView(getActivity());
        im2.setImageResource(R.drawable.pageview2);

        ImageView im3 = new ImageView(getActivity());
        im3.setImageResource(R.drawable.pageview3);

        ImageView im4 = new ImageView(getActivity());
        im4.setImageResource(R.drawable.pageview4);

        ImageView im5 = new ImageView(getActivity());
        im5.setImageResource(R.drawable.pageview5);

        ImageView im6 = new ImageView(getActivity());
        im6.setImageResource(R.drawable.pageview6);

        imageList.add(im1);
        imageList.add(im2);
        imageList.add(im3);
        imageList.add(im4);
        imageList.add(im5);
        imageList.add(im6);




        pager = (ViewPager) fragmentView.findViewById(R.id.pager);

        adapter = new ViewPagerAdapter(getContext(), imageList);
        pager.setAdapter(adapter);


        indicator = (CirclePageIndicator) fragmentView.findViewById(R.id.indicator);
        indicator.setRadius(12.0f);
        indicator.setViewPager(pager);


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == pageSize)
                    currentPage = 0;
                pager.setCurrentItem(currentPage++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1000, 4000);

    }

}
