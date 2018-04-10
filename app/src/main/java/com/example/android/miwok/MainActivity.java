/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView( R.layout.activity_main );

        ViewPager viewPager = (ViewPager) findViewById( R.id.view_main_fragment );

        FragmentPageAdapter adapter = new FragmentPageAdapter( getSupportFragmentManager() );

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs_miwok);

        tabLayout.setupWithViewPager( viewPager );
        tabLayout.setBackgroundColor( getResources().getColor( R.color.primary_color ) );
        tabLayout.setTabTextColors( Color.GRAY ,Color.WHITE );

    }
}
