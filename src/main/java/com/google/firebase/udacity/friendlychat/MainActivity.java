/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static boolean CLOUD_CONNECTION = false;
    public static boolean LOCAL_CONNECTION = false;

//    static StringBuilder data = new StringBuilder("");
//    static StringBuilder room2Data = new StringBuilder("");
//    static StringBuilder room3Data = new StringBuilder("");
//    static StringBuilder room4Data = new StringBuilder("");
/*    Asynutiles tasl = new Asynutiles(data);
    Asynutiles tas2 = new Asynutiles(room2Data);
    Asynutiles tas3 = new Asynutiles(room3Data);
    Asynutiles tas4 = new Asynutiles(room4Data);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SampleFregmantPagerAdapter adapter = null;
/*

        //check internet connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        if (CLOUD_CONNECTION) {
            //we are connected to a Cloud
            adapter = new SampleFregmantPagerAdapter(getSupportFragmentManager(), MainActivity.this, false);
            Toast.makeText(this, "your connected to cloud connection ...", Toast.LENGTH_SHORT).show();
            LOCAL_CONNECTION = false;

        } else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // may connected to ESP localy
            adapter = new SampleFregmantPagerAdapter(getSupportFragmentManager(), MainActivity.this, false);
            LOCAL_CONNECTION = true;
            Toast.makeText(this, "your connected to local connection ...", Toast.LENGTH_SHORT).show();
*//*            tasl.execute("http://" + roomIP1);
            tas2.execute("http://" + roomIP2);
            tas3.execute("http://" + roomIP3);
            tas4.execute("http://" + roomIP4);*//*
        } else {
            Toast.makeText(this, "your are not connected to internet \n please close the app ...", Toast.LENGTH_SHORT).show();
            adapter = new SampleFregmantPagerAdapter(getSupportFragmentManager(), MainActivity.this, true);

        }*/
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
                adapter = new SampleFregmantPagerAdapter(getSupportFragmentManager(), MainActivity.this, false);
                Toast.makeText(this, "your connected to cloud connection ...", Toast.LENGTH_SHORT).show();
        } else {
            
            Toast.makeText(this, "your are not connected to internet \n please close the app ...", Toast.LENGTH_SHORT).show();
            adapter = new SampleFregmantPagerAdapter(getSupportFragmentManager(), MainActivity.this, true);      
        }
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

}