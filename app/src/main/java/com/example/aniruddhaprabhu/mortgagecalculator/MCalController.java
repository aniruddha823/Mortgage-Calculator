package com.example.aniruddhaprabhu.mortgagecalculator;

/**
 * Created by aniruddhaprabhu on 4/23/18.
 */

public class MCalController {

    protected double calculate(double prin, double dp, double apr, int terms){

        double loan = prin - dp;
        double rate = apr / 100 / 12;
        double payments = terms * 12;

        double top = loan * rate * Math.pow(rate + 1, payments);
        double bottom = Math.pow(rate + 1, payments) - 1;

        return top / bottom;
    }
}
