package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.FinancialTextWatcher;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.AuthActivity;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMyFranchiseEdit extends AppCompatActivity {

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_LOGO = 1;
    private static final int SELECT_BANNER = 2;

    private final static String NAME = "NAME";
    private final static String LOGO = "LOGO";
    private final static String BANNER = "BANNER";
    private final static String EMAIL = "EMAIL";
    private final static String USER_ID = "USER_ID";
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
    private final static String LATITUDE = "LATITUDE";
    private final static String LONGITUDE = "LONGITUDE";

    @BindView(R.id.editdetailmyfranchise_address) TextInputEditText address;
    @BindView(R.id.editdetailmyfranchise_banner) ImageView banner;
    @BindView(R.id.editdetailmyfranchise_category) Spinner category;
    @BindView(R.id.editdetailmyfranchise_email) TextInputEditText email;
    @BindView(R.id.editdetailmyfranchise_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.editdetailmyfranchise_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.editdetailmyfranchise_layoutname) TextInputLayout layoutname;
    @BindView(R.id.editdetailmyfranchise_layoutphonenumber) TextInputLayout layoutphone;
    @BindView(R.id.editdetailmyfranchise_layoutwebsite) TextInputLayout layoutwebsite;
    @BindView(R.id.editdetailmyfranchise_logo) CircleImageView logo;
    @BindView(R.id.editdetailmyfranchise_name) TextInputEditText name;
    @BindView(R.id.editdetailmyfranchise_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.editdetailmyfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.editdetailmyfranchise_type) Spinner type;
    @BindView(R.id.editdetailmyfranchise_website) TextInputEditText website;
    @BindView(R.id.editdetailmyfranchise_container) LinearLayout container;
    @BindView(R.id.editdetailmyfranchise_since) LinearLayout sinces;
    @BindView(R.id.editdetailmyfranchise_tvsince) TextView tvsince;
    @BindView(R.id.editdetailmyfranchise_franchisefee) TextInputEditText franchisefee;
    @BindView(R.id.editdetailmyfranchise_layoutfranchisefee) TextInputLayout layoutfranchisefee;
    @BindView(R.id.editdetailmyfranchise_layoutdetail) TextInputLayout layoutdetail;
    @BindView(R.id.editdetailmyfranchise_detail) TextInputEditText detail;
    @BindView(R.id.editdetailmyfranchise_layoutinvestment) TextInputLayout layoutinvestmens;
    @BindView(R.id.editdetailmyfranchise_investment) TextInputEditText investments;
    @BindView(R.id.editdetailmyfranchise_openfromgooglemaps) LinearLayout openfromgooglemaps;
    @BindView(R.id.editdetailmyfranchise_layoutlongitude) TextInputLayout layoutllongitude;
    @BindView(R.id.editdetailmyfranchise_layoutlatitude) TextInputLayout layoutlatitude;
    @BindView(R.id.editdetailmyfranchise_latitude) TextInputEditText latitude;
    @BindView(R.id.editdetailmyfranchise_longitude) TextInputEditText longitude;

    private String extraname, extralogo, extrabanner, extraemail,
            extrauserid, extrafranchiseid, extracategory, extratype,
            extraestablishsince, extrainvesment, extrafranchisefee,
            extrawebsite, extraaddress, extralocation, extraphonenumber,
            extradetail, extralongitude, extralatitude, lat, lo;
    private String choosencategory, choosentype, choosencountry, token;
    private String mediaPathLogo,mediaPathBanner;
    private ProgressDialog progressDialog;
    SessionManagement session;
    Context context;
    private AlertDialog dialog;
    private apiService service;

    private Drawable logodrawable, bannerdrawable;

    private PermissionsChecker checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_my_franchise);
        initObject();
        setupToolbar();
        initGetIntent();
        initDetail();

        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);

        franchisefee.addTextChangedListener(new FinancialTextWatcher(franchisefee));
        investments.addTextChangedListener(new FinancialTextWatcher(investments));
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_LOGO);
            }
        });
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_BANNER);
            }
        });
        initSpinner();
        openfromgooglemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, getFromMaps.class);
                i.putExtra(LATITUDE, extralatitude);
                i.putExtra(LONGITUDE, extralongitude);
                startActivityForResult(i, 1);
            }
        });


    }
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        progressDialog = new ProgressDialog(this);
        logodrawable = getResources().getDrawable(R.drawable.addcamera);
        logo.setImageDrawable(logodrawable);
        bannerdrawable = getResources().getDrawable(R.drawable.addpicture);
        banner.setImageDrawable(bannerdrawable);
        session = new SessionManagement(getApplicationContext());
    }
    private void initSpinner(){
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosencategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosentype = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                choosencountry = (String) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.x1);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MyFranchiseActivity.class));
                finish();
            }
        });
    }
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISEFEE)){
            extraname = getintent.getExtras().getString(NAME);
            extralogo = getintent.getExtras().getString(LOGO);
            extrabanner = getintent.getExtras().getString(BANNER);
            extraemail = getintent.getExtras().getString(EMAIL);
            extrauserid = getintent.getExtras().getString(USER_ID);
            extrafranchiseid = getintent.getExtras().getString(FRANCHISE_ID);
            extracategory = getintent.getExtras().getString(CATEGORY);
            extratype = getintent.getExtras().getString(TYPE);
            extraestablishsince = getintent.getExtras().getString(ESTABLISHSINCE);
            extrainvesment = getintent.getExtras().getString(INVESTMENT);
            extrafranchisefee = getintent.getExtras().getString(FRANCHISEFEE);
            extrawebsite = getintent.getExtras().getString(WEBSITE);
            extraaddress = getintent.getExtras().getString(ADDRESS);
            extralocation = getintent.getExtras().getString(LOCATION);

            String[] pisah = extralocation.split("%");
            extralongitude = pisah[1];
            extralatitude = pisah[0];
            extraphonenumber = getintent.getExtras().getString(PHONENUMBER);
            extradetail = getintent.getExtras().getString(DETAIL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void initDetail(){
        Picasso.with(context)
                .load(extrabanner)
                .placeholder(R.drawable.logo404)
                .into(banner);
        Picasso.with(context)
                .load(extralogo)
                .placeholder(R.drawable.logo404)
                .into(logo);
        name.setText(extraname);
        String compareCategory = extracategory;
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.categoryspinner, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapterCategory);
        if (!compareCategory.equals(null)) {
            int spinnerPosition = adapterCategory.getPosition(compareCategory);
            category.setSelection(spinnerPosition);
        }
        String compareType = extratype;
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.typespinner, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);
        if (!compareType.equals(null)) {
            int spinnerPosition = adapterType.getPosition(compareType);
            type.setSelection(spinnerPosition);
        }
        latitude.setText(extralatitude);
        longitude.setText(extralongitude);
        tvsince.setText(extraestablishsince);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        investments.setText(formatter.format(Double.parseDouble(extrainvesment)));
        franchisefee.setText(formatter.format(Double.parseDouble(extrafranchisefee)));
        address.setText(extraaddress);
        String compareCountry = extralocation;
        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this, R.array.countryspinner, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        country.setAdapter(adapterCountry);
//        if (!compareCountry.equals(null)) {
//            int spinnerPosition = adapterCountry.getPosition(compareCountry);
//            country.setSelection(spinnerPosition);
//        }
        phonenumber.setText(extraphonenumber);
        email.setText(extraemail);
        website.setText(extrawebsite);
        detail.setText(extradetail);
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return false;
            }
        }
        else {
            return true;
        }
    }
    private void initupload(int RequestCode){
        if(isStoragePermissionGranted()){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, RequestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                if(resultCode == RESULT_OK) {
                    lo = data.getStringExtra(LONGITUDE);
                    lat = data.getStringExtra(LATITUDE);
                    latitude.setText(lat);
                    longitude.setText(lo);
                }
            }
            if (requestCode == SELECT_LOGO && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toast.makeText(this, "Unable to pick image", Toast.LENGTH_LONG).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathLogo = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathLogo))
                            .into(logo);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_BANNER && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toast.makeText(this, "Unable to pick image", Toast.LENGTH_LONG).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathBanner = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathBanner))
                            .into(banner);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){}
    }
    private void startPermissionsActivity(String[] permission) {
        PermissionActivity.startActivityForResult(this, SELECT_BANNER, permission);
        PermissionActivity.startActivityForResult(this, SELECT_LOGO, permission);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anothertoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.anothertoolbar_save) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save changes ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postDataToAPI();
                startActivity(new Intent(context, MyFranchiseActivity.class));
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void postDataToAPI(){
        layoutaddress.setErrorEnabled(false);
        layoutemail.setErrorEnabled(false);
        layoutname.setErrorEnabled(false);
        layoutphone.setErrorEnabled(false);
        layoutwebsite.setErrorEnabled(false);
        layoutfranchisefee.setErrorEnabled(false);
        layoutinvestmens.setErrorEnabled(false);
        layoutdetail.setErrorEnabled(false);
        layoutlatitude.setErrorEnabled(false);
        layoutllongitude.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name is required");
            return;
        } else if (TextUtils.isEmpty(tvsince.getText())) {
            Toast.makeText(context, "Date of Established is required", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(investments.getText())) {
            layoutinvestmens.setErrorEnabled(true);
            layoutinvestmens.setError("Investment is required");
            return;
        } else if (TextUtils.isEmpty(franchisefee.getText())) {
            layoutfranchisefee.setErrorEnabled(true);
            layoutfranchisefee.setError("Franchisee fee is required");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(latitude.getText())) {
            layoutlatitude.setErrorEnabled(true);
            layoutlatitude.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(longitude.getText())) {
            layoutllongitude.setErrorEnabled(true);
            layoutllongitude.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphone.setErrorEnabled(true);
            layoutphone.setError("Phone number is required");
            return;
        } else if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(email.getText().toString())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is not valid");
            return;
        } else if (TextUtils.isEmpty(website.getText())) {
            layoutwebsite.setErrorEnabled(true);
            layoutwebsite.setError("Website is required");
            return;
        } else if (TextUtils.isEmpty(detail.getText())) {
            layoutdetail.setErrorEnabled(true);
            layoutdetail.setError("Address is required");
            return;
        } else if(logo.getDrawable().equals(logodrawable)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        } else if(banner.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();

        MediaType text = MediaType.parse("text/plain");


//
        String gabung = lat + "%" + lo;

        RequestBody requestName = RequestBody.create(text, name.getText().toString());
        RequestBody requestFranchiseId = RequestBody.create(text, extrafranchiseid);
        RequestBody requestCategory = RequestBody.create(text, choosencategory);
        RequestBody requestType = RequestBody.create(text, choosentype);
        RequestBody requestSince = RequestBody.create(text, tvsince.getText().toString());
        RequestBody requestInvestment = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(investments.getText().toString()));
        RequestBody requestFranchisefee = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(franchisefee.getText().toString()));
        RequestBody requestWebsite = RequestBody.create(text, website.getText().toString());
        RequestBody requestAddress = RequestBody.create(text, address.getText().toString());
        RequestBody requestLocation = RequestBody.create(text, gabung);
        RequestBody requestPhoneNumber = RequestBody.create(text, phonenumber.getText().toString());
        RequestBody requestEmail = RequestBody.create(text, email.getText().toString());
        RequestBody requestAveragerating = RequestBody.create(text, "0");
        RequestBody requestDetail = RequestBody.create(text, detail.getText().toString());
        RequestBody requestToken = RequestBody.create(text, token);
