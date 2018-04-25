package com.example.aniruddhaprabhu.mortgagecalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.math.MathUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;
import android.content.Intent;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText principle;
    private TextInputLayout pLayout;
    private TextInputEditText downPayment;
    private TextInputLayout dpLayout;
    private TextInputEditText months;
    private TextInputLayout moLayout;
    private TextInputEditText apr;
    private TextInputLayout aprLayout;
    private TextInputEditText propertyTax;
    private TextInputLayout propTaxLayout;

    private TextInputEditText startDate;

    private TextView error;
    private Button calc;
    private Button reset;
    private Button date;
    private ImageButton info;
    private TextInputEditText moPay;
    private TextInputEditText totint;
    private TextInputEditText endDate;
    private TextInputEditText totalPropertyTax;

    private Mortgage m;
    private Handler h = new Handler();
    private boolean validation;

    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener odsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        error = findViewById(R.id.error);
        principle = findViewById(R.id.principle);
        pLayout = findViewById(R.id.pLayout);
        downPayment = findViewById(R.id.downPayment);
        dpLayout = findViewById(R.id.dpLayout);
        months = findViewById(R.id.months);
        moLayout = findViewById(R.id.moLayout);
        apr = findViewById(R.id.apr);
        aprLayout = findViewById(R.id.aprLayout);
        propertyTax = findViewById(R.id.propertyTax);
        propTaxLayout = findViewById(R.id.propTaxLayout);

        startDate = findViewById(R.id.startDate);
        startDate.setEnabled(false);

        info = findViewById(R.id.btnInfo);
        reset = findViewById(R.id.btnReset);
        calc = findViewById(R.id.btnCalc);
        date = findViewById(R.id.btnDate);

        moPay = findViewById(R.id.moPay);
        moPay.setEnabled(false);
        totint = findViewById(R.id.totalInterest);
        totint.setEnabled(false);
        endDate = findViewById(R.id.endDate);
        endDate.setEnabled(false);
        totalPropertyTax = findViewById(R.id.totalPropertyTax);
        totalPropertyTax.setEnabled(false);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){

                    m = new Mortgage(Double.parseDouble(principle.getText().toString()),
                            Double.parseDouble(downPayment.getText().toString()),
                            Double.parseDouble(apr.getText().toString()),
                            Double.parseDouble(propertyTax.getText().toString()),
                            Integer.parseInt(months.getText().toString()),
                            startDate.getText().toString());


                    moPay.setText(Double.toString(m.calculateMonthly()));
                    totint.setText(Double.toString(m.calculateInterest()));
                    totalPropertyTax.setText(Double.toString(m.calculatePropertyTax()));
                    endDate.setText(m.calculateEndDate());

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                principle.setText("");
                downPayment.setText("");
                months.setText("");
                apr.setText("");
                propertyTax.setText("");
                startDate.setText("");

                moPay.setText("");
                totint.setText("");
                endDate.setText("");
                totalPropertyTax.setText("");
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        odsl, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        odsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = month + "/" + day + "/" + year;
                startDate.setText(date);
            }
        };

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Down payment < Principle" +
                        "\n" + "Period must be 15, 20, 25, 30, or 40 years")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Guidelines");
                alert.show();
            }
        });
    }

    private boolean validate(){

        String test = months.getText().toString();
        String dateField = startDate.getText().toString();

        if(!parseCheck(principle.getText().toString())){
            validation = false;
            error.setText("Error: Enter valid principle.");
            h.postDelayed(new Runnable(){
                public void run(){ error.setText(""); }
            }, 2000);
        }
        else if(!parseCheck(downPayment.getText().toString())){
            validation = false;
            error.setText("Error: Enter valid down payment.");
            h.postDelayed(new Runnable(){
                public void run(){ error.setText(""); }
            }, 2000);
        }
        else if(!parseCheck(apr.getText().toString())){
            validation = false;
            error.setText("Error: Enter valid APR.");
            h.postDelayed(new Runnable(){
                public void run(){ error.setText(""); }
            }, 2000);
        }
        else if(!parseCheck(propertyTax.getText().toString())){
            validation = false;
            error.setText("Error: Enter valid property tax.");
            h.postDelayed(new Runnable(){
                public void run(){ error.setText(""); }
            }, 2000);
        }
        else if(!test.equals("15") && !test.equals("20") && !test.equals("25") &&
                !test.equals("30") && !test.equals("40")){
            validation = false;
            error.setText("Error: Enter a valid loan period. (Check info button)");
            h.postDelayed(new Runnable(){
                public void run(){ error.setText(""); }
            }, 2000);
        }
        else if(dateField.equals("") || dateField.equals(null)){
            validation = false;
            error.setText("Error: Please choose a start date.");
            h.postDelayed(new Runnable(){
                public void run(){ error.setText(""); }
            }, 2000);
        }
        else {
            validation = true;
        }

        return validation;
    }

    private boolean parseCheck(String test){
        boolean result;
        try{
            double d = Double.parseDouble(test);
            result = true;
        } catch(Exception e){
            result = false;
        }
        return result;
    }

}
