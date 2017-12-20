package com.example.asus.pikachise.view.myfranchise.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Event;
import com.example.asus.pikachise.view.event.activity.DetailEvent;
import com.example.asus.pikachise.view.myfranchise.activity.AddEvent;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 18/12/2017.
 */

public class MyEventRVAdapter extends RecyclerView.Adapter<MyEventRVAdapter.ViewHolder> {

    private final static String NAME = "NAME";
    private final static String ID = "ID";
    private final static String IMAGE = "IMAGE";
    private final static String FRANCHISE_ID = "FRANCHISE_ID";
    private final static String TIME = "TIME";
    private final static String DATE = "DATE";
    private final static String PRICE = "PRICE";
    private final static String DETAIL = "DETAIL";
    private final static String VENUE = "VENUE";
    private final static String EVENT = "EVENT";

    private List<Event> listevent;
    private LayoutInflater inflater;
    Context context;
    private String token;
    public MyEventRVAdapter(List<Event> listevent, Context c, String token){
        this.inflater = LayoutInflater.from(c);
        this.listevent = listevent;
        this.token = token;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_events, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event current = listevent.get(position);
        holder.judul.setText(current.getName());
        holder.alamat.setText(current.getVenue());
        holder.tanggal.setText(current.getDate());
        holder.time.setText(current.getTime());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(current.getPrice())));
        holder.description.setText(current.getDetail());
        Picasso.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.logo404)
                .into(holder.gambar);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEvent.class);
                intent.putExtra(ID, current.getId());
                intent.putExtra(FRANCHISE_ID, current.getFranchiseId());
                intent.putExtra(NAME, current.getName());
                intent.putExtra(DATE, current.getDate());
                intent.putExtra(TIME, current.getTime());
                intent.putExtra(VENUE, current.getVenue());
                intent.putExtra(DETAIL, current.getDetail());
                intent.putExtra(IMAGE, current.getImage());
                intent.putExtra(PRICE, current.getPrice());
                intent.putExtra(EVENT, "edit");
                Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listevent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemmyevents_alamat) TextView alamat;
        @BindView(R.id.itemmyevents_container) LinearLayout container;
        @BindView(R.id.itemmyevents_description) TextView description;
        @BindView(R.id.itemmyevents_edit) ImageView edit;
        @BindView(R.id.itemmyevents_Gambar) ImageView gambar;
        @BindView(R.id.itemmyevents_judul) TextView judul;
        @BindView(R.id.itemmyevents_price) TextView price;
        @BindView(R.id.itemmyevents_tanggal) TextView tanggal;
        @BindView(R.id.itemmyevents_time) TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
