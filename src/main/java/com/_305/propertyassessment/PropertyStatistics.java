package com._305.propertyassessment;
import java.text.NumberFormat;
import java.util.List;

public class PropertyStatistics {

      private int propertyCount;
      private int min;
      private int max;
      private int range;
      private double mean;
      private double median;


    private int getPropertyCount() {
        return propertyCount;
    }

    private void setPropertyCount(int propertyCount) {
        this.propertyCount = propertyCount;
    }

    private int getMin() {
        return min;
    }

    private void setMin(int min) {
        this.min = min;
    }

    private int getMax() {
        return max;
    }

    private void setMax(int max) {
        this.max = max;
    }

    private int getRange() {
        return range;
    }

    private void setRange(int range) {
        this.range = range;
    }

    private double getMean() {
        return mean;
    }

    private void setMean(double mean) {
        this.mean = mean;
    }

    private double getMedian() {
        return median;
    }

    private void setMedian(double median) {
        this.median = median;
    }




    /**
     * @param propertyAssessments takes a property assessment list and calculates statistics on that given list
     * Assumes the list is sorted before being processed
     * @return PropertyStatistics object which contains descriptive statistics like min mean max etc..
     */
    public PropertyStatistics descriptiveStats(List<PropertyAssessment> propertyAssessments) {

        if (propertyAssessments.isEmpty() ){
            throw new IllegalArgumentException("Data not found");
        }

        NumberFormat nf = NumberFormat.getCurrencyInstance();

        PropertyStatistics stats = new PropertyStatistics();

        /*Min starts at the first index since it is sorted  Max is the last index*/
        int minVal=propertyAssessments.get(0).getAssessedValue();
        int maxVal=propertyAssessments.get(propertyAssessments.size() - 1).getAssessedValue();

        stats.setPropertyCount(propertyAssessments.size());
        stats.setMin(minVal);
        stats.setMax(maxVal);
        stats.setRange(maxVal-minVal );

        /*sums all assessed values */
        long combinedValues = 0;
        for (int i = 0; i < propertyAssessments.size(); i++) {
            combinedValues = propertyAssessments.get(i).getAssessedValue() + combinedValues;
        }

        /*finds the mean */
        double mean = (double) combinedValues / propertyAssessments.size();
        stats.setMean(Math.round(mean));


        /* median case for even */
        if (propertyAssessments.size() % 2 == 0) {
            int firstHalf = (propertyAssessments.size() / 2);
            int secondHalf = firstHalf - 1;
            double median = (double) (propertyAssessments.get(firstHalf).getAssessedValue() + propertyAssessments.get(secondHalf).getAssessedValue()) / 2.0;
            stats.setMedian(Math.round(median));

            /*median case for odd */
        } else {
            int half = (propertyAssessments.size() / 2);
            double median =  propertyAssessments.get(half).getAssessedValue();
            stats.setMedian(median);
        }

        return stats;

    }

    @Override
    public String toString() {

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMinimumFractionDigits(0);


        return
                "n = " + getPropertyCount()  + "\n" +
                "min = " + nf.format(getMin()) + "\n" +
                "max = " + nf.format(getMax()) + "\n" +
                "range = " + nf.format(getRange()) + "\n" +
                "mean = " + nf.format((int)getMean()) + "\n" +
                "median = " + nf.format((int)getMedian()) + "\n" ;


    }

}
