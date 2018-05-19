package com.example.user.storybook.FirstPart;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.storybook.R;

import java.util.HashMap;
import java.util.Map;

import static android.R.color.holo_green_light;

public class RegistrationFragment extends Fragment {
    EditText fname,lname,password,cpassword,email,phone;
    Button regbtn;
    RequestQueue queue;
    StringRequest request;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        fname = (EditText)v.findViewById(R.id.fname);
        lname = (EditText)v.findViewById(R.id.lname);
        password = (EditText)v.findViewById(R.id.password);
        cpassword = (EditText)v.findViewById(R.id.cpassword);
        email = (EditText)v.findViewById(R.id.email);
        regbtn = (Button)v.findViewById(R.id.regbtn);
        phone = (EditText)v.findViewById(R.id.phone);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().equals(cpassword.getText().toString())){
                    cpassword.setTextColor(holo_green_light);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    public void signUp(){

        final String firstname = fname.getText().toString().trim();
        final String lastname = lname.getText().toString().trim();
        final String uemail = email.getText().toString().trim();
        final String upassword = password.getText().toString().trim();
        final String ucpassword = cpassword.getText().toString().trim();
        final String uphone = phone.getText().toString().trim();
        Log.d("Mylogs",uemail);
        request = new StringRequest(Request.Method.POST, "http://nurflower.ru/registration/reg.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response.toString().equals("Successfully registered")){
                                ((MainActivity)getActivity()).switchFragment(2);
                            }else {
                                Toast.makeText(getActivity(),"Something wrong",Toast.LENGTH_SHORT).show();
                            }
                            Log.d("Mylogs",response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }},
                new Response.ErrorListener(){

                    public void onErrorResponse(VolleyError volleyError){
                        Log.d("Mylogs",volleyError.toString());

                    }
                }

        )
        {
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("firstname",firstname);
                map.put("lastname",lastname);
                map.put("email",uemail);
                map.put("password",upassword);
                map.put("phone",uphone);
                return  map;
            }
        };
        queue = Volley.newRequestQueue(getActivity());
        queue.add(request);


    }
}
