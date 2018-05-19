package com.example.user.storybook.FirstPart;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.storybook.R;
import com.example.user.storybook.SecondPart.BasicActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    EditText email,password;
    Button login;
    TextView create;
    String susername,spassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v =inflater.inflate(R.layout.fragment_login, container, false);
        email = (EditText)v.findViewById(R.id.emailText);
        password = (EditText)v.findViewById(R.id.passwordText);
        login = (Button)v.findViewById(R.id.loginButton);
        create = (TextView)v.findViewById(R.id.createAccount);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).switchFragment(3);
            }
        });
        return v;
    }

    private void sendData() {
        susername = email.getText().toString().trim();
        spassword = password.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, "http://nurflower.ru/registration/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")) {
                            profilepage();
                            getActivity().finish();
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("username",susername);
                map.put("password",spassword);
                return map;
            }
        };
        RequestQueue queue =  Volley.newRequestQueue(getActivity());
        queue.add(request);
    }
    public void profilepage(){
        Intent intent = new Intent(getActivity(),BasicActivity.class);
        intent.putExtra("useremail",susername);
        startActivity(intent);

    }

}
