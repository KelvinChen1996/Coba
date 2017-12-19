package com.example.asus.pikachise.view.franchisedetail.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Outlet;
import com.example.asus.pikachise.model.OutletResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftaroutletmaps extends AppCompatActivity implements
        OnMapReadyCallback{
    @BindView(R.id.map_toolbar) Toolbar toolbar;
    private Context context;
    private apiService service;
    private SessionManagement session;
    private String token;
    private final static String NAME = "NAME";
    private final static String LOGO = "LOGO";
    private final static String BANNER = "BANNER";
    private final static String EMAIL = "EMAIL";
    private final static String AVERAGE_RATING = "AVERAGE_RATING";
    private final static String FRANCHISE_ID = "FRANCHISE_ID";
    private final static String CATEGORY = "CATEGORY";
    private final static String TYPE = "TYPE";
    private final static String ESTABLISHSINCE = "ESTABLISHSINCE";
    private final static String INVESTMENT = "INVESTMENT";
    private final static String FRANCHISEFEE = "FRANCHISEFEE";
    private final static String WEBSITE = "WEBSITE";
    private final static String ADDRESS = "ADDRESS";
    private final static String LOCATION = "LOCATION";
    private final static String PHONENUMBER = "PHONENUMBER";
    private final static String DETAIL = "DETAIL";
    Location myLocation;

    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid,
            extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee,
            extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;
    private GoogleMap mMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private ArrayList<LatLng> latLngs = new ArrayList<>();
    private ArrayList<String> ket = new ArrayList<>();
    private ArrayList<String> loc = new ArrayList<>();
    private MarkerOptions options = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftaroutletmaps);
        ButterKnife.bind(this);
        setupToolbar();
        context = this;
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);

        initGetIntent();
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        setMarker();
        if(extralocation!=null){
            String pisah[] = extralocation.split("%");
            Double lat = Double.parseDouble(pisah[0]);
            Double lon = Double.parseDouble(pisah[1]);
            LatLng position = new LatLng(lat,lon);
            Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(extraname).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
            float zoomLevel = 0.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));
        }

    }

   private void setMarker() {
       service = apiUtils.getAPIService();

       service.outletlistRequest(token, extrafranchiseid)
               .enqueue(new Callback<OutletResponse>() {
                   @Override
                   public void onResponse(Call<OutletResponse> call, Response<OutletResponse> response) {
                       List<Outlet> outletList = response.body().getOutlets();
                       int length = outletList.size();
                       for(int i = 0; i<length; i++){
                           String fullAlamat = outletList.get(i).getAddress();
                           String[] pisah = fullAlamat.split("%");
                           String alamat = pisah[0];
                           Double longitude = Double.parseDouble(pisah[2]);
                           Double latitude = Double.parseDouble(pisah[1]);
                           latLngs.add(new LatLng(latitude, longitude));
                           loc.add(alamat);
                           ket.add(extraname);
                       }
                       Log.d("CHECK",String.valueOf(latLngs.size()));
                       for (int i = 0;i <latLngs.size();i++){
                           Marker marker = mMap.addMarker(new MarkerOptions().
                                   position(latLngs.get(i)).
                                   title(loc.get(i)).
                                   snippet(ket.get(i)));
                           marker.setTag(0);
                       }
                   }
                   @Override
                   public void onFailure(Call<OutletResponse> call, Throwable t) {
                       Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                       Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();

                   }
               });

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Daftaroutletmaps.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISE_ID)){
            extraname = getintent.getExtras().getString(NAME);
            extrafranchiseid = getintent.getExtras().getString(FRANCHISE_ID);
            extralocation = getintent.getExtras().getString(LOCATION);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
}

