package com.example.user.storybook.FirstPart;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.storybook.R;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences sp;
    public static String spName = "Storybook";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (sp ==null){
            sp = getSharedPreferences(spName,MODE_PRIVATE);
        }
        try {
            if (sp.getString("first","").toString().equals("true")){
                switchFragment(2);
            }
        }catch (NullPointerException e){
            Toast.makeText(this,"Something wrong",Toast.LENGTH_SHORT).show();
        }
        sp.edit().putString("first","true").commit();
        switchFragment(1);

    }
    public void switchFragment(int num){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (num){
            case 1:
                IntroductionFragment fragment = new IntroductionFragment();
                transaction.replace(R.id.frame,fragment).commit();
                break;
            case 2:
                LoginFragment fragment1 = new LoginFragment();
                transaction.replace(R.id.frame,fragment1).commit();
                break;
            case 3:
                RegistrationFragment fragment2 = new RegistrationFragment();
                transaction.replace(R.id.frame,fragment2).commit();
                break;
        }
    }
}
