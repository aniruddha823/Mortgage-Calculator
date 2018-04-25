package com.example.aniruddhaprabhu.mortgagecalculator;

/**
 * Created by aniruddhaprabhu on 4/23/18.
 */

import java.text.DecimalFormat;

public class Mortgage {
    private double principle;
    private double loan;
    private double rate;
    private double ptRate;
    private int months;
    private String beginDate;

    private double top;
    private double bottom;

    private double monthly;
    private double total;
    private double totalPropertyTax;
    private double interest;
    private String endDate;

    protected Mortgage(double prin, double dp, double apr, double ptr, int years, String start){
        principle = prin;
        loan = prin - dp;
        rate = apr / 100 / 12;
        ptRate = ptr / 100 / 12;
        months = years * 12;
        beginDate = start;
    }

    protected double calculateMonthly(){
        top = loan * rate * Math.pow(rate + 1, months);
        bottom = Math.pow(rate + 1, months);

        monthly = top / (bottom - 1);
        monthly += (ptRate * principle);

        monthly = roundTwoDecimals(monthly);
        return monthly;
    }

    protected double calculateInterest(){
        monthly -= (ptRate * principle);
        total = monthly * months;
        interest = total - loan;

        interest = roundTwoDecimals(interest);
        return interest;
    }

    protected double calculatePropertyTax(){
        totalPropertyTax = ptRate * principle * months;

        totalPropertyTax = roundTwoDecimals(totalPropertyTax);
        return totalPropertyTax;
    }

    protected String calculateEndDate(){
        String[] date = beginDate.split("/");

        int startMonth = Integer.parseInt(date[0]);
        int startYear = Integer.parseInt(date[2]);
        int endMonth = (startMonth + months) % 12;
        int endYear = startYear + (months / 12);

        endDate = endMonth + "/" + date[1] + "/" + endYear;
        return endDate;
    }

    public double roundTwoDecimals(double d) {
        DecimalFormat twoDec = new DecimalFormat("#.##");
        return Double.valueOf(twoDec.format(d));
    }

}
