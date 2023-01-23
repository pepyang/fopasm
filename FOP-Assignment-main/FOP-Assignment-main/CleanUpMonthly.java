
package testfx;

import static testfx.MainRt.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CleanUpMonthly extends Application{
    
    public void start(Stage outStage1) {

        cleanup("C:\\Users\\Rachel\\IdeaProjects\\exercise\\src\\extracted_log");

        /*-----------------Table-------------------------*/

        // Creating table view object
        TableView<CleanUpMonthly.Courses> table = new TableView<CleanUpMonthly.Courses>();

        //Observable list for taking multiple values at a time
        ObservableList<CleanUpMonthly.Courses> courseData1 = FXCollections.observableArrayList((new CleanUpMonthly.Courses(monthStrings[0], cleanup[0])));

        // Adding columns to the table
        TableColumn mColumn = new TableColumn("Months");
        TableColumn cColumn = new TableColumn("Number of cleanup completing");

        //Adding column value
        mColumn.setCellValueFactory(new PropertyValueFactory<CleanUpMonthly.Courses, String>("javaCorse"));
        cColumn.setCellValueFactory(new PropertyValueFactory<CleanUpMonthly.Courses, Integer>("numberofcleanup"));

        table.setItems(courseData1);

        for (int i = 1; i < cleanup.length; i++) {
            courseData1.add(new CleanUpMonthly.Courses(monthStrings[i], cleanup[i]));
        }

        //setting minimum width
        mColumn.setMinWidth(200);
        cColumn.setMinWidth(200);

        // adding columns to the table
        table.getColumns().addAll(mColumn, cColumn);

        // creating VBox component
        VBox vBox = new VBox();

        // adding table to the vBox
        vBox.getChildren().add(table);

        // Title for display on top of the application
        outStage1.setTitle("Number of CleanUp Completing");

        /*-----------------BarChart-------------------------*/
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of CleanUp Completing");

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Number of CleanUp Completing");

        // Create the data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Number of CleanUp Completing");

        barChart.minWidth(100);

        // Add data points to the series
        for (int i = 0; i < cleanup.length; i++) {
            series.getData().add(new XYChart.Data<>(monthStrings[i], cleanup[i]));
        }

        // Add the data series to the chart
        barChart.getData().addAll(series);


        BorderPane layout = new BorderPane();
        layout.setLeft(table);
        layout.setCenter(barChart);


        // Show the chart
        Scene scene1 = new Scene(layout, 800, 600);
        outStage1.setScene(scene1);
        outStage1.show();

    }

    public static class Courses {
        private String javaCorse;
        private int numberofcleanup;

        public Courses() {
        }

        public Courses(String javaCorse, int numberofcleanup) {
            super();
            this.javaCorse = javaCorse;
            this.numberofcleanup = numberofcleanup;
        }

        public String getJavaCorse() {
            return javaCorse;
        }

        public void setJavaCorse(String javaCorse) {
            this.javaCorse = javaCorse;
        }

        public int getNumberofcleanup() {
            return numberofcleanup;
        }

        public void setNumberofcleanup(int numberofcleanup) {
            this.numberofcleanup = numberofcleanup;
        }
    }
}