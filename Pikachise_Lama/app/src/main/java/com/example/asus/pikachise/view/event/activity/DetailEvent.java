package com.example.asus.pikachise.view.event.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.EventResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEvent extends AppCompatActivity {

    @BindView(R.id.detailEvent_Alamat) TextView alamat;
    @BindView(R.id.detailEvent_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.detailEvent_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailEvent_Gambar) ImageView gambar;
    @BindView(R.id.detailEvent_Isi) TextView isi;
    @BindView(R.id.detailEvent_Judul) TextView judul;
    @BindView(R.id.detailEvent_Tanggal) TextView tanggal;
    @BindView(R.id.detailEvent_toolbar) Toolbar toolbar;
    @BindView(R.id.detailEvent_Waktu) TextView waktu;
    @BindView(R.id.detailevent_gone) LinearLayout gone;
    @BindView(R.id.detailEvent_price) TextView price;
    @BindView(R.id.detailevent_qrcodeimage) ImageView qrcodeimage;
    @BindView(R.id.detailevent_tvqrcode) TextView tvqrcode;
    private final static String NAME = "NAME";
    private final static String ID = "ID";
    private final static String IMAGE = "IMAGE";
    private final static String FRANCHISE_ID = "FRANCHISE_ID";
    private final static String TIME = "TIME";
    private final static String DATE = "DATE";
    private final static String PRICE = "PRICE";
    private final static String DETAIL = "DETAIL";
    private final static String VENUE = "VENUE";
    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token, extraid, extrafranchiseid, extraname, extradate, extratime, extravenue, extradetail, extraimage, extraprice, extrauserid, extrauseremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        initObject();
        initGetIntent();
        setupToolbar();
        initCollapToolbar();
        initData();
        allowbookAPI();
        gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookevent();
            }
        });
    }
    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
            }
        });
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle(extraname);
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(ID)){
            extrafranchiseid = getintent.getExtras().getString(FRANCHISE_ID);
            extraname = getintent.getExtras().getString(NAME);
            extraid = getintent.getExtras().getString(ID);
            extravenue = getintent.getExtras().getString(VENUE);
            extratime = getintent.getExtras().getString(TIME);
            extraprice = getintent.getExtras().getString(PRICE);
            extraimage = getintent.getExtras().getString(IMAGE);
            extradate = getintent.getExtras().getString(DATE);
            extradetail = getintent.getExtras().getString(DETAIL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void initData(){
        Picasso.with(context)
                .load(extraimage)
                .placeholder(R.drawable.logo404)
                .into(gambar);
        alamat.setText(extravenue);
        judul.setText(extraname);
        waktu.setText(extratime);
        tanggal.setText(extradate);
        isi.setText(extradetail);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        price.setText("IDR " + formatter.format(Double.parseDouble(extraprice)));

    }
    private void allowbookAPI(){
        service.AllowBookEvent(token, extraid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==401 || token == null){
                            startActivity(new Intent(context, Error401.class));
                            finish();
                        }
                        else if(response.code()==200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                Boolean check = jsonResults.getBoolean("allow");
                                if(check){
                                    gone.setVisibility(View.VISIBLE);
                                }else{
                                    gone.setVisibility(View.INVISIBLE);
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void bookevent(){
        service.userRequest(token).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                extrauseremail = jsonResults.getJSONObject("result").getString("email");
                                extrauserid = String.valueOf(jsonResults.getJSONObject("result").getInt("id"));
//                                String amount = "20";
                                String qrcode = extrauseremail + "%" + extraname + "%" + extraprice + "%" +  extrauserid + "%" + extraid;
                                service.BookEvent(token, extraid, qrcode)
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.code()==401 || token == null){
                                                    startActivity(new Intent(context, Error401.class));
                                                    finish();
                                                }
                                                else if(response.code()==200){
                                                    Log.i("debug", "onResponse: SUCCESS");
                                                    try{
                                                        JSONObject jsonResults = new JSONObject(response.body().string());
                                                        Boolean check = jsonResults.getBoolean("success");
                                                        if(check){
                                                            Toast.makeText(context, "Booked, check your booked event", Toast.LENGTH_SHORT).show();
                                                            gone.setVisibility(View.INVISIBLE);
                                                        }else{
                                                            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }catch (JSONException e){
                                                        e.printStackTrace();
                                                    }catch (IOException e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                                                Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
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
