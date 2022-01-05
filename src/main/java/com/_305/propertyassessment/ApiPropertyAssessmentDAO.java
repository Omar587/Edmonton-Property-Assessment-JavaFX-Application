package com._305.propertyassessment;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO{


    /**
     *
     * @param accountNumber account number from textfield
     *                      used to fill the table with data.
     * @return propertyAssessment list that contains the account number
     */
    public PropertyAssessment getByAccountNumber(String accountNumber){

        String endpoint= "https://data.edmonton.ca/resource/q7d6-ambg.csv";
        String query= endpoint + "?account_number=" + accountNumber;


            String s1 =  ApiBodyResponse(query);

        PropertyAssessmentCsvParser pas  = new PropertyAssessmentCsvParser();

        PropertyAssessment property = pas.parseCsvLine( s1.split("\n")[1]);
        return property;


    }


    /**
     *
     * @param neighbourhood neighbourhood from textfield
     *                     used to fill the table with data
     *
     * @return propertyAssessment list that contains the neighbourhoods
     */
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood){


        String endpoint= "https://data.edmonton.ca/resource/q7d6-ambg.csv";

       String query= "https://data.edmonton.ca/resource/q7d6-ambg.csv?$order=account_number ASC&$limit=500&$where=neighbourhood like" + "'%25"
                + neighbourhood.toUpperCase() + "%25'";

        String response=  ApiBodyResponse(makeValidUrl(query));
        return Collections.unmodifiableList(PropertyAssessmentList(response));

    }


    /**
     *
     * @param assessmentClass assement class from the comboBox
     *                     used to fill the table with data
     *
     * @return propertyAssessment list that contains the assessment classes.
     */
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass){

        String endpoint ="https://data.edmonton.ca/resource/q7d6-ambg.csv";
        String query = endpoint + "?$limit=500" +"&$where=" + "mill_class_1=" +"'" + assessmentClass.toUpperCase() + "' OR " +
                "mill_class_2=" +"'" + assessmentClass.toUpperCase() + "' OR "+ "mill_class_3=" +"'" + assessmentClass.toUpperCase() +"'";
        String response=  ApiBodyResponse(makeValidUrl(query));
        return Collections.unmodifiableList(PropertyAssessmentList(response));

    }


    /**
     * Grabs all data from the api
     * this method is used to initially fill table with data,
     * when the user reads the data source.
     *
     * @return
     */
    public List<PropertyAssessment> getAll(){

        String query= "https://data.edmonton.ca/resource/q7d6-ambg.csv?$order=account_number+ASC&$limit=500" ;
        String response=  ApiBodyResponse(query);
        return PropertyAssessmentList(response);

    }


    /**
     *
     * @param address gets the address text field
     *                used to fill the table
     *
     * @return returns the list of property assessments that contain the address
     */
    public List<PropertyAssessment> getByAddress(String address) {

        String input =address.toUpperCase();


        String query =addressQuerySelector(input);

        String response=  ApiBodyResponse(makeValidUrl(query));
        return Collections.unmodifiableList(PropertyAssessmentList(response));

    }


    /**
     *
     * @param min  minimum text field input
     * @param max  maximum text field  input
     *                         Gets property assessments within a certain range
     *
     * @return a list of propertyAssessments
     */

    public List<PropertyAssessment> getByValueRange(String min, String max) {

        if (min.equals("")) {
            min = "0";
        }


        /* %3E is > in ASCII  %3C is < in ASCII  */
        String endpoint ="https://data.edmonton.ca/resource/q7d6-ambg.csv";
        String query = endpoint + "?$order=account_number ASC&$limit=500&$where=assessed_value %3E "
                + min +  " AND " + "assessed_value %3C " + max  ;


        String response=  ApiBodyResponse(makeValidUrl(query));
        return Collections.unmodifiableList(PropertyAssessmentList(response));

    }




    /***
     *
     * @param assessmentClassCb assessment class combo box input
     * @param addressTf        the address text field input can take a suite or street name or house number or all 3.
     * @param neighbourhoodTf  neighbourhood text field
     * @param minValueTf       minimum range textfield
     * @param maxValueTf       maximum range textfield
     *
     *                         this method, takes several text fields and combines them into a google like search query
     *                         text fields passed in may be empty.
     *
     * @return propertyAssessment list.
     */

    public List<PropertyAssessment> getOnAdvancedSearch(String assessmentClassCb, String addressTf, String neighbourhoodTf, String minValueTf, String maxValueTf){


        // The combo Box  might be null so change the value to an empty string
        if (assessmentClassCb == null){
            assessmentClassCb = "";
        }
        // the minimum text field might have no input given so the default minimum input is 0.
        if (minValueTf.equals("")){
            minValueTf= "0";

        }

        String address = addressTf.toUpperCase().replace("ST", "STREET").replace("AVE", "AVENUE");
        String totalSearch =   address +" " +  neighbourhoodTf.toUpperCase() + " "+ assessmentClassCb.toUpperCase();

        String query = "https://data.edmonton.ca/resource/q7d6-ambg.csv?$q=";

        if (maxValueTf.equals("")){
             query= query + totalSearch;
        }else{
            query = query + totalSearch +  "&$where=assessed_value between "+ "'" +minValueTf + "'" + " and "+ " '"+maxValueTf +"'";
        }


        String response=  ApiBodyResponse(makeValidUrl(query));
        return Collections.unmodifiableList(PropertyAssessmentList(response));



    }



    /********************************************* Helper methods *************************************************/

    /**
     *
     * @param apiBodyResponse takes the response of the API query as a string
     *
     * Parses the string and turns into a propertyAssessment Object
     *
     * @return a propertyAssessment List
     */

    /*takes the api body response and parses the data and turns it into a list of propertyAssessment  */
    private   List<PropertyAssessment> PropertyAssessmentList(String apiBodyResponse){

        List<PropertyAssessment> propertyAssessmentList = new ArrayList<>();
        Scanner sc = new Scanner(apiBodyResponse);
        //Skip the first row which is the column names
        sc.nextLine();
        while (sc.hasNextLine()) {
            String csvLine = sc.nextLine();
            PropertyAssessmentCsvParser pas= new PropertyAssessmentCsvParser();
            PropertyAssessment property = pas.parseCsvLine(csvLine);
            propertyAssessmentList.add(property);
        }

        return Collections.unmodifiableList(propertyAssessmentList);

    }


    /**
     *
     * @param query Takes an API query
     * @return the response to the API request as a String
     */

    private String  ApiBodyResponse(String query){


        String bodyResponse = "";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =  HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            bodyResponse = response.body().replace("\"", "");
            return bodyResponse;

        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
            return bodyResponse;
        }



    }


    /**
     *
     * @param address addesss field.
     *
     *An address can either be given partially or fully, this method returns the query for either a
     * partial address or a full address.
     *
     * @return
     */
    private String addressQuerySelector(String address) {


        /*if the address has more 3 numbers  then
        $q preforms a full text search similar to a search engine like google*/

        int numberCounter = 0;

        for (int i = 0; i < address.length(); i++) {
            if (Character.isDigit(address.charAt(i))) {
                numberCounter++;
            }
        }


        /*full text search */
        if (numberCounter > 3) {
            return "https://data.edmonton.ca/resource/q7d6-ambg.csv?$q=" + address;
        }

            /*partial search return street search */
        else {
            return "https://data.edmonton.ca/resource/q7d6-ambg.csv?$order=account_number ASC&$limit=500&$where=street_name like" + "'%25"
            + address.toUpperCase() + "%25'";

            }


        }





    /**
     *
     * @param url
     * Helper method replaces spaces with plus sign to make the url valid
     *
     * @return
     */
    private  String makeValidUrl(String url) {

        return url.replaceAll("\\s","+");
    }

}