package testfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import static testfx.MainRt.getErrorCount;

public class ErrorBarGraph extends Application{
    @Override
    public void start(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Error Type");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Error Types");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Error Counts");

        // Add data for Association Error
        series.getData().add(new XYChart.Data<>("Association Error", getErrorCount("error: ","This association")));
        // Add data for Different Configuration Error
        series.getData().add(new XYChart.Data<>("Different Configuration Error", getErrorCount("error: ","different slurm.conf")));
        // Add data for Invalid Node Error
        series.getData().add(new XYChart.Data<>("Invalid Node Error", getErrorCount("error: ","invalid node specified")));
        // Add data for Invalid QoS Error
        series.getData().add(new XYChart.Data<>("Invalid Qos Error", getErrorCount("error","Invalid qos")));
        // Add data for Lookup Failure Error
        series.getData().add(new XYChart.Data<>("Lookup Failure Error", getErrorCount("error","lookup failure for node")));
        // Add data for Not Responding Error
        series.getData().add(new XYChart.Data<>("Not Responding Error", getErrorCount("error","not responding")));
        // Add data for Registered PENDING Error
        series.getData().add(new XYChart.Data<>("Registered PENDING Error", getErrorCount("error","Registered PENDING")));
        // Add data for Remove Time Error
        series.getData().add(new XYChart.Data<>("Remove Time Error", getErrorCount("error","grp_used_tres_run_secs underflow")));
        // Add data for Security Violation Error
        series.getData().add(new XYChart.Data<>("Security Violation Error", getErrorCount("error","Security violation")));
        // Add data for Socket Error
        series.getData().add(new XYChart.Data<>("Socket Error", getErrorCount("error","Socket timed out")));
        // Add data for Underflow Error
        series.getData().add(new XYChart.Data<>("Underflow Error", getErrorCount("error","underflow (0 1)")));
        // Add data for User Not Found Error
        series.getData().add(new XYChart.Data<>("User Not Found Error", getErrorCount("error","User 548300548 not found")));
        // Add data for Zero Bytes Error
        series.getData().add(new XYChart.Data<>("Zero Bytes Error", getErrorCount("error","Zero Bytes were transmitted or received")));

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Error Types");
        primaryStage.show();
    }
}

