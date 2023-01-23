package testfx;

import javafx.application.Application;
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
import static testfx.MainRt.*;

public class JobKilledTimeRangeBarChart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Get data from timeRange class
        inputData();
        timeRange(filename);

        //table1
        TableView<JobData1> tableT = new TableView<>();
        tableT.setPrefWidth(1000);
        tableT.setPrefHeight(100);

        // Create columns
        TableColumn<JobData1, Integer> submittedColumn = new TableColumn<>("No. of Jobs Submitted");
        submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submitted"));

        TableColumn<JobData1, Integer> killedColumn = new TableColumn<>("No of Jobs Killed");
        killedColumn.setCellValueFactory(new PropertyValueFactory<>("killed"));

        TableColumn<JobData1, Double> rateColumn = new TableColumn<>("Rate of Job Killed in %");
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));

        TableColumn<JobData1, Double> averageColumn = new TableColumn<>("Average Job Killed");
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        // Add columns to table
        tableT.getColumns().addAll(submittedColumn, killedColumn, rateColumn, averageColumn);

        // Add data to table
        tableT.getItems().add(new JobData1(numJobSubmitted, numJobKilled, rateJobKilledTimeRange, averageJobKilledTimeRange));

        submittedColumn.setMinWidth(250);
        killedColumn.setMinWidth(250);
        rateColumn.setMinWidth(250);
        averageColumn.setMinWidth(250);
        
        // creating VBox component
        VBox vBox = new VBox();

        // adding table to the vBox
        vBox.getChildren().add(tableT);

        //barchart1
        stage.setTitle("Jobs Killed");
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Bar chart within " + startDate + " to " + endDate);
        xAxis.setLabel("Type");
        yAxis.setLabel("No. of jobs");

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Jobs Submitted", numJobSubmitted));
        series.getData().add(new XYChart.Data("Jobs Killed", numJobKilled));
        series.getData().add(new XYChart.Data("Average Job Killed", averageJobKilledTimeRange));
        barChart.getData().add(series);

        //barchart2
        CategoryAxis xAxis1 = new CategoryAxis();
        NumberAxis yAxis1 = new NumberAxis();
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
        barChart1.setTitle("Bar chart within " + startDate + " to " + endDate);
        xAxis1.setLabel("Type");
        yAxis1.setLabel("No. of jobs");

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("Rate of Job Killed in %", rateJobKilledTimeRange));
        barChart1.getData().add(series1);

        BorderPane layout = new BorderPane();
        layout.setTop(tableT);
        layout.setLeft(barChart);
        layout.setRight(barChart1);

        Scene scene = new Scene(layout, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public class JobData1 {

        private final int submitted;
        private final int killed;
        private final double rate;
        private final double average;

        public JobData1(int submitted, int killed, double rate, double average) {
            this.submitted = submitted;
            this.killed = killed;
            this.rate = rate;
            this.average = average;
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
