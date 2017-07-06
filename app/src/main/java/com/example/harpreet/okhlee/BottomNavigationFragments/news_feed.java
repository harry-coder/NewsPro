package com.example.harpreet.okhlee.BottomNavigationFragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.TabsFragments.category;
import com.example.harpreet.okhlee.TabsFragments.headlines;
import com.example.harpreet.okhlee.TabsFragments.trending;
import com.example.harpreet.okhlee.volly.MyApplication;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class news_feed extends Fragment {

    ViewPager pager;
    TabLayout tabs;
    Toolbar toolbar;

    public news_feed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_feed, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new myAdapter(getFragmentManager()));
        pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        tabs = (TabLayout) view.findViewById(R.id.tablayout);
        tabs.setupWithViewPager(pager);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("OKHLEE");

       /* if (!isConnectedToInternet()) {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this file!")

                    .show();

        }
*/
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);


        return view;
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isConnectedToInternet()) {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Internet Warning?")
                    .setContentText("Please check your internet connection!!")

                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            getActivity().finish();

                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        }

    }

    class myAdapter extends FragmentStatePagerAdapter {


        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if (position == 0) {
                return new category();
                //  return null;
            } else if (position == 1)

            {
                //return new category();
                //return null;
                return new headlines();
            } else if (position == 2) {
                return new trending();
            }


            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Category";
            }
            if (position == 1) {
                return "Headline";
            }
            if (position == 2) {
                return "Trending";
            } else return null;
        }
    }

}
