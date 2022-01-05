package com._305.propertyassessment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;


/**
 * This
 */

public class PropertyAssessmentMainApplication extends Application implements EventHandler {
    private TableView<PropertyAssessment> table;

    private ComboBox<String> dataSourceCb;
    private ComboBox<String> assessmentClassCb;
    private TextField tfAccount;
    private TextField tfAddress;
    private TextField tfNeighbourhood;
    private TextField tfMinVal;
    private TextField tfMaxVal;
    private Button searchButton;
    private Button resetButton;
    private Button readData;
    private ApiPropertyAssessmentDAO apiDAO;
    private CsvPropertyAssessmentDAO csvDAO;
    private PropertyAssessmentDAO propertyDao;
    private Button showCrimeGui;
    private Scene propertyCrimeGui;




    /**
     * Sets the stage of all the Ui elements that were defined
     * in helper methods.
     * @param stage
     *
     */
    @Override
    public void start(Stage stage)  {


        Group group = new Group();
        group.getChildren().addAll(createTable(), leftLayout());
        Scene propertyAssessmentScene = new Scene(group, 1350, 720);
        apiDAO = new ApiPropertyAssessmentDAO();
        csvDAO = new CsvPropertyAssessmentDAO();
        //initializes  the csv property assessment  list  on startup
        makeAssessmentClassSelection(csvDAO.getAll());
        stage.setTitle("Edmonton Property Assessments!");
        stage.setScene(propertyAssessmentScene);
        //stage.setResizable(false);



        NeighbourhoodCrimeGUI p = new NeighbourhoodCrimeGUI();
        VBox layout = new VBox(20);
        propertyCrimeGui = new Scene(p.propertyCrime(),1350,720);

        showCrimeGui.setOnAction(e -> stage.setScene(propertyCrimeGui) );
        p.getPreviousWindow().setOnAction(event -> stage.setScene(propertyAssessmentScene));




        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }




    /**
     * This method sets up the table view, on the Ui with its columns,
     *The data is taken from the propertyAssessment class.
     *
     * @return
     */

