//(a) time range bar chart
package testfx;

import static testfx.MainRt.*;
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

public class JobTimeRangeBarChart extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        inputData();
        getJobTimeRange(filename);
        
        //table
        TableView<Job> tableJT = new TableView<>();
        tableJT.setPrefWidth(400);
        tableJT.setPrefHeight(600);

        // Create columns
        TableColumn<Job, Integer> createdColumn = new TableColumn<>("No. of Jobs Created");
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));

        TableColumn<Job, Integer> endedColumn = new TableColumn<>("No of Jobs Ended");
        endedColumn.setCellValueFactory(new PropertyValueFactory<>("ended"));


        // Add columns to table
        tableJT.getColumns().addAll(createdColumn, endedColumn);

        // Add data to table
        tableJT.getItems().add(new Job(numJobCreated, numJobEnded));
        
        // set column size
        createdColumn.setMinWidth(200);
        endedColumn.setMinWidth(200);
        
        // creating VBox component
        VBox vBox = new VBox();

        // adding table to the vBox
        vBox.getChildren().add(tableJT);

        //bar chart
        stage.setTitle("Jobs created and ended");
        CategoryAxis xAxisx = new CategoryAxis();
        final NumberAxis yAxisx = new NumberAxis();
        final BarChart<String, Number> barChartJT = new BarChart<>(xAxisx, yAxisx);
        barChartJT.setTitle("Jobs created/ended within " + startDate + " to " + endDate);
        xAxisx.setLabel("Job Status");
        yAxisx.setLabel("No. of jobs");


        XYChart.Series seriesx = new XYChart.Series();
        seriesx.getData().add(new XYChart.Data("Jobs Created", numJobCreated));
        seriesx.getData().add(new XYChart.Data("Jobs Ended", numJobEnded));
        barChartJT.getData().add(seriesx);
        
        BorderPane layout = new BorderPane();
        layout.setLeft(tableJT);
        layout.setCenter(barChartJT);

        Scene scene1 = new Scene(layout, 1000, 600);
        stage.setScene(scene1);
        stage.show();
        }
    public class Job{

        private int created;
        private int ended;

        public Job(int created, int ended){
            this.created = created;
            this.ended = ended;
   
        }

        public int getCreated() {
            return created;
        }

        public int getEnded() {
            return ended;
        }
    }
}