//
        service = apiUtils.getAPIService();
        if(mediaPathBanner != null && mediaPathLogo!=null){
            File filelogo = new File(mediaPathLogo);
            RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filelogo);
            MultipartBody.Part bodylogo = MultipartBody.Part.createFormData("logo", filelogo.getName(), requestFileLogo);

            File filebanner = new File(mediaPathBanner);
            RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
            MultipartBody.Part bodybanner = MultipartBody.Part.createFormData("banner", filebanner.getName(), requestFileBanner);
            service.editfranchiselogobannerRequest(requestToken, bodylogo, bodybanner)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Log.i("debug", "onResponse: SUCCESS");
                                if(response.code()==401){
                                    startActivity(new Intent(context, Error401.class));
                                    finish();
                                }
                                else if(response.code()==200){
                                    try{
                                        JSONObject jsonResults = new JSONObject(response.body().string());
                                        if(jsonResults.getString("message").equals("Franchise edited Successfully")){
                                            String message = jsonResults.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                        }else{
                                            String message = jsonResults.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }

                                else {
                                    Log.i("debug", "onResponse: FAILED");
                                }
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else if(mediaPathLogo != null){
            File filelogo = new File(mediaPathLogo);
            RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filelogo);
            MultipartBody.Part bodylogo = MultipartBody.Part.createFormData("logo", filelogo.getName(), requestFileLogo);
            service.editfranchiselogoRequest(requestToken, bodylogo)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code()==401){
                                startActivity(new Intent(context, Error401.class));
                                finish();
                            }
                            else if(response.code()==200){
                                try{
                                    JSONObject jsonResults = new JSONObject(response.body().string());
                                    if(jsonResults.getString("message").equals("Franchise edited Successfully")){
                                        String message = jsonResults.getString("message");
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    }else{
                                        String message = jsonResults.getString("message");
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }

                            else {
                                Log.i("debug", "onResponse: FAILED");
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else if(mediaPathBanner != null){
            File filebanner = new File(mediaPathBanner);
            RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
            MultipartBody.Part bodybanner = MultipartBody.Part.createFormData("banner", filebanner.getName(), requestFileBanner);
            service.editfranchisebannerRequest(requestToken, bodybanner)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code()==401){
                                startActivity(new Intent(context, Error401.class));
                                finish();
                            }
                            else if(response.code()==200){
                                try{
                                    JSONObject jsonResults = new JSONObject(response.body().string());
                                    if(jsonResults.getString("message").equals("Franchise edited Successfully")){
                                        String message = jsonResults.getString("message");
                                        finish();
                                    }else{
                                        String message = jsonResults.getString("message");
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }

                            else {
                                Log.i("debug", "onResponse: FAILED");
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        service.editfranchiseRequest(requestToken, requestFranchiseId, requestName, requestCategory, requestType, requestSince, requestInvestment, requestFranchisefee, requestWebsite, requestAddress
                ,requestLocation, requestPhoneNumber, requestEmail, requestDetail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("debug", "onResponse: SUCCESS");
                        if(response.code()==401){
                            startActivity(new Intent(context, Error401.class));
                            finish();
                        }
                        else if(response.code()==200){
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Franchise edited Successfully")){
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }else{
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                        else {
                            Log.i("debug", "onResponse: FAILED");
                        }
                        progressDialog.dismiss();
                    }
                }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
