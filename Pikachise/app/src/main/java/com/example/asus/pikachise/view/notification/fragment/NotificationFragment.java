package com.example.asus.pikachise.view.notification.fragment;


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
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Notification;
import com.example.asus.pikachise.model.NotificationResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.notification.adapter.NotificationRVAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.asus.pikachise.view.notification.adapter.NotificationRVAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.notification_recyclervidw) RecyclerView recyclerView;
    @BindView(R.id.notification_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    private List<Notification> notificationList = new ArrayList<>();
    private NotificationRVAdapter notificationRVAdapter;
    Context context;

    String token;
    apiService service;
    SessionManagement session;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        context = container.getContext();

        session = new SessionManagement(context);
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();

        ButterKnife.bind(this,view);
        notificationRVAdapter = new NotificationRVAdapter(notificationList,context,token);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationRVAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                GET_notificationAPI();
            }
        });

        return view;
    }

    private void GET_notificationAPI() {
        service.myNotificationRequest(token)
                .enqueue(new Callback<NotificationResponse>() {
                    @Override
                    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            List<Notification> myNotification = response.body().getNotifications();

                            recyclerView.setAdapter(new NotificationRVAdapter(myNotification, context,token));
                            recyclerView.smoothScrollToPosition(0);
                            if(swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();

                    }
                });

        notificationRVAdapter.notifyItemRangeRemoved(0,notificationList.size());
        notificationList.clear();
    }

    @Override
    public void onRefresh() {

        notificationRVAdapter.notifyItemRangeRemoved(0,notificationList.size());
        notificationList.clear();
        GET_notificationAPI();

    }
}
