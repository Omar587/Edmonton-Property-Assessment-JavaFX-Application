package com._305.propertyassessment;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class AssessmentClass {

    private  Map<String,Integer> assessmentClassMap;


    /*An assessment class will be stored as the key and its value will be the allocated percent  */
    public AssessmentClass(Map<String, Integer> assessmentClassMap) {
        this.assessmentClassMap = new HashMap<>(assessmentClassMap);
    }

    public Map<String, Integer> getAssessmentClassMap() {
        return Collections.unmodifiableMap(assessmentClassMap);
    }


    public void addAssessmentClass(String assessmentClass, Integer assessmentPercentage){
        /* combines assessment class percentage if the assessment class given is already in the hashmap a row may have a repeat
        * assessment class. Combining the percentage will help clean up the data.*/
        if (assessmentClassMap.containsKey(assessmentClass)){
            int currentKeyValue= assessmentClassMap.get(assessmentClass);
            int givenKeyValue = assessmentPercentage;

            int combinedPercent =  givenKeyValue + currentKeyValue;
            assessmentClassMap.put(assessmentClass, combinedPercent);

        /*The assessment class is not in the map*/
        }else{
            assessmentClassMap.put(assessmentClass, assessmentPercentage);
        }
    }


    @Override
    public String toString() {

        String assessmentClasses = Arrays.toString(assessmentClassMap.entrySet().toArray());
        return  assessmentClasses;

    }
}
