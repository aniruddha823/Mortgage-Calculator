package com.example.aniruddhaprabhu.mortgagecalculator;

/**
 * Created by aniruddhaprabhu on 4/23/18.
 */

public class Mortgage {
    private double loan;
    private double rate;
    private int months;

    private double top;
    private double bottom;

    private double monthly;
    private double total;
    private double interest;

    protected Mortgage(double prin, double dp, double apr, int years){
        loan = prin - dp;
        rate = apr / 100 / 12;
        months = years * 12;
    }

    protected double calculate(){
        top = loan * rate * Math.pow(rate + 1, months);
        bottom = Math.pow(rate + 1, months);

        monthly = top / (bottom - 1);
        return monthly;
    }

    protected double totInterest(){
        total = monthly * months;
        interest = total - loan;

        return interest;
    }
}
