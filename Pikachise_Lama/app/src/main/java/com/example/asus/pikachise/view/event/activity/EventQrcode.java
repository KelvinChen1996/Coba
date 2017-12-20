package com.example.asus.pikachise.view.event.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.view.event.eventbarcode.Barcode;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.example.asus.pikachise.view.myfranchise.activity.MyFranchiseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventQrcode extends AppCompatActivity {
    @BindView(R.id.event_qrcode_qrcode) ImageView qrcode;
    @BindView(R.id.event_qrcode_toolbar) Toolbar toolbar;
    private final static String QRCODE = "QRCODE";
    private final static String AMOUNT = "AMOUNT";
    private String extraqrcode, extraamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_qrcode);
        ButterKnife.bind(this);
        setupToolbar();

        Intent getintent = getIntent();
        if (getintent.hasExtra(QRCODE)){
            extraqrcode = getintent.getExtras().getString(QRCODE);
            extraamount = getintent.getExtras().getString(AMOUNT);
        }
        else{
            Toast.makeText(this, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            String baru = extraqrcode + "%" + extraamount;
            BitMatrix bitMatrix = multiFormatWriter.encode(baru, BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
        finish();
    }

}
