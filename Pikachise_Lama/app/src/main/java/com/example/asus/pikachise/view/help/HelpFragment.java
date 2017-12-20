package com.example.asus.pikachise.view.help;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.view.event.activity.DetailEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {
    @BindView(R.id.help_1) CardView help1;
    @BindView(R.id.help_2) CardView help2;
    @BindView(R.id.help_3) CardView help3;
    @BindView(R.id.help_4) CardView help4;
    @BindView(R.id.help_5) CardView help5;
    @BindView(R.id.help_6) CardView help6;
    @BindView(R.id.help_7) CardView help7;
    @BindView(R.id.help_8) CardView help8;
    @BindView(R.id.help_9) CardView help9;
    @BindView(R.id.help_10) CardView help10;
    @BindView(R.id.help_11) CardView help11;
    @BindView(R.id.help_12) CardView help12;
    @BindView(R.id.help_13) CardView help13;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this,view);
        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 1);
                getActivity().startActivity(intent);
            }
        });
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 2);
                getActivity().startActivity(intent);
            }
        });
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 3);
                getActivity().startActivity(intent);
            }
        });
        help4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 4);
                getActivity().startActivity(intent);
            }
        });
        help5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 5);
                getActivity().startActivity(intent);
            }
        });
        help6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 6);
                getActivity().startActivity(intent);
            }
        });
        help7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 7);
                getActivity().startActivity(intent);
            }
        });
        help8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 8);
                getActivity().startActivity(intent);
            }
        });
        help9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 9);
                getActivity().startActivity(intent);
            }
        });
        help10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 10);
                getActivity().startActivity(intent);
            }
        });
        help11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 11);
                getActivity().startActivity(intent);
            }
        });
        help12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 12);
                getActivity().startActivity(intent);
            }
        });
        help13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("HELP", 13);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}
