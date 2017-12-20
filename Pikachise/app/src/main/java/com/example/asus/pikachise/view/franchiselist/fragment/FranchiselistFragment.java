package com.example.asus.pikachise.view.franchiselist.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.view.franchiselist.listall.AllCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiselistFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabs;
    private final static String INTENT_CATEGORY = "INTENT_CATEGORY";
    @BindView(R.id.franchiselist_all) CardView all;
    @BindView(R.id.franchiselist_automotive) CardView automotive;
    @BindView(R.id.franchiselist_beauty) CardView beauty;
    @BindView(R.id.franchiselist_cleaning) CardView cleaning;
    @BindView(R.id.franchiselist_education) CardView education;
    @BindView(R.id.franchiselist_food) CardView food;
    @BindView(R.id.franchiselist_hotel) CardView hotel;
    @BindView(R.id.franchiselist_maintenance) CardView maintenance;
    @BindView(R.id.franchiselist_medic) CardView medic;
    @BindView(R.id.franchiselist_pet) CardView pet;
    @BindView(R.id.franchiselist_retail) CardView retail;
    @BindView(R.id.franchiselist_tech) CardView tech;
    @BindView(R.id.franchiselist_travel) CardView travel;


    public FranchiselistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_franchiselist, container, false);
        ButterKnife.bind(this,view);
        initClick();
        return view;
    }
    private void initClick(){
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "ALL");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        automotive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "AUTOMOTIVE");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "BEAUTY");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "CLEANING");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "EDUCATION");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "FOOD");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "HOTEL");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "MAINTENANCE");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        medic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "MEDIC");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "PET");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "RETAIL");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "TECH");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllCategory.class);
                i.putExtra(INTENT_CATEGORY, "TRAVEL");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(),R.anim.slideright, R.anim.fadeout).toBundle();
                getActivity().startActivity(i, bundle);
            }
        });
    }
    
}