    private Group createTable() {

        //title above the table
        Text tableTitle = new Text(350,20,"Edmonton Property Assessments (2021)");
        tableTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        //Account column
        TableColumn<PropertyAssessment,String> account= new TableColumn("Account");
        account.setMinWidth(50);
        account.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

        //Address column
        TableColumn<PropertyAssessment,String>  address= new TableColumn("Address");
        address.setMinWidth(150);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        //assessed value column
        TableColumn<PropertyAssessment,String>  assessedValue = new TableColumn("Assessed Value");
        assessedValue.setMinWidth(150);
        assessedValue.setCellValueFactory(new PropertyValueFactory<>("valueCurrencyForm"));

        //assessment classs column
        TableColumn<PropertyAssessment,String>  assessmentClass = new TableColumn("Assessment Class");
        assessmentClass.setMinWidth(200);
        assessmentClass.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));

        //neighbourhood column
        TableColumn<PropertyAssessment, String>  neighbourhood = new TableColumn("Neighbourhood");
        neighbourhood.setMinWidth(200);
        neighbourhood.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));

        //latitude and longitude column
        TableColumn<PropertyAssessment,String>  cord = new TableColumn("(Latitude, Longitude)");
        cord.setMinWidth(200);
        cord.setCellValueFactory(new PropertyValueFactory<>("coordinates"));

        // table = new TableView<>();
        table = new TableView<>();
        table.getColumns().addAll(account,address, assessedValue, assessmentClass, neighbourhood, cord);
        table.setPrefSize(1000, 600);
        table.setLayoutX(350);
        table.setLayoutY(35);

        Group group = new Group();
        group.getChildren().addAll(table,tableTitle);
        return group;

    }


    /**
     * This layout contains everything in the left layout, things like text fields, combo boxes,
     * text titles, and buttons.
     *
     */
    private VBox leftLayout(){

        VBox layout = new VBox(15);
        layout.setLayoutX(10);
        Text dataSourceText = new Text();
        dataSourceText.setText("Select Data Source");
        dataSourceText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));

        dataSourceCb = new ComboBox<>();
        dataSourceCb.getItems().add("CSV File");
        dataSourceCb.getItems().add("Edmonton Open Data Portal");
        dataSourceCb.setMaxSize(300,300);

        dataSourceCb.setOnAction(this);
        readData = new Button("Read Data");
        readData.setMaxSize(300,300);
        readData.setOnAction(this);

        showCrimeGui = new Button("Show Neighbourhood Crime Screen");
        showCrimeGui.setMaxSize(300,300);
        showCrimeGui.setOnAction(this);

        Text findPropertyText = new Text("Find Property Assessment");
        findPropertyText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        layout.getChildren().addAll(dataSourceText,dataSourceCb,readData,findPropertyText,textFields(), showCrimeGui);

        return layout;
    }


    /**
     * Contains all text fields and their text titles used for the GUI,
     * it is a helper method for the left leftLayout method.
     *
     * @return a Vbox that contains the the Ui format and elements.
     */
        public VBox textFields(){
        VBox layout = new VBox(15);
        layout.setLayoutX(10);

        Text accountNumText = new Text("Account Number");
        tfAccount = new TextField();

        Text addressText = new Text("Address (#suite #house street):");
        tfAddress = new TextField();

        Text neighbourhoodText = new Text("Neighbourhood");
        tfNeighbourhood = new TextField();

        Text assessmentClassText = new Text("Assessment Class:");
        assessmentClassCb= new ComboBox<>();
        assessmentClassCb.setMaxSize(300,300);

        Text rangeText = new Text("Assessed Value Range: ");
        HBox minMaxTextFields = new HBox(3);
        tfMinVal = new TextField();
        tfMaxVal = new TextField();
        tfMaxVal.setPromptText("Max Value");
        tfMinVal.setPromptText("Min Value");

        minMaxTextFields.getChildren().addAll(tfMinVal, tfMaxVal);

        HBox searchAndReset = new HBox(3);
        resetButton = new Button("                Reset               ");
        resetButton.setMaxWidth(700);
        resetButton.setOnAction(this);
        searchButton = new Button("                Search               ");
        searchButton.setOnAction(this);
        searchAndReset.getChildren().addAll(resetButton, searchButton);

        layout.getChildren().addAll(accountNumText,tfAccount, addressText, tfAddress, neighbourhoodText, tfNeighbourhood);
        layout.getChildren().addAll(assessmentClassText, assessmentClassCb,rangeText,minMaxTextFields, searchAndReset);
        return layout;
    }


    /**
     * A general purpose alert box, that is used to display
     *a message to a user, this alertBox will be used to let users know
     * if there search results in nothing.
     *
     * @param errorMessage
     */
    public void alertBox(String errorMessage){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error window");
        Text text = new Text();
        text.setText(errorMessage);
        text.setFont(new Font("Verdana",12));

        VBox layout = new VBox(10);

        layout.getChildren().add(text);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout,400,150);

        window.setScene(scene);
        window.showAndWait();


    }


    /**
     * The purpose of this method is  to fill the comboBox with the relevant, assessment Classes.
     *from the propertyAssessment list.
     *
     * @param properties
     */
    private void makeAssessmentClassSelection(List<PropertyAssessment> properties) {

        ;
        Set<String> uniqueValues = new HashSet<>();

        for (int i = 0; i < properties.size(); i++) {

            //get key values for assessment classes
            Map<String, Integer> ac = properties.get(i).getAssessmentClass().getAssessmentClassMap();
            List<String> keys = new ArrayList<>(ac.keySet());
            uniqueValues.add(keys.get(0));

        }

        //fill assessmnet classes
        for (String assessmentClass : uniqueValues) {
            assessmentClassCb.getItems().add(assessmentClass);
        }

    }


    /**
     *
     * @param properties a propertyAssessment list
     * @return an observableList that will be used to fill the tableView.
     */
    public ObservableList<PropertyAssessment> tableContents(List<PropertyAssessment> properties){
        ObservableList<PropertyAssessment> propertyList = FXCollections.observableArrayList(properties);

        return propertyList;


    }

    /*****************************  PROPERTY CRIME GUI CODE **********************************************************/









    /******************************* Controller Code Handles Action Events *****************************************/

    @Override
    public void handle(Event event) {

        //gets datasource
        dataSourceActionButton(event);
        //resets fields and combo boxes and buttons
        resetButtonAction(event);
        //all query searches happen in this function
        searchButtonAction(event);



    }


    /**
     * Simply clears the entire Ui
     *
     * @param event
     */
    public void resetButtonAction(Event event){

        if (event.getSource() == resetButton) {

            tfAddress.clear();
            tfMinVal.clear();
            tfMaxVal.clear();
            tfNeighbourhood.clear();
            tfAddress.clear();
            tfAccount.clear();
            dataSourceCb.valueProperty().set(null);
            assessmentClassCb.valueProperty().set(null);
            table.getItems().clear();

        }

    }


    /**
     * This method is responsible for responding the the user data source selection,
     *based on this selection, the propertyDao will be either switched to the apiDAO or csvDAO.
     *
     * @param event
     */
    public  void dataSourceActionButton(Event event){

        if (event.getSource() == readData && dataSourceCb.getValue() != null) {

            if (dataSourceCb.getValue().equals("Edmonton Open Data Portal")) {
                System.out.println("api selected");
                propertyDao = apiDAO;


            } else {
                System.out.println("CSV file selected");
                propertyDao = csvDAO;
            }
            table.setItems( tableContents( propertyDao.getAll()));

        }


    }


    /**
     * finds an account when the text field is not empty and every other field is empty
     * Helper for the searchButtonAction.
     */
    public void accountSearch(){
        if (!tfAccount.getText().isEmpty()
                && tfAddress.getText().isEmpty() && tfNeighbourhood.getText().isEmpty()
                && tfMinVal.getText().isEmpty() && tfMaxVal.getText().isEmpty()
                && assessmentClassCb.getValue() == null) {

            ObservableList<PropertyAssessment> p = FXCollections.observableArrayList();
            p.addAll(propertyDao.getByAccountNumber(tfAccount.getText()));
            table.setItems(tableContents(p));


        }

    }

    /**
     * finds a neighbourhood when the text field is not empty and every other field is empty. Helper
     * for the searchButtonAction.
     */
    public void crimeDescriptorByNeighbourhood(){

           if (!tfNeighbourhood.getText().isEmpty()
                && tfAddress.getText().isEmpty() && tfAccount.getText().isEmpty()
                && tfMinVal.getText().isEmpty() && tfMaxVal.getText().isEmpty()
                && assessmentClassCb.getValue() == null) {

            ObservableList<PropertyAssessment> neighbourhoodList =
                    tableContents(propertyDao.getByNeighbourhood(tfNeighbourhood.getText()));

            if (neighbourhoodList.size()==0){
                alertBox("Neighbourhood " + tfNeighbourhood.getText() +" not found");

            }else{
                table.setItems(neighbourhoodList);
            }


        }
    }


    /**
     * finds an address when the text field is not empty and every other field is empty. Helper
     * for the searchButtonAction.
     *
     */
    public void addressSearch(){

           if (! tfAddress.getText().isEmpty()
                && tfNeighbourhood.getText().isEmpty() && tfAccount.getText().isEmpty()
                && tfMinVal.getText().isEmpty() && tfMaxVal.getText().isEmpty()
                && assessmentClassCb.getValue() == null) {


               ObservableList<PropertyAssessment> addressList = tableContents(propertyDao.getByAddress(tfAddress.getText()));
               table.setItems(addressList);

               if (addressList.size()==0){
                   alertBox("The address " + tfAddress.getText() +" not found");

               }else{
                   table.setItems(addressList);
               }



        }
    }

    /**
     * finds an assessment Class when the combo box is not empty and every other field is empty. Helper
     * for the searchButtonAction.
     */
    public void assessmentClassSearch(){

          if (assessmentClassCb.getValue() != null
                && tfNeighbourhood.getText().isEmpty() && tfAccount.getText().isEmpty()
                && tfMinVal.getText().isEmpty() && tfMaxVal.getText().isEmpty()
                && tfAddress.getText().isEmpty()) {

            table.setItems(tableContents(propertyDao.getByAssessmentClass(assessmentClassCb.getValue())));



        }

    }

    /**
     * finds the max and min field is not empty  and every other field is empty. Helper
     *  for the searchButtonAction.
     *
     */
    public void assessedValueRangeSearch(){



            if ( (!tfMinVal.getText().isEmpty() && !tfMaxVal.getText().isEmpty())
                && tfNeighbourhood.getText().isEmpty() && tfAccount.getText().isEmpty()
                &&assessmentClassCb.getValue() == null ) {

                ObservableList<PropertyAssessment>   rangeList=
                        tableContents(propertyDao.getByValueRange(tfMinVal.getText(), tfMaxVal.getText()));


                if  (rangeList.size()==0){
                    alertBox("The range between " + tfMinVal.getText() + " and" + tfMaxVal.getText() + "" +
                            " could not be found.");
                }else{
                    table.setItems(rangeList);
                }


        }
    }


    /**
     * finds an assessment Class when the combo box is not empty and every other field is empty. Helper
     * for the searchButtonAction.
     */

    public void assessedValueMaxSearch(){
        //finds the max property
            if (!tfMaxVal.getText().isEmpty() &&
                tfMinVal.getText().isEmpty()  && tfNeighbourhood.getText().isEmpty() && tfAccount.getText().isEmpty()
                &&assessmentClassCb.getValue() == null ) {

                ObservableList<PropertyAssessment>   rangeList=
                        tableContents(propertyDao.getByValueRange(tfMinVal.getText(), tfMaxVal.getText()));


                if  (rangeList.size()==0){
                    alertBox("The range between " + tfMinVal.getText() + " and" + tfMaxVal.getText() + "" +
                            " could not be found.");
                }else{
                    table.setItems(rangeList);
                }

        }
    }


    /**Multiple critera search if there are 2 or more fields given on the Ui there will be an advanced search.
     Helper for the searchButtonAction */
    public void advancedSearch(){

        int fieldCount =advancedSearchHelper();
        if (fieldCount >= 2) {
            ObservableList<PropertyAssessment> advancedSearchList = tableContents((propertyDao.getOnAdvancedSearch(assessmentClassCb.getValue(),
                    tfAddress.getText(), tfNeighbourhood.getText(), tfMinVal.getText(), tfMaxVal.getText())));

            if (advancedSearchList.size() == 0) {
                alertBox("Your multiple criteria search can't be found!");
            } else {
                table.setItems(advancedSearchList);
            }
        }

    }


    /**
     * Helper for the advancedSearch method, it simply counts how many fields are
     * not empty, it will help the advanced search button to assess if an advanced search
     * will commence.
     * @return
     */
    public int advancedSearchHelper(){
        int fieldInputCount =0;
        if (assessmentClassCb.getValue() != null){
            fieldInputCount = fieldInputCount +1;
        }
        String[] fieldList =  {tfAddress.getText(), tfNeighbourhood.getText(), tfMaxVal.getText()};
        for (String s : fieldList) {
            if (!s.equals("")) {
                fieldInputCount++;
            }
        }
        return fieldInputCount;
    }


    /**
     * Calls all the helper methods for searching
     *
     * @param event
     */
    public void searchButtonAction (Event event){

        if (event.getSource() == searchButton && dataSourceCb.getValue() != null) {

            crimeDescriptorByNeighbourhood();
            assessmentClassSearch();
            accountSearch();
            addressSearch();
            assessedValueMaxSearch();
            assessedValueRangeSearch();
            advancedSearch();


        }


    }





}