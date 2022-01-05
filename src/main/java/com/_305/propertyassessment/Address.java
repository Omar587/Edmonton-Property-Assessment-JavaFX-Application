package com._305.propertyassessment;

public class Address {

    private String suite;
    private String houseNum;
    private String streetName;

    public Address(String suite, String houseNum, String streetName) {
        this.suite = suite;
        this.houseNum = houseNum;
        this.streetName = streetName;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public String toString() {

      String address = suite + " " + houseNum + " " + streetName;
      return address;

    }
}
