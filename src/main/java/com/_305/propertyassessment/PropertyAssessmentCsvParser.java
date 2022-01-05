package com._305.propertyassessment;

import java.util.HashMap;
import java.util.Map;

public class PropertyAssessmentCsvParser {
    /**
     * @param csvLine takes a single line from a csv file and turns elements of that line
     * to represent a PropertyAssessment object.
     *
     * @return returns a single PropertyAssessment object.
     */
    public static PropertyAssessment parseCsvLine(String csvLine) {

        /*The indexes of the string csvLine represents each column of the csv file */
        String[] column = csvLine.split(",");

        String accountNum = column[0];
        Address address = new Address(column[1], column[2], column[3]);
        String hasGarage = column[4];
        Neighbourhood neighbourhood = new Neighbourhood(column[5], column[6], column[7]);
        int assessedValue = Integer.valueOf(column[8]);
        Coordinates coordinates = new Coordinates(Double.valueOf(column[9]), Double.valueOf(column[10]));

        Map<String, Integer> acm = new HashMap<>();
        AssessmentClass assessmentClass = new AssessmentClass(acm);
        String assessmentClassPercent1 = column[12];
        String assessmentClassPercent2 = column[13];
        String assessmentClassPercent3 = column[14];
        String assessmentClassNum1 = column[15];
        assessmentClass.addAssessmentClass(assessmentClassNum1, Integer.parseInt(assessmentClassPercent1));

        /*A few lines have special cases sometimes a csvLine will have additional assessment classes
         that must be accounted for.
         */

        if (column.length == 17) {
            String assessmentClassNum2 = column[16];
            assessmentClass.addAssessmentClass(assessmentClassNum2, Integer.parseInt(assessmentClassPercent2));
        }

        if (column.length == 18) {
            String assessmentClassNum3 = column[17];
            String assessmentClassNum2 = column[16];
            assessmentClass.addAssessmentClass(assessmentClassNum2, Integer.parseInt(assessmentClassPercent2));
            assessmentClass.addAssessmentClass(assessmentClassNum3, Integer.parseInt(assessmentClassPercent3));
        }

        PropertyAssessment property = new PropertyAssessment(accountNum, address, hasGarage, neighbourhood, assessedValue, coordinates, assessmentClass);
        return property;

    }




}
