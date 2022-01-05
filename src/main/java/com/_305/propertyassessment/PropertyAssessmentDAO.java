package com._305.propertyassessment;

import java.util.List;

public interface PropertyAssessmentDAO {

    PropertyAssessment getByAccountNumber(String accountNumber);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getByAddress(String address);
    List<PropertyAssessment> getByValueRange(String min, String max);
    List<PropertyAssessment> getAll();

    /*The paramater is simply used for storing multiple search requirements */
    List<PropertyAssessment> getOnAdvancedSearch(String assessmentClassCb, String addressTf, String neighbourhoodTf, String minValueTf, String maxValueTf);


}
