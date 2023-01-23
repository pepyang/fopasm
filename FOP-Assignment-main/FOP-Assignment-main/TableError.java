package testfx;

import javax.swing.*;
import java.io.IOException;
import static testfx.MainRt.*;

public class TableError {
    public static void main(String args) throws IOException, InterruptedException {

        // Create table columns
        String[] columnNames = {"Type of Error", "Number of Cases"};

        // Create table data
        Object[][] data = {
                {"Association Error", getErrorCount("error: ","This association")},
                {"Different Configuration Error", getErrorCount("error: ","different slurm.conf")},
                {"Invalid Node Error", getErrorCount("error: ","invalid node specified")},
                {"Invalid QoS Error", getErrorCount("error","Invalid qos")},
                {"Lookup Failure Error", getErrorCount("error","lookup failure for node")},
                {"Not Responding Error", getErrorCount("error","not responding")},
                {"Registered PENDING Error", getErrorCount("error","Registered PENDING")},
                {"Remove Time Error", getErrorCount("error","grp_used_tres_run_secs underflow")},
                {"Security Violation Error", getErrorCount("error","Security violation")},
                {"Socket Error", getErrorCount("error","Socket timed out")},
                {"Underflow Error", getErrorCount("error","underflow (0 1)")},
                {"User Not Found Error", getErrorCount("error","User 548300548 not found")},
                {"Zero Bytes Error", getErrorCount("error","Zero Bytes were transmitted or received")}
        };

        // Create the table
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a frame to hold the table
        JFrame frame = new JFrame("Error Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }
}