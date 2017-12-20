package com.example.asus.pikachise.view.search.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.model.ListFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.example.asus.pikachise.view.search.adapter.SearchRVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener{
    @BindView(R.id.search_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.search_toolbar) Toolbar toolbar;
    @BindView(R.id.search_textview) TextView textView;
    @BindView(R.id.search_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private SearchRVAdapter adapter;

    private List<ListFranchise> listFranchise;
    private List<ListFranchise> newList;

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;

    String choosencategory, choose_filter_by, choose_price_min,choose_price_max, choose_order;
    Spinner category;
    Boolean checked;
    RadioButton r1,r2, radioButton;
    RadioGroup radioGroup;

    private static TextView text;
    private static SeekBar seekBar;

    private final static String INTENT_CATEGORY = "INTENT_CATEGORY";
    private String extraintentcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        choose_price_min = "0";
        choose_price_max= "10000000000";
        ButterKnife.bind(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGoHome();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                initObject();
            }
        });

        initObject();



//        setupToolbar();

    }



    private void initObject(){
        listFranchise = new ArrayList<>();
        adapter = new SearchRVAdapter(listFranchise, context);

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        Log.i("debug", "error di object" + listFranchise.size());
        allFranchiseAPI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
    }

    @SuppressLint("StaticFieldLeak")
    private void allFranchiseAPI(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                listFranchise.clear();
                service.franchiselistRquest(token)
                        .enqueue(new Callback<ListFranchiseResponse>() {
                            @Override
                            public void onResponse(Call<ListFranchiseResponse> call, Response<ListFranchiseResponse> response) {
                                if(response.code() == 401){
                                    responseAPI401();
                                    swipeRefreshLayout.setRefreshing(false);

                                }else if(response.body().getListFranchises() == null){
                                    responseAPI200null();
                                    swipeRefreshLayout.setRefreshing(false);

                                }
                                else{
                                    responseAPI200(response);
                                    swipeRefreshLayout.setRefreshing(false);

                                }
                            }
                            @Override
                            public void onFailure(Call<ListFranchiseResponse> call, Throwable t) {
                                responseAPIfailure(t);
                            }
                        });

                return null;
            }
        }.execute();
    }
    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void responseAPI200(Response<ListFranchiseResponse> response){
        List<ListFranchise> listFranchises = response.body().getListFranchises();
        listFranchise = listFranchises;
        recyclerView.setAdapter(new SearchRVAdapter(listFranchises, context));
        recyclerView.smoothScrollToPosition(0);
    }
    private void responseAPI200null(){
        textView.setVisibility(View.VISIBLE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        newList = new ArrayList<>();
        for (ListFranchise daftarFranchise : listFranchise)
        {
            String name = daftarFranchise.getName().toLowerCase();
            if(name.contains(newText)){
                newList.add(daftarFranchise);
            }
        }
        adapter.setFilter(newList);
        recyclerView.setAdapter(adapter);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.search);
        final MenuItem search = menu.findItem(R.id.search);
        initfilter(menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Cari Nama Franchise");
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void initfilter(Menu menu) {
        final MenuItem filter = menu.findItem(R.id.filter);
        filter.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
                        View view = LayoutInflater.from(Search.this).inflate(R.layout.filter_itemfranchise, null);
                        initSeekbar(view);
                        initCategory(view);
                        initTitleAndShowBuilder(builder, view);
                        return false;
                    }
                }
        );
    }

    private void initTitleAndShowBuilder(AlertDialog.Builder builder, final View view) {
        TextView title = (TextView) view.findViewById(R.id.title);
        radioGroup = (RadioGroup) view.findViewById(R.id.rb_group);
        title.setText("Filter Franchise");
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int selected_id = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selected_id);
                choose_filter_by = radioButton.getText().toString();

                if (choose_filter_by.equals("A-Z"))
                    choose_order = "asc";
                else
                    choose_order ="desc";

                String tmp=choosencategory+ " "+ choose_filter_by +" "+ choose_price_min+ " \n" +choose_price_max + choose_order;
//                initFilterServiceByAlphabet(choose_order);
                initFilterFranchise(choose_order,choose_price_min,choose_price_max,choosencategory);
//                Toast.makeText(Search.this, tmp, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(Search.this, "Never Mind!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        builder.show();
    }

    @SuppressLint("StaticFieldLeak")
    private void initFilterFranchise(final String choose_order, final String choose_price_min, final String choose_price_max, final String choosecategory) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                listFranchise.clear();
                service.List_by_Price_Range(token,choose_order,choose_price_min,choose_price_max,choosecategory)
                        .enqueue(new Callback<ListFranchiseResponse>() {
                            @Override
                            public void onResponse(Call<ListFranchiseResponse> call, Response<ListFranchiseResponse> response) {
                                if(response.code() == 401){
                                    responseAPI401();
                                    swipeRefreshLayout.setRefreshing(false);

                                }else if(response.body().getListFranchises() == null){
                                    responseAPI200null();
                                    swipeRefreshLayout.setRefreshing(false);

                                }
                                else{
                                    responseAPI200(response);
                                    swipeRefreshLayout.setRefreshing(false);

                                }
                            }
                            @Override
                            public void onFailure(Call<ListFranchiseResponse> call, Throwable t) {
                                responseAPIfailure(t);
                            }
                        });

                return null;
            }
        }.execute();
    }


    private void initCategory(View view) {
        category = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(view.getContext(), R.array.FilterFranchise, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapterCategory);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosencategory = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choosencategory = (String) parent.getItemAtPosition(0);

            }
        });
    }

    private void initSeekbar(View view) {
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        text = (TextView) view.findViewById(R.id.nol);
//        textView.setText(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
//                        choose_price_min = String.valueOf(progress_value);
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
//                        invesment.setText("IDR" + formatter.format(Double.parseDouble(extrainvesment)));
                        choose_price_min = String.valueOf(progress*10000000);
                        text.setText(formatter.format(Double.parseDouble(String.valueOf(progress*10000000))));
                        Log.i("Seekbar in Progress", String.valueOf(seekBar.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Log.i("Start Tracking", String.valueOf(seekBar.getProgress()));

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Log.i("Stop Tracking", String.valueOf(seekBar.getProgress()));

                    }
                }
        );

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void initGoHome(){
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
        finish();
    }
}

