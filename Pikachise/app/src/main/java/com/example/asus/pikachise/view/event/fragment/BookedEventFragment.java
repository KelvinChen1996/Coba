package com.example.asus.pikachise.view.event.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Event;
import com.example.asus.pikachise.model.MyEvent;
import com.example.asus.pikachise.model.MyEventResponse;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.event.adapter.BookedEventRVAdapter;
import com.example.asus.pikachise.view.myfranchise.adapter.MyFranchiseRVAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookedEventFragment extends Fragment {
    @BindView(R.id.bookedevent_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.bookedevent_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.bookedevent_textview) TextView textView;

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token, email, image, name ,id;
    private ProgressDialog progressDialog;
    private List<MyEvent> myEventList;
    private BookedEventRVAdapter adapter;

    public BookedEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booked_event, container, false);
        iniObject(view);
        initDataSession();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        return view;
    }
    private void iniObject(View view){
        ButterKnife.bind(this, view);
        context = getContext();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        progressDialog = new ProgressDialog(context);
        session = new SessionManagement(getContext());
        service = apiUtils.getAPIService();
    }
    private void initRefresh(){
        myEventList = new ArrayList<>();
        adapter = new BookedEventRVAdapter(myEventList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getBookedEvent();
    }
    private void getBookedEvent(){
        service.getBookEvent(token)
                .enqueue(new Callback<MyEventResponse>() {
                    @Override
                    public void onResponse(Call<MyEventResponse> call, Response<MyEventResponse> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                            getActivity().finish();
                        }else if(response.code() == 200){
                            if(response.body().getEvents().toString().equals("[]")){
                                textView.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            else{
                                List<MyEvent> myEvents = response.body().getEvents();
                                recyclerView.setAdapter(new BookedEventRVAdapter(myEvents, context));
                                recyclerView.smoothScrollToPosition(0);
                                if(swipeRefreshLayout.isRefreshing()){
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                        else{
                            Toast.makeText(context, "WTF IS HAPPENING AT BOOKEDEVENT", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyEventResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
}
