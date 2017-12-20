package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
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
    private final static String ACTIVITY = "ACTIVITY";
    private final static String EVENT = "EVENT";
    private final static String ID = "ID";
    private final static String IMAGE = "IMAGE";
    private final static String TIME = "TIME";
    private final static String DATE = "DATE";
    private final static String PRICE = "PRICE";
    private final static String VENUE = "VENUE";

    @BindView(R.id.addevent_address) TextInputEditText address;
    @BindView(R.id.addevent_banner) ImageView banner;
    @BindView(R.id.addevent_container) LinearLayout container;
    @BindView(R.id.addevent_detail) TextInputEditText detail;
    @BindView(R.id.addevent_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.addevent_layoutdetail) TextInputLayout layoutdetail;
    @BindView(R.id.addevent_layoutname) TextInputLayout layoutname;
    @BindView(R.id.addevent_layoutprice) TextInputLayout layoutprice;
    @BindView(R.id.addevent_name) TextInputEditText name;
    @BindView(R.id.addevent_price) TextInputEditText price;
    @BindView(R.id.addevent_since) LinearLayout since;
    @BindView(R.id.addevent_time) LinearLayout time;
    @BindView(R.id.addevent_toolbar) Toolbar toolbar;
    @BindView(R.id.addevent_tvsince) TextView tvsince;
    @BindView(R.id.addevent_tvtime) TextView tvtime;

    private apiService service;
    private String mediaPathBanner;
    private ProgressDialog progressDialog;
    private PermissionsChecker checker;
    private String choosencategory, choosentype, choosencountry, token;
    private Drawable logodrawable, bannerdrawable;
    private AlertDialog dialog;
    private Context context;
    SessionManagement session;

    private String extraname, extralogo, extrabanner, extraemail, extrauserid,
            extrafranchiseid, extracategory, extratype, extraestablishsince,
            extrainvesment, extrafranchisefee, extrawebsite, extraaddress,
            extralocation, extraphonenumber, extradetail, extraevent, extraeventid, extra_event_image, extra_event_time, extra_event_date, extra_event_price, extra_event_venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initObject();
        getactivityIntent();
        if(extraevent.equals("edit"))
            initsetData();
        setupToolbar();
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload();
            }
        });
        since.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvtime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        price.addTextChangedListener(new FinancialTextWatcher(price));

    }
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        progressDialog = new ProgressDialog(this);
        bannerdrawable = getResources().getDrawable(R.drawable.picture);
        banner.setImageDrawable(bannerdrawable);
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();
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
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anothertoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.anothertoolbar_save) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }
    public void datePicker(View view){

        eventDatepickerFragment fragment = new eventDatepickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }
    public static class eventDatepickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)
                    getActivity(), year, month, day);
            return pickerDialog;
        }
    }
    private void setDate(final Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String hasil = format.format(calendar.getTime());

//        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
//        tvsince.setText(dateFormat.format(calendar.getTime()));
        tvsince.setText(hasil);
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
    private void initupload(){
        if(isStoragePermissionGranted()){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 0);
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
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
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
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save changes ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (extraevent.equals("add"))
                    addEvent();
                else
                    editEvent();
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
    private void editEvent(){
        layoutname.setErrorEnabled(false);
        layoutaddress.setErrorEnabled(false);
        layoutprice.setErrorEnabled(false);
        layoutdetail.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name of event is required");
            return;
        } else if (TextUtils.isEmpty(tvsince.getText())) {
            Toast.makeText(context, "Date of Event Start is required", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(tvtime.getText())) {
            Toast.makeText(context, "Time of Event Start is required", Toast.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(price.getText())) {
            layoutprice.setErrorEnabled(true);
            layoutprice.setError("Price is required");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(detail.getText())) {
            layoutdetail.setErrorEnabled(true);
            layoutdetail.setError("Address is required");
            return;
        }
        service = apiUtils.getAPIService();

        MediaType text = MediaType.parse("text/plain");
        RequestBody requestToken = RequestBody.create(text, token);

        if(mediaPathBanner!=null){
            File filebanner = new File(mediaPathBanner);
            RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
            MultipartBody.Part bodybanner = MultipartBody.Part.createFormData("image", filebanner.getName(), requestFileBanner);
            service.changeeventimageRequest(requestToken, bodybanner)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code()==401 || response.code()==400){
                                responseAPI401();
                            }
                            else if(response.code() == 200){
                                Log.i("debug", "onResponse: SUCCESS");
                                try{
                                    JSONObject jsonResults = new JSONObject(response.body().string());
                                    if(jsonResults.getString("message").equals("Event Image changed successfully")){
                                        Toast.makeText(context, "Please pull down to refresh", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                        finish();
                                    }else{
                                        String message = jsonResults.getString("message");
                                        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                            else {
                                Log.i("debug", "onResponse: FAILED");
                                Toast.makeText(context, "Whoops something wrong !", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("debug", "onResponse: FAILED");
                            Toast.makeText(context, "Whoops something wrong !", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        service.editEventRequest(token, extraeventid, name.getText().toString(), tvsince.getText().toString(), tvtime.getText().toString(),
                address.getText().toString(), detail.getText().toString(), FinancialTextWatcher.trimCommaOfString(price.getText().toString()))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==401 || response.code()==400){
                            responseAPI401();
                        }
                        else if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Event edited successfully")){
                                    Toast.makeText(context, "Please pull down to refresh", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                    finish();

                                }else{
                                    String message = jsonResults.getString("message");
                                    Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                            Toast.makeText(context, "Whoops something wrong !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void addEvent(){
        layoutname.setErrorEnabled(false);
        layoutaddress.setErrorEnabled(false);
        layoutprice.setErrorEnabled(false);
        layoutdetail.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name of event is required");
            return;
        } else if (TextUtils.isEmpty(tvsince.getText())) {
            Toast.makeText(context, "Date of Event Start is required", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(tvtime.getText())) {
            Toast.makeText(context, "Time of Event Start is required", Toast.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(price.getText())) {
            layoutprice.setErrorEnabled(true);
            layoutprice.setError("Price is required");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(detail.getText())) {
            layoutdetail.setErrorEnabled(true);
            layoutdetail.setError("Address is required");
            return;
        } else if(banner.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        }

        MediaType text = MediaType.parse("text/plain");

        File filebanner = new File(mediaPathBanner);
        RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
        MultipartBody.Part bodybanner = MultipartBody.Part.createFormData("image", filebanner.getName(), requestFileBanner);

        RequestBody requestName = RequestBody.create(text, name.getText().toString());
        RequestBody requestSince = RequestBody.create(text, tvsince.getText().toString());
        RequestBody requestTime = RequestBody.create(text, tvtime.getText().toString());
        RequestBody requestPrice = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(price.getText().toString()));
        RequestBody requestAddress = RequestBody.create(text, address.getText().toString());
        RequestBody requestDetail = RequestBody.create(text, detail.getText().toString());
        RequestBody requestToken = RequestBody.create(text, token);
        RequestBody requestFranchiseId = RequestBody.create(text, extrafranchiseid);
        service = apiUtils.getAPIService();
        service.addEvent(requestToken, requestFranchiseId, requestName, requestSince, requestTime, requestAddress, requestDetail, bodybanner, requestPrice)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Event added successfully")){
                                    Toast.makeText(context, "Success, please pull down to refresh", Toast.LENGTH_LONG).show();
                                    onBackPressed();
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
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getactivityIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISE_ID)){
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
            extraphonenumber = getintent.getExtras().getString(PHONENUMBER);
            extradetail = getintent.getExtras().getString(DETAIL);
            extraevent = getintent.getExtras().getString(EVENT);
            extraeventid = getintent.getExtras().getString(ID);
            extra_event_date = getintent.getExtras().getString(DATE);
            extra_event_image = getintent.getExtras().getString(IMAGE);
            extra_event_price = getintent.getExtras().getString(PRICE);
            extra_event_time = getintent.getExtras().getString(TIME);
            extra_event_venue = getintent.getExtras().getString(VENUE);
         }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void initsetData(){
        Picasso.with(context)
                .load(extra_event_image)
                .into(banner);
        name.setText(extraname);
        tvsince.setText(extra_event_date);
        tvtime.setText(extra_event_time);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        price.setText(formatter.format(Double.parseDouble(extra_event_price)));
        address.setText(extra_event_venue);
        detail.setText(extradetail);
    }
    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void initGoHome(){
        Intent intent1 = new Intent(context, MyFranchiseActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
        finish();
    }

}
