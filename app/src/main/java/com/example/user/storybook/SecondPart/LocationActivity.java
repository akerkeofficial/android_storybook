package com.example.user.storybook.SecondPart;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.user.storybook.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.user.storybook.FirstPart.MainActivity.sp;

public class LocationActivity extends AppCompatActivity  implements OnMapReadyCallback {

    GoogleMap map;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()){
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_location);

            initMap();
        }else {}

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }
        else {
            Toast.makeText(this,"Can't connect to play services",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map!=null){
            map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                }
            });
        }
        if (marker!=null){
            marker.remove();
        }
                    MarkerOptions options = new MarkerOptions()
                    .title(sp.getString("name",""))
                    .draggable(true)
                    .position(new LatLng(Double.parseDouble(sp.getString("lat","")),Double.parseDouble(sp.getString("long",""))));


            marker =  map.addMarker(options);
        goToLocationZoom(Double.parseDouble(sp.getString("lat","")),Double.parseDouble(sp.getString("long","")),15);

    }
    private void goToLocationZoom(double lat,double lng,float zoom){
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        map.moveCamera(update);
    }
    public void back(View view){
        finish();
    }

}
