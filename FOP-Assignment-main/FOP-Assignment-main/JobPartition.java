package testfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import static testfx.MainRt.*;

public class JobPartition extends Application{
    public void start(Stage stage) {
        stage.setTitle("CPU/GPU Count");

        // Create x-axis and y-axis
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Create bar chart
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("CPU/GPU Count");

        // Create data for chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Opteron", jobbyPartitionChart("cpu-opteron")));
        series.getData().add(new XYChart.Data<>("Epyc", jobbyPartitionChart("cpu-epyc")));
        series.getData().add(new XYChart.Data<>("Titan", jobbyPartitionChart("gpu-titan")));
        series.getData().add(new XYChart.Data<>("V100s", jobbyPartitionChart("gpu-v100s")));
        series.getData().add(new XYChart.Data<>("K10", jobbyPartitionChart("gpu-k10")));
        series.getData().add(new XYChart.Data<>("K40c", jobbyPartitionChart("gpu-k40c")));

        // Add data to chart
        barChart.getData().add(series);

        // Add chart to scene and display
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
