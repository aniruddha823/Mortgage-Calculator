package com.example.aniruddhaprabhu.mortgagecalculator;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText principle;
    private TextInputLayout pLayout;
    private TextInputEditText downPayment;
    private TextInputLayout dpLayout;
    private TextInputEditText apr;
    private TextInputLayout aprLayout;
    private TextInputEditText months;
    private TextInputLayout moLayout;

    private Button calc;
    private Button reset;
    private TextInputEditText moPay;
    private TextInputEditText totint;

    private Mortgage m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        principle = findViewById(R.id.principle);
        pLayout = findViewById(R.id.pLayout);
        downPayment = findViewById(R.id.downPayment);
        dpLayout = findViewById(R.id.dpLayout);
        apr = findViewById(R.id.apr);
        aprLayout = findViewById(R.id.aprLayout);
        months = findViewById(R.id.months);
        moLayout = findViewById(R.id.moLayout);
        reset = findViewById(R.id.btnReset);
        calc = findViewById(R.id.btnCalc);
        moPay = findViewById(R.id.moPay);
        moPay.setEnabled(false);
        totint = findViewById(R.id.totalInterest);
        totint.setEnabled(false);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(principle.getText().toString(), downPayment.getText().toString(),
                        apr.getText().toString(), months.getText().toString())){

                    m = new Mortgage(Double.parseDouble(principle.getText().toString()),
                            Double.parseDouble(downPayment.getText().toString()),
                            Double.parseDouble(apr.getText().toString()),
                            Integer.parseInt(months.getText().toString()));


                    moPay.setText(Double.toString(m.calculate()));
                    totint.setText(Double.toString(m.totInterest()));

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                principle.setText("");
                downPayment.setText("");
                apr.setText("");
                months.setText("");
                moPay.setText("");
                totint.setText("");
            }
        });

    }

    private boolean validate(String p, String dp, String apr, String terms){
        boolean result = true;

        if(!valTerms(terms)){
            result = false;
            moLayout.setError("Enter valid terms");
        }

        return result;
    }

    private boolean valTerms(String test){
        boolean result = false;

        if(test.equals("15") || test.equals("20") || test.equals("25") ||
                test.equals("30") || test.equals("40") || test.equals("6")){
            result = true;
        }

        return result;
    }

}
