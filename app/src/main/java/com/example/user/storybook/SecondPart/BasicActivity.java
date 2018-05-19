package com.example.user.storybook.SecondPart;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.storybook.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class BasicActivity extends AppCompatActivity {
    BottomBar mBottomBar;
    String useremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        mBottomBar = BottomBar.attach(this,savedInstanceState);
        final Intent intent = getIntent();
        useremail = intent.getStringExtra("useremail");
        mBottomBar.setItemsFromMenu(R.menu.menu_main,new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int i) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                switch (i){
                    case R.id.Bottombaritemone:
                        NewsFragment fragment = new NewsFragment();
                        fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                        break;
                    case R.id.Bottombaritemtwo:
                        AddFragment addFragment = new AddFragment();
                        fragmentTransaction.replace(R.id.frameLayout,addFragment).commit();
                        break;
                    case R.id.Bottombaritemthree:
                        NotificationFragment notificationFragment = new NotificationFragment();
                        fragmentTransaction.replace(R.id.frameLayout,notificationFragment).commit();
                        break;
                    case R.id.Bottombaritemfour:
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentTransaction.replace(R.id.frameLayout,profileFragment).commit();
                        break;

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int i) {

            }
        });
        mBottomBar.mapColorForTab(0, "#F44336");
        mBottomBar.mapColorForTab(1,"#9C27B0");
        mBottomBar.mapColorForTab(2,"#03A9F4");

        mBottomBar.mapColorForTab(3,"#FF6F00");
    }
}
