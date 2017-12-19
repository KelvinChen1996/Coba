package com.example.asus.pikachise.view.myfranchise.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class getFromMaps extends AppCompatActivity implements OnMapReadyCallback {
    private final static String LATITUDE = "LATITUDE";
    private final static String LONGITUDE = "LONGITUDE";

    @BindView(R.id.getfrommaps_fab) FloatingActionButton fab;
    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Marker marker;
    Double latitude, longitude;
    String getlatitude, getlongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_from_maps);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.getfrommaps_maps);
        mapFragment.getMapAsync(this);

        Intent getintent = getIntent();
        if(getintent.hasExtra(LATITUDE)) {
            getlatitude = getintent.getExtras().getString(LATITUDE);
            getlongitude = getintent.getExtras().getString(LONGITUDE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(MainActivity.this, longitude.toString() + " " + latitude.toString() , Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.putExtra(LATITUDE, String.valueOf(latitude));
                i.putExtra(LONGITUDE, String.valueOf(longitude));
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(3.587277, 98.692222);
//        Toast.makeText(this, getlatitude, Toast.LENGTH_SHORT).show();
        if (getlatitude!=null && getlongitude!=null) {
            latitude = Double.parseDouble(getlatitude);
            longitude = Double.parseDouble(getlongitude);
            LatLng position = new LatLng(latitude, longitude);
            marker =  mMap.addMarker(new MarkerOptions().position(position).title("Your marker").draggable(true));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));
        }else{
            latitude = 0.0;
            longitude = 0.0;
            LatLng position = new LatLng(latitude, longitude);
            marker =  mMap.addMarker(new MarkerOptions().position(position).title("Your marker").draggable(true));
            float zoomLevel = 0.50f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker!=null)
                    marker.remove();
                marker = mMap.addMarker(new MarkerOptions().position(latLng));
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
            }
        });
    }
}
