package testfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static testfx.MainRt.*;

public class JobCreatedEndedMonthly extends Application{
     public void start(Stage outStage3) {
        getMonthlyJobCreated("C:\\Users\\Rachel\\IdeaProjects\\exercise\\src\\extracted_log");
        getMonthlyJobEnded("C:\\Users\\Rachel\\IdeaProjects\\exercise\\src\\extracted_log");

        /*-----------------Table-------------------------*/
        // creating table view object
        TableView<Courses> table = new TableView<Courses>();

        //observable list for taking multiple values at a time
        ObservableList<Courses> courseData1 = FXCollections.observableArrayList((new Courses(monthStrings[0], cMonthlyCount[0], eMonthlyCount[0])));

        // Adding columns to the table
        TableColumn mColumn = new TableColumn("Months");
        TableColumn cColumn = new TableColumn("Number of jobs created");
        TableColumn eColumn = new TableColumn<>("Number of jobs ended");

        //Adding column value
        mColumn.setCellValueFactory(new PropertyValueFactory<Courses, String>("javaCorse"));
        cColumn.setCellValueFactory(new PropertyValueFactory<Courses, Integer>("numberofJobsCreated"));
        eColumn.setCellValueFactory(new PropertyValueFactory<Courses, Integer>("numberofJobsEnded"));

        table.setItems(courseData1);

        for (int i = 1; i < cMonthlyCount.length; i++) {
            courseData1.add(new Courses(monthStrings[i], cMonthlyCount[i], eMonthlyCount[i]));
        }


        //setting minimum width
        mColumn.setMinWidth(100);
        cColumn.setMinWidth(100);
        eColumn.setMinWidth(100);

        // adding columns to the table
        table.getColumns().addAll(mColumn, cColumn, eColumn);

        // creating VBox component
        VBox vBox = new VBox();

        // adding table to the vBox
        vBox.getChildren().add(table);

        // Title for display on top of the application
        outStage3.setTitle("Number of jobs created or ended");

        /*-----------------BarChart-------------------------*/
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of jobs");

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Number of jobs created and ended");

        // Create the data series
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Jobs created");

        // Add data points to the series
        for (int i = 0; i < cMonthlyCount.length; i++) {
            series1.getData().add(new XYChart.Data<>(monthStrings[i], cMonthlyCount[i]));
        }

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Jobs ended");

        // Add data points to the series
        for (int i = 0; i < cMonthlyCount.length; i++) {
            series2.getData().add(new XYChart.Data<>(monthStrings[i], eMonthlyCount[i]));
        }

        // Add the data series to the chart
        barChart.getData().addAll(series1, series2);
        BorderPane layout = new BorderPane();
        layout.setLeft(table);
        layout.setCenter(barChart);


        // Show the chart
        Scene scene3 = new Scene(layout, 800, 600);
        outStage3.setScene(scene3);
        outStage3.show();

    }

    public static class Courses {
        private String javaCorse;
        private int numberofJobsCreated;
        private int numberofJobsEnded;

        public Courses() {
        }


        public Courses(String javaCorse, int numberofJobsCreated, int numberofJobsEnded) {
            super();
            this.javaCorse = javaCorse;
            this.numberofJobsCreated = numberofJobsCreated;
            this.numberofJobsEnded = numberofJobsEnded;

        }

        public int getNumberofJobsEnded() {
            return numberofJobsEnded;
        }

        public void setNumberofJobsEnded(int numberofJobsEnded) {
            this.numberofJobsEnded = numberofJobsEnded;
        }

        public String getJavaCorse() {
            return javaCorse;
        }

        public void setJavaCorse(String javaCorse) {
            this.javaCorse = javaCorse;
        }

        public int getNumberofJobsCreated() {
            return numberofJobsCreated;
        }

        public void setNumberofJobsCreated(int numberofJobsCreated) {
            this.numberofJobsCreated = numberofJobsCreated;
        }


    }
}
