package com.example.harpreet.okhlee.BottomNavigationFragments;


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

import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.TabsFragments.category;
import com.example.harpreet.okhlee.TabsFragments.group;
import com.example.harpreet.okhlee.TabsFragments.headlines;
import com.example.harpreet.okhlee.TabsFragments.profile;
import com.example.harpreet.okhlee.TabsFragments.trending;
import com.example.harpreet.okhlee.TabsFragments.wallet;


public class Account extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Toolbar accountToolbar;
    private ViewPager pager;
    private TabLayout tabLayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account, container, false);

        pager = (ViewPager) view.findViewById(R.id.pager);

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        pager.setAdapter(new myAdapter(getFragmentManager()));

        tabLayout.setupWithViewPager(pager);

        accountToolbar= (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(accountToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Account");

        return view;
    }

    class myAdapter extends FragmentStatePagerAdapter {


        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //   Fragment fragment;
            if (position == 0) {
                return new group();
            }
            else if (position == 1)
            {
                return new profile();
            }
            else if (position == 2) {
                return new wallet();
            }
            else return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Group";
            }
            if (position == 1) {
                return "Profile";
            }
            if (position == 2) {
                return "Wallet";
            } else return null;
        }
    }

}
