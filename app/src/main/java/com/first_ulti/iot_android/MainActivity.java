package com.first_ulti.iot_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 pager;
    private LogInAdapter logInAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // instantiate
        pager = (ViewPager2)findViewById(R.id.pager);
        logInAdapter = LogInAdapter.newInstance(this);
        pager.setAdapter(logInAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> {
                    if(position == 0)
                        tab.setText("Login");
                    else if(position == 1)
                        tab.setText("Sign up");
                }
        ).attach();
    }

    public void startNewIoTActivity(){
        Intent intent = new Intent(this, IoTActivity.class);
        startActivity(intent);
    }
}