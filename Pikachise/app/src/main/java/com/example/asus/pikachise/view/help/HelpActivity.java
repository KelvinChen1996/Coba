package com.example.asus.pikachise.view.help;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.view.authentication.LoginUser;
import com.example.asus.pikachise.view.authentication.RegisterUser;
import com.example.asus.pikachise.view.home.activity.IntroActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {
    @BindView(R.id.help_btnNext) Button next;
    @BindView(R.id.help_btnPrev) Button prev;
    @BindView(R.id.help_layoutdots) LinearLayout introdotsLayout;
    @BindView(R.id.help_viewpager) ViewPager viewpager;
    private TextView[] dots;
    private int[] layouts;
    HelpAdapter adapter;
    private static final Integer[] images1 = {
            R.drawable.helper11,
            R.drawable.helper12,
            R.drawable.helper13,
            R.drawable.helper14,
            R.drawable.helper15,
            R.drawable.helper16};
    private static final Integer[] images2 = {
            R.drawable.helper21,
            R.drawable.helper22,
            R.drawable.helper23,
            R.drawable.helper24};
    private static final Integer[] images3 = {
            R.drawable.helper31,
            R.drawable.helper32,
            R.drawable.helper33,
            R.drawable.helper34,
            R.drawable.helper35,
            R.drawable.helper36,
            R.drawable.helper37,
            R.drawable.helper38,
            R.drawable.helper39,
            R.drawable.helper40};
    private static final Integer[] images4 = {
            R.drawable.helper41,
            R.drawable.helper42,
            R.drawable.helper43,
            R.drawable.helper44,
            R.drawable.helper45,
            R.drawable.helper46,
            R.drawable.helper47,
            R.drawable.helper48};
    private static final Integer[] images5 = {
            R.drawable.helper51,
            R.drawable.helper52,
            R.drawable.helper53,
            R.drawable.helper54,
            R.drawable.helper55,
            R.drawable.helper56,
            R.drawable.helper57,
            R.drawable.helper58,
            R.drawable.helper59};
    private ArrayList<Integer> imagesArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        Intent getintent = getIntent();
        if(getintent.getExtras().getInt("HELP") == 1){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 2){
            for(int i=0;i<images2.length;i++)
                imagesArray.add(images2[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 3){
            for(int i=0;i<images3.length;i++)
                imagesArray.add(images3[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 4){
            for(int i=0;i<images4.length;i++)
                imagesArray.add(images4[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 5){
            for(int i=0;i<images5.length;i++)
                imagesArray.add(images5[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 6){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 7){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 8){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 9){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 10){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 11){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 12){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }
        else if(getintent.getExtras().getInt("HELP") == 13){
            for(int i=0;i<images1.length;i++)
                imagesArray.add(images1[i]);
        }

        addBottomDots(0);

        changeStatusBarColor();
        initClickListener();


        adapter = new HelpAdapter(this, imagesArray);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private void initClickListener(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(-1);
                if (current < imagesArray.size()) {
                    viewpager.setCurrentItem(current);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < imagesArray.size()) {
                    viewpager.setCurrentItem(current);
                }
                else {
                    onBackPressed();
                    finish();
                }
            }
        });
        prev.setVisibility(View.GONE);
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == imagesArray.size() - 1) {
//                next.setVisibility(View.GONE);
                next.setText("Finish");
            }
            else if (position == 0){
                prev.setVisibility(View.GONE);
            }
            else {
                next.setText(getString(R.string.next));
                prev.setText(getString(R.string.prev));
                prev.setTextSize(40);
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void addBottomDots(int currentPage){
        dots = new TextView[imagesArray.size()];
        introdotsLayout.removeAllViews();
        for(int i = 0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey));
            introdotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
//            dots[currentPage].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dots[currentPage].getLayoutParams();
//            params.setMargins(0, 0, 0, 10);
            dots[currentPage].setPadding(0, 18, 0, 0);
            dots[currentPage].setTextSize(20);
            dots[currentPage].setText(Html.fromHtml("&#9670;"));
            dots[currentPage].setTextColor(getResources().getColor(R.color.black));
        }
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private int getItem(int i) {
        return viewpager.getCurrentItem() + i;
    }
}
