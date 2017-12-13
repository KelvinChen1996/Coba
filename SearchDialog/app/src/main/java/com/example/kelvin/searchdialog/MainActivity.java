package com.example.kelvin.searchdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button btn;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn_dialog);
        textView = (TextView)  findViewById(R.id.txt_view);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SearchDialog();
                    }
                }
        );

        //reference of button added in the main layout
        Button button = (Button) findViewById(R.id.button);

//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
////        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array)
//        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
//                this, R.array.MetricUnitsArray, android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.MetricUnitsArray, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener((OnItemSelectedListener) this);




//
//        String[] items = new String[] { "A", "B", "C" };
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, items); // Use String[] , Dynamic
//
//        dynamicSpinner.setAdapter(adapter);
//
//        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                Toast.makeText(getApplicationContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });


        //setting click event listener to button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_layout, null);

                TextView title = (TextView) view.findViewById(R.id.title);

                title.setText("Hello There!");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Never Mind!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(view);
                builder.show();

            }
        });
    }

    private void SearchDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        final Spinner _spinner = (Spinner) findViewById(R.id.spinner1);
        seek.setMax(1000000);
        seek.setKeyProgressIncrement(1);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Please Select Into Your Desired Brightness ");
        popDialog.setView(seek);



        seek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        textView.setText("Value of : " + i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.MetricUnitsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinner.setAdapter(adapter);


        // Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        popDialog.create();
        popDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
