package com.example.gujiawei.movieapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkConnected(this)) {
            showErrorMsg("No Internet access. Please connect to the Internet first.");
        } else {
            mFragments = getFragments();
            initView();
            if (GenreMap.generateGenreMap() == -1) {
                showErrorMsg("Cannot get genres. Please reload the program again.");
            }
        }
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        mFragmentManager = getSupportFragmentManager();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < Metadata.tabTitles.length; i++)
            mTabLayout.addTab(mTabLayout.newTab().setText(Metadata.tabTitles[i]));
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        if (position < mFragments.length)
            fragment = mFragments[position];
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_container, fragment);
            fragmentTransaction.commit();
        }
    }

    private static Fragment[] getFragments() {
        Fragment fragments[] = new Fragment[2];
        fragments[0] = new NowPlayingFragment();
        fragments[1] = new UpcomingMoviesFragment();
        return fragments;
    }

    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }
        return false;
    }

    private void showErrorMsg(String msg) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("ERROR");
        normalDialog.setMessage(msg);
        normalDialog.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        normalDialog.show();
    }
}