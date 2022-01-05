package com._305.propertyassessment;


import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PropertyAssessment implements  Comparable {


    private String accountNumber;
    private Address address;
    private String hasGarage;
    private Neighbourhood neighbourhood;
    private int assessedValue;
    private Coordinates coordinates;
    private AssessmentClass assessmentClass;


    public PropertyAssessment(String accountNumber, Address address, String hasGarage, Neighbourhood neighbourhood, int assessedValue, Coordinates coordinates, AssessmentClass assessmentClass) {
        this.accountNumber = accountNumber;
        this.address = address;
        this.hasGarage = hasGarage;
        this.neighbourhood = neighbourhood;
        this.assessedValue = assessedValue;
        this.coordinates = coordinates;

        this.assessmentClass = assessmentClass;
    }



    public AssessmentClass getAssessmentClass() {
        return assessmentClass;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public Address getAddress() {
        return address;
    }


    public String getHasGarage() {
        return hasGarage;
    }


    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }



    public int getAssessedValue() {
        return assessedValue;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }



    public String getValueCurrencyForm() {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(0);
        String valueCurrencyForm = formatter.format(assessedValue);

        return valueCurrencyForm;
    }






    /*Will be used on a Property Assessment list sort on lowest assessment values from low to high*/

    @Override
    public int compareTo(Object obj) {
        int compareAsessedVal =((PropertyAssessment)obj).getAssessedValue();

        return  this.assessedValue - compareAsessedVal;
    }

    @Override
    public String toString() {

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMinimumFractionDigits(0);



        return
                "Account Number = " + accountNumber +"\n"+
                "Address = " + address + "\n" +
                "Assessed value = " + nf.format(assessedValue) + "\n" +
                "Assessment class = " + assessmentClass  + "\n" +
                "Neighbourhood= " + neighbourhood + "\n" +
                "Location = " + coordinates + "\n"

                ;
    }


}
