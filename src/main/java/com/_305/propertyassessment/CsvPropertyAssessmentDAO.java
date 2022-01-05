package com._305.propertyassessment;


/*manages a collection of propertyAssessment objects */

import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.Collections;
import java.util.stream.Collectors;


public class CsvPropertyAssessmentDAO extends PropertyStatistics implements  PropertyAssessmentDAO {


    private List<PropertyAssessment> propertyData = new ArrayList<>();


    public List<PropertyAssessment> getAll() {
        if (propertyData.size() == 0) {
            generatePropertyData("src\\main\\java\\propertyData.csv");
        }

        return Collections.unmodifiableList(propertyData);
    }

    private void setPropertyData(List<PropertyAssessment> propertyData) {
        this.propertyData = propertyData;
    }


    /**
     * This method will make use of the parseCSVLine helper method and parse
     * the lines of the csv file and turn each line into a propertyAssessment object.
     * Then it will set class field propertyData with a List of propertyAssessments.
     *
     * @throws IOException if the file con not be opened
     */
    /*generates a list of property asessments  */
    public void generatePropertyData(String file) {

        // String file = "src\\main\\java\\propertyData.csv";
        Scanner sc = null;

        try {
            sc = new Scanner(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<PropertyAssessment> propertyAssessmentList = new ArrayList<>();

        //Skip the first row which is the column names
        sc.nextLine();

        while (sc.hasNextLine()) {
            String csvLine = sc.nextLine();

            PropertyAssessmentCsvParser pas = new PropertyAssessmentCsvParser();
            PropertyAssessment property = pas.parseCsvLine(csvLine);
            propertyAssessmentList.add(property);
        }
        Collections.sort(propertyAssessmentList);
        setPropertyData(propertyAssessmentList);

    }

    /**
     *
     * @param neighbourhood takes a neighbourhood as a String
     * @return an unmodifiable list that will be used for the table view
     */

    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {

        if (getAll().size() == 0) {
            throw new IllegalStateException("generatePropertyData method must be invoked first");
        }

        return getAll().stream().
                filter(property -> property.getNeighbourhood().getArea().
                        contains(neighbourhood.toUpperCase())).collect(Collectors.toList());

    }




    /**
     * @param accountNum takes an account number as a String
     * @return returns a single property assessment which contains the account number
     */

    public PropertyAssessment getByAccountNumber(String accountNum){

        if (getAll().size() == 0) {
            throw new IllegalStateException("generatePropertyData method must be invoked first");
        }

        return getAll().stream().
                filter(property -> property.getAccountNumber().equals(accountNum)).findFirst().orElse(null);

    }




    /**
     * @param assessmentClass takes an assessment class as a string input
     * @return returns an unmodifiable list of property assessments which contain the neighbourhood given.
     */

    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass){

        if (getAll().size() == 0) {
            throw new IllegalStateException("generatePropertyData method must be invoked first");
        }

        return getAll().stream().
                filter(property -> property.getAssessmentClass().getAssessmentClassMap().toString().
                        contains(assessmentClass.toUpperCase())).collect(Collectors.toList());
    }



    /**
     * @param address takes an address as a string input
     * @return returns an unmodifiable list of propertyAssessments which contain the address given.
     */

    public List<PropertyAssessment> getByAddress(String address){
        if (getAll().size() == 0) {
            throw new IllegalStateException("generatePropertyData method must be invoked first");
        }

        String input = address.toUpperCase().replaceAll("\\s+", "");


        return getAll().stream().
                filter(property -> (property.getAddress().toString().toUpperCase().replaceAll("\\s+", "")).
                        contains(input.toUpperCase())).collect(Collectors.toList());

    }



    /**
     * @param min takes the min value as a string
     * @param max takes the max value as a string
     *
     * @return returns an unmodifiable list of propertyAssessments which contain the range  given.
     */
    public List<PropertyAssessment> getByValueRange(String min, String max) {
        //if the min text field is passed nothing, then 0 is default
        if (min.equals("")) {
            min = "0";
        }

        if (getAll().size() == 0) {
            throw new IllegalStateException("generatePropertyData method must be invoked first");
        }

        String finalMin = min;
        return getAll().stream().
                filter(property -> (property.getAssessedValue() > Integer.parseInt(finalMin) &&
                        property.getAssessedValue() < Integer.parseInt(max))).collect(Collectors.toList());
    }






    /**
     *
     * @param assessmentClassCb comboBox value
     * @param addressTf address text field input
     * @param neighbourhoodTf neighbourhood text field input
     * @param minValueTf      min value text field input
     * @param maxValueTf      max value text field input
     *
     *                        All the text fields and the assessment class comboBox is placed into this method.
     *                        the method will figure out which fields are empty and which ones are not. From there
     *                        it will match those values given with the propertyAssessment list.
     *
     *
     * @return a list
     */

    public List<PropertyAssessment> getOnAdvancedSearch(String assessmentClassCb, String addressTf, String neighbourhoodTf, String minValueTf, String maxValueTf) {


        if (getAll().size() == 0) {
            throw new IllegalStateException("generatePropertyData method must be invoked first");
        }
        // The comoboBox  might be null so change the value to an empty string
        if (assessmentClassCb == null) {
            assessmentClassCb = "";
        }
        // the minimum text field might have no input given so the default minimum input is 0.
        if (minValueTf.equals("")) {
            minValueTf = "0";
        }



        List<PropertyAssessment> advancedSearchList = new ArrayList<>();

        for (int i=0; i< getAll().size(); i++) {

            //values from the CSV columns
            String assessmentClass  = getAll().get(i).getAssessmentClass().toString();
            String address = getAll().get(i).getAddress().toString();
            String neighbourhood = getAll().get(i).getNeighbourhood().toString();
            int assessedValue = getAll().get(i).getAssessedValue();

            // if all booleans are true then place it into the list
            boolean addressBool =  addressTf.equals("") || address.contains(addressTf.toUpperCase());
            boolean neighbourhoodBool = neighbourhoodTf.equals("") || neighbourhood.contains(neighbourhoodTf.toUpperCase());
            boolean assessmentClassBool=  assessmentClassCb.equals("") || assessmentClass.contains(assessmentClassCb);
            boolean valueBool =true;
            if (!maxValueTf.equals("")) {
                valueBool = (assessedValue > Integer.parseInt(minValueTf) && assessedValue < Integer.parseInt(maxValueTf));
            }
            if (addressBool && neighbourhoodBool && assessmentClassBool && valueBool){

                advancedSearchList.add(getAll().get(i));

            }
        }

        return  Collections.unmodifiableList(advancedSearchList);


    }






}