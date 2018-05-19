package com.example.user.storybook.SecondPart;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.storybook.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.user.storybook.FirstPart.MainActivity.sp;

public class AddFragment extends Fragment implements PlaceSelectionListener{
    ImageButton postButton;
    ImageView choosePhoto;
    TextView editPhoto,addLocation;
    EditText description;
    private Bitmap bitmap;
    String latitude = "";
    String longitude ="";
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="http://nurflower.ru/registration/upload.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    String s;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_add, container, false);
        postButton = (ImageButton)view.findViewById(R.id.postButton);
        choosePhoto = (ImageView)view.findViewById(R.id.choosePhoto);
        editPhoto = (TextView)view.findViewById(R.id.editPhoto);
        addLocation = (TextView)view.findViewById(R.id.addLocation);
        description = (EditText)view.findViewById(R.id.description);
        Bundle extra = getActivity().getIntent().getExtras();
        s = extra.getString("useremail");
        PlaceAutocompleteFragment place  = (PlaceAutocompleteFragment)getChildFragmentManager().findFragmentById(R.id.autocomplete);
        place.setOnPlaceSelectedListener(this);
        place.setHint("Add location to your story");
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LocationActivity.class);
                startActivityForResult(intent,0);
            }
        });
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        return view;
    }
    @Override
    public void onPlaceSelected(Place place){
        Log.d("My",place.getName()+"");
        LatLng ll = place.getLatLng();
        longitude = ll.longitude+"";
        latitude = ll.latitude+"";
        sp.edit().putString("name",place.getName()+"").commit();
        sp.edit().putString("lat",latitude).commit();
        sp.edit().putString("long",longitude).commit();
    }

    @Override
    public void onError(Status status) {
        Log.d("My",status.toString());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                choosePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("Mylogs",encodedImage);
        return encodedImage;
    }
    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.d("My",s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d("My",volleyError.toString());

                          }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = description.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                params.put("useremail",s);
                params.put("long",longitude);
                params.put("lat",latitude);


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getBaseContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}
