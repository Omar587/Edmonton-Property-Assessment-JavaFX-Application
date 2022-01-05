package com._305.propertyassessment;

import PropertyCrime.NeighbourhoodCrimeAssessments;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.*;


public class NeighbourhoodCrimeGUI extends PropertyAssessmentMainApplication{

    private BarChart<String, Number> barChart;
    private  ComboBox<String> dimensionsCb, crimeDescriptorCb;
    private TextField neighbourhoodTf;
    private Button searchButton, resetButton, previousWindow;
    private TextArea statBox;

    private final NeighbourhoodCrimeAssessments neighbourhoodCrimes = new NeighbourhoodCrimeAssessments();



    /*Links to previous scene */
    public Button getPreviousWindow() {
        return previousWindow;
    }

    /**
     * This is a method that places all the Ui elements on the left side
     * of the Ui such as combo boxes, text fields, textArea, Buttons.
     *
     * @return a group of text labels
     */
    private Group leftLayout(){

        Text leftLayOutTitle = new Text("Find Crime Occurrences");
        leftLayOutTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        Text neighbourhoodText = new Text("Neighbourhood ");
        neighbourhoodTf = new TextField();

        Text dimensions = new Text("Dimensions");
        Text crimeDescriptor = new Text("Crime Descriptor");
        VBox vb1 = new VBox(15);
        HBox searchAndReset = new HBox(3);
        resetButton = new Button("Reset");
        resetButton.setOnAction(this);
        resetButton.setPrefWidth(150);
        searchButton = new Button("Search");
        searchButton.setOnAction(this);
        searchButton.setPrefWidth(150);
        searchAndReset.getChildren().addAll(resetButton, searchButton);
        previousWindow = new Button("                               Previous Window                             ");

        Text statText = new Text("General Neighbourhood Crime Statistics");
        statBox= new TextArea();
        statBox.setMaxWidth(300);
        statBox.setEditable(false);

        vb1.getChildren().addAll(leftLayOutTitle, dimensions, setDimensionsCB()
                ,neighbourhoodText, neighbourhoodTf, crimeDescriptor,setCrimeDescriptorsForCb(),
                searchAndReset, statText, statBox, previousWindow);

        vb1.setLayoutY(0);
        vb1.setLayoutX(5);

        Group group = new Group(vb1);


        return group;

    }




    /**
     * Responsible for setting up the barchart Chart on the Ui
     *
     */

    private Group createBarChart(){

        CategoryAxis xAxis = new CategoryAxis();
       // xAxis.setLabel("Neighbourhood");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Crime Occurrences");
        barChart = new BarChart(xAxis, yAxis);
        barChart.setTitle("Neighbourhood Criminal Occurrences 2009-2019");
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);
        barChart.setPrefSize(1000, 600);
        barChart.setLayoutX(350);
        barChart.setLayoutY(35);


