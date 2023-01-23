//(e) MONTHLY complete table and bar chart
package testfx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static testfx.MainRt.*;

public class MergedJobRateBarChart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        //table1
        TableView<JobData> table1 = new TableView<>();
        table1.setPrefWidth(1000);
        table1.setPrefHeight(210);

        // Create columns
        TableColumn<JobData, String> monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<JobData, Integer> submittedColumn = new TableColumn<>("No. of Jobs Submitted");
        submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submitted"));

        TableColumn<JobData, Integer> killedColumn = new TableColumn<>("No of Jobs Killed");
        killedColumn.setCellValueFactory(new PropertyValueFactory<>("killed"));

        TableColumn<JobData, Double> rateColumn = new TableColumn<>("Rate of Job Killed in %");
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));

        TableColumn<JobData, Double> averageColumn = new TableColumn<>("Average Job Killed");
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        // Add columns to table
        table1.getColumns().addAll(monthColumn, submittedColumn, killedColumn, rateColumn, averageColumn);

        // Get data from getJobKill class
        getJobKilled(filename);
        
        for (int i = 0; i < monthStrings.length; i++) {
            table1.getItems().add(new JobData(monthStrings[i], monthlyJobSubmitted[i], monthlyJobKilled[i], rateJobKilled[i], averageJobKilled[i]));
        }
        monthColumn.setMinWidth(200);
        submittedColumn.setMinWidth(200);
        killedColumn.setMinWidth(200);
        rateColumn.setMinWidth(200);
        averageColumn.setMinWidth(200);

        //barchart 1
        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setLabel("Month");
        NumberAxis yAxis1 = new NumberAxis();
        yAxis1.setLabel("No. of Jobs");
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
        barChart1.setTitle("Job Submitted and Job Killed");

        XYChart.Series<String, Number> series11 = new XYChart.Series<>();
        series11.setName("Jobs Submitted");
        for (int i = 0; i < monthStrings.length; i++) {
            series11.getData().add(new XYChart.Data<>(monthStrings[i], monthlyJobSubmitted[i]));
        }

        XYChart.Series<String, Number> series21 = new XYChart.Series<>();
        series21.setName("Jobs Killed");
        for (int i = 0; i < monthStrings.length; i++) {
            series21.getData().add(new XYChart.Data<>(monthStrings[i], monthlyJobKilled[i]));
        }
        barChart1.getData().addAll(series11, series21);

        //barchart 2
        CategoryAxis xAxis2 = new CategoryAxis();
        xAxis2.setLabel("Month");
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setLabel("No. of Jobs");
        BarChart<String, Number> barChart2 = new BarChart<>(xAxis2, yAxis2);
        barChart2.setTitle("Rate of Job Killed and Average Job Killed");

        XYChart.Series<String, Number> series12 = new XYChart.Series<>();
        series12.setName("Rate of Job Killed");
        for (int i = 0; i < monthStrings.length; i++) {
            series12.getData().add(new XYChart.Data<>(monthStrings[i], rateJobKilled[i]));
        }

        XYChart.Series<String, Number> series22 = new XYChart.Series<>();
        series22.setName("Average Job Killed");
        for (int i = 0; i < monthStrings.length; i++) {
            series22.getData().add(new XYChart.Data<>(monthStrings[i], averageJobKilled[i]));
        }
        barChart2.getData().addAll(series12, series22);

        BorderPane layout = new BorderPane();
        layout.setTop(table1);
        layout.setLeft(barChart1);
        layout.setRight(barChart2);

        Scene scene = new Scene(layout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Job Killed");
        primaryStage.show();
    }

    public class JobData {

        private String month;
        private int submitted;
        private int killed;
        private double rate;
        private double average;

        public JobData(String month, int submitted, int killed, double rate, double average) {
            this.month = month;
            this.submitted = submitted;
            this.killed = killed;
            this.rate = rate;
            this.average = average;
        }

        public String getMonth() {
            return month;
        }

        public int getSubmitted() {
            return submitted;
        }

        public int getKilled() {
            return killed;
        }

        public double getRate() {
            return rate;
        }

        public double getAverage() {
            return average;
        }
    }
}
