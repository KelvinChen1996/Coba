package com.example.asus.pikachise.view.event.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Event;
import com.example.asus.pikachise.model.MyEvent;
import com.example.asus.pikachise.view.event.activity.EventQrcode;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 13/12/2017.
 */

public class BookedEventRVAdapter extends RecyclerView.Adapter<BookedEventRVAdapter.EventHolder> {

    private final static String QRCODE = "QRCODE";
    private final static String AMOUNT = "AMOUNT";

    private List<MyEvent> myEventList;
    private LayoutInflater inflater;
    Context context;
    private String token;

    public BookedEventRVAdapter(List<MyEvent> myEventList, Context c){
        this.inflater = LayoutInflater.from(c);
        this.myEventList = myEventList;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_booked_event, parent, false);
        context = parent.getContext();
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final MyEvent current = myEventList.get(position);
        holder.address.setText(current.getVenue());
        holder.date.setText(current.getDate());
        holder.time.setText(current.getTime());
        holder.title.setText(current.getName());
        Picasso.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.logo404)
                .into(holder.image);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventQrcode.class);
                intent.putExtra(QRCODE, current.getQrcode());
                intent.putExtra(AMOUNT, current.getAmount());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myEventList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_booked_event_address) TextView address;
        @BindView(R.id.item_booked_event_container) RelativeLayout container;
        @BindView(R.id.item_booked_event_date) TextView date;
        @BindView(R.id.item_booked_event_image) ImageView image;
        @BindView(R.id.item_booked_event_time) TextView time;
        @BindView(R.id.item_booked_event_title) TextView title;
        public EventHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