        return new Group(barChart);

    }

    /**The propertyAssessmentMainApplication uses this method to set up the scene in this GUI */
    public Group propertyCrime(){
        Group group = new Group();
        group.getChildren().addAll(leftLayout(), createBarChart());
        return group;
    }


    /**
     * Gets the crime descriptors from the NeighbourhoodCrimeAssessments
     * list. And fills the crimeDescriptorCb with crimes like Assault,
     * Homicide etc...
     *
     * @return a group containing the crimeDescriptorCb
     */
    public Group setCrimeDescriptorsForCb() {


        crimeDescriptorCb = new ComboBox<>();
        crimeDescriptorCb.setPrefWidth(300);

        NeighbourhoodCrimeAssessments pc = new NeighbourhoodCrimeAssessments();

        Map<String, Integer> hm = pc.crimeDescriptorsAndCount();
        //TreeMap<String, Integer> tm = new TreeMap<>(hm);

        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
            crimeDescriptorCb.getItems().add(entry.getKey());
        }

        return new Group(crimeDescriptorCb);
    }


    /**
     * Sets the values for the dimension combo box.
     *
     * @return a group containing the dimension combo box.
     */
        public Group setDimensionsCB(){
        dimensionsCb = new ComboBox<>();
        dimensionsCb.setPrefWidth(300);
        dimensionsCb.getItems().add("Crime Violation Types");
        dimensionsCb.getItems().add("Total Criminal Incidents Per Year");
        dimensionsCb.getItems().add("Total Criminal Incidents Per Quarter");
        dimensionsCb.getItems().add("Total Criminal Incidents reported by Neighbourhood");
        dimensionsCb.getItems().add("Total Criminal Incidents Per Month");
        return new Group(dimensionsCb);

    }


    /**
     *This method will be have the responsiiblity to draw the barchart on the Ui.
     * This method will take many maps that were generated from the propertyCrimeAssessments
     * class.
     *
     * @param neighbourhoodMap Map<String, Integer>
     *
     */
    public void drawBarChart(Map<String, Integer> neighbourhoodMap){
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : neighbourhoodMap.entrySet())
        {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));

        }
        barChart.getData().add(series);

    }








    /**************************************** CONTROLLER CODE FOR ACTION EVENTS **************************************/

    @Override
    public void handle(Event event) {

        if (dimensionsCb.getValue() != null){
            neighbourhoodTf.setDisable(true);
            crimeDescriptorCb.setDisable(true);
        }

        resetButtonAction(event);
        searchButtonAction(event);



    }

    /**Resets all fields on the Ui */
    public void resetButtonAction(Event event){

        if (event.getSource() == resetButton) {

            neighbourhoodTf.clear();
            dimensionsCb.valueProperty().set(null);
            crimeDescriptorCb.valueProperty().set(null);
            neighbourhoodTf.setDisable(false);
            crimeDescriptorCb.setDisable(false);
            statBox.clear();



        }


    }



    /**Calls the helper methods to search for desired inputs */
    public void searchButtonAction(Event event) {

        if (event.getSource() == searchButton) {

            dimensionSelections();
            crimeDescriptorAndNeighbourhoodSearch();
            crimeDescriptorByNeighbourhood();
            crimeDescriptorYearTrend();


        }

    }



    /**draws the bar chart based on dimension combo box selection */
    public void dimensionSelections() {



        if (dimensionsCb.getValue() != null) {


            if (dimensionsCb.getValue().equals("Crime Violation Types")) {
                drawBarChart(neighbourhoodCrimes.crimeDescriptorsAndCount());
            } else if (dimensionsCb.getValue().equals("Total Criminal Incidents reported by Neighbourhood")) {
                drawBarChart(neighbourhoodCrimes.neighbourhoodsCrimeCount());

            } else if (dimensionsCb.getValue().equals("Total Criminal Incidents Per Quarter")) {
                drawBarChart(neighbourhoodCrimes.incidentsPerQuarter());
            } else if (dimensionsCb.getValue().equals("Total Criminal Incidents Per Year")) {
                drawBarChart(neighbourhoodCrimes.incidentsPerYear());
            } else if (dimensionsCb.getValue().equals("Total Criminal Incidents Per Month")) {
                drawBarChart(neighbourhoodCrimes.totalIncidentsPerMonth());
            } else if (!(crimeDescriptorCb.getValue() == null) && !neighbourhoodTf.getText().isEmpty()) {
                drawBarChart(neighbourhoodCrimes.crimeTypeByYearInNeighbourhood(neighbourhoodTf.getText(), crimeDescriptorCb.getValue()));

            }
        }
    }





    /** if the neighbourhood field and the crime descriptor field is the only fields with input.  */
    public void crimeDescriptorAndNeighbourhoodSearch(){


           if ( !(crimeDescriptorCb.getValue() == null) && !neighbourhoodTf.getText().isEmpty() &&
                   dimensionsCb.getValue() == null  ){

            drawBarChart(neighbourhoodCrimes.crimeTypeByYearInNeighbourhood(neighbourhoodTf.getText(), crimeDescriptorCb.getValue()));


        }
    }


    /** If the neighbourhood is the only field with input. */
    public void crimeDescriptorByNeighbourhood(){


        if ( !neighbourhoodTf.getText().isEmpty() && (crimeDescriptorCb.getValue() == null) &&
                dimensionsCb.getValue() == null  ){

            drawBarChart(neighbourhoodCrimes.neighbourhoodCrimeType(neighbourhoodTf.getText()));

            statBox.setText( neighbourhoodCrimes.neighbourhoodSafetyRank(neighbourhoodTf.getText()));


        }

    }

    /**if the crime descriptor box is the only field with input on the Ui.
     */
    public void crimeDescriptorYearTrend(){


        if ( (crimeDescriptorCb.getValue() != null) && neighbourhoodTf.getText().isEmpty() &&
                dimensionsCb.getValue() == null  ){

            drawBarChart(neighbourhoodCrimes.crimeTypePerYear(crimeDescriptorCb.getValue()));

        }


    }






}







