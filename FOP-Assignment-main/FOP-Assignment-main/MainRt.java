package testfx;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class MainRt extends Application {

    static ArrayList createJobTime = new ArrayList();
    static ArrayList createJobId = new ArrayList();
    static ArrayList endJobTime = new ArrayList();
    static ArrayList endJobId = new ArrayList();
    static ArrayList<Double> exeTime = new ArrayList<>();
    static ArrayList formatted_createJobTime = new ArrayList();
    static ArrayList formatted_endJobTime = new ArrayList();
    static double totalExecutionTime;
    final static String filename = "C:\\Users\\Rachel\\IdeaProjects\\exercise\\src\\extracted_log";
    static int numJobCreated = 0;
    static int numJobEnded = 0;
    static String jobCreated = "Allocate";
    static String jobEnded = "done";
    static int startDate;
    static int endDate;
    final static int[] cMonthlyCount = new int[7];
    final static int[] eMonthlyCount = new int[7];
    final static String[] monthStrings = {"June", "July", "August", "September", "October", "November", "December"};

    public static void getJobTimeRange(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                char[] splitDate = parts[0].toCharArray();
                int date = Character.getNumericValue(splitDate[6]) * 1000 + Character.getNumericValue(splitDate[7]) * 100
                        + Character.getNumericValue(splitDate[9]) * 10 + Character.getNumericValue(splitDate[10]);

                if (date >= startDate && date <= endDate) {
                    if (line.contains(jobCreated)) {
                        numJobCreated++;
                    } else if (line.contains(jobEnded)) {
                        numJobEnded++;
                    }
                }
            }
            System.out.println("+" + "-".repeat(51) + "+");
            System.out.printf("| %-23s | %-23s |\n", "No. of Jobs Created: ", "No. of Jobs Ended: ");
            System.out.println("+" + "-".repeat(51) + "+");
            System.out.printf("| %-23s | %-23s |\n", numJobCreated, numJobEnded);
            System.out.println("+" + "-".repeat(51) + "+");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter start month and date(MMDD): ");
        startDate = scanner.nextInt();
        System.out.println("Enter end month and date(MMDD): ");
        endDate = scanner.nextInt();
    }

    final static int[] monthlyJobSubmitted = new int[7];
    final static int[] monthlyJobKilled = new int[7];
    final static double[] rateJobKilled = new double[7];
    final static double[] averageJobKilled = new double[7];
    static int month;
    static int numJobKilled = 0;
    static int numJobSubmitted = 0;
    static String jobKilled = "REQUEST_KILL_JOB";
    static String jobSubmitted = "_slurm_rpc_submit_batch_job:";
    static double rateJobKilledTimeRange;
    static double averageJobKilledTimeRange;

    public static void getJobKilled(String filename) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineElement = line.split(" ");
                String date = lineElement[0];
                month = Integer.parseInt(date.split("-")[1]);
               try{
                String jobType = lineElement[2];

                if (jobType.contains(jobKilled)) {
                    monthlyJobKilled[month - 6] += 1;
                }
                }catch (ArrayIndexOutOfBoundsException e) {

                }
                String jobType2 = lineElement[1];
                if (jobType2.contains(jobSubmitted)) {
                    monthlyJobSubmitted[month - 6] += 1;
                }
                //rate of job killed
                for (int i = 0; i < monthStrings.length; i++) {
                    rateJobKilled[i] = ((double) monthlyJobKilled[i] / (double) monthlyJobSubmitted[i]) * 100;
                }
                //average job killed
                for (int i = 0; i < monthStrings.length; i++) {
                    if (month == 6 || month == 9 || month == 11) {
                        averageJobKilled[i] = (double) monthlyJobKilled[i] / 30;
                    } else if (month == 7 || month == 8 || month == 10 || month == 12) {
                        averageJobKilled[i] = (double) monthlyJobKilled[i] / 31;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i < monthStrings.length; i++) {
            rateJobKilled[i] = Double.valueOf(decimalFormat.format(rateJobKilled[i]));
            averageJobKilled[i] = Double.valueOf(decimalFormat.format(averageJobKilled[i]));
        }
        System.out.println("+" + "-".repeat(114) + "+");
        System.out.printf("| %-10s | %-20s | %-20s | %-23s | %-26s |\n", "Month", "No. of Jobs Submitted", "No of Jobs Killed", "Rate of Job Killed in %", "Average Job Killed");
        System.out.println("+" + "-".repeat(114) + "+");

        for (int i = 0; i < monthlyJobSubmitted.length; i++) {
            System.out.printf("| %-10s | %-20s  | %-20s | %-23s | %-26s |\n", monthStrings[i], monthlyJobSubmitted[i], monthlyJobKilled[i], rateJobKilled[i], averageJobKilled[i]);
        }
        System.out.println("+" + "-".repeat(114) + "+");
    }

    public static void timeRange(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                char[] splitDate = parts[0].toCharArray();
                int date = Character.getNumericValue(splitDate[6]) * 1000 + Character.getNumericValue(splitDate[7]) * 100
                        + Character.getNumericValue(splitDate[9]) * 10 + Character.getNumericValue(splitDate[10]);

                if (date >= startDate && date <= endDate) {
                    if (line.contains(jobKilled)) {
                        numJobKilled++;
                    } else if (line.contains(jobSubmitted)) {
                        numJobSubmitted++;
                    }
                }

                rateJobKilledTimeRange = (double) numJobKilled / (double) numJobSubmitted;

                averageJobKilledTimeRange = (double) numJobKilled / (endDate - startDate + 1);
            }
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            rateJobKilledTimeRange = Double.valueOf(decimalFormat.format(rateJobKilledTimeRange));
            averageJobKilledTimeRange = Double.valueOf(decimalFormat.format(averageJobKilledTimeRange));

            System.out.println("+" + "-".repeat(108) + "+");
            System.out.printf("| %-23s | %-23s | %-25s | %-26s |\n", "No. of Jobs Submitted: ", "No. of Jobs Killed: ", "Rate of Job Killed in %", "Average Job Killed");
            System.out.println("+" + "-".repeat(108) + "+");
            System.out.printf("| %-23s | %-23s | %-25s | %-26s |\n", numJobSubmitted, numJobKilled, rateJobKilledTimeRange, averageJobKilledTimeRange);
            System.out.println("+" + "-".repeat(108) + "+");

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int jobbyPartitionChart(String abc) {

        int count = 0;

        for (LogMessage logMessage : partitions) {
            if ((logMessage.partition + logMessage.cpu).equals(abc)) {
                count++;
            }
        }
        return count;
    }

    public static void jobbyPartitions() {
        System.out.print("All partitions");

        int countOpteron = 0;
        int countEpyc = 0;
        int countTitan = 0;
        int countV100s = 0;
        int countK10 = 0;
        int countK40c = 0;
        int totalCpu, totalGpu, totalCpuGpu;
        for (LogMessage logMessage : partitions) {
            if ((logMessage.partition + logMessage.cpu).equals("cpu-opteron")) {
                countOpteron++;
            }
            if ((logMessage.partition + logMessage.cpu).equals("cpu-epyc")) {
                countEpyc++;
            }
            if ((logMessage.partition + logMessage.cpu).equals("gpu-titan")) {
                countTitan++;
            }
            if ((logMessage.partition + logMessage.cpu).equals("gpu-v100s")) {
                countV100s++;
            }
            if ((logMessage.partition + logMessage.cpu).equals("gpu-k10")) {
                countK10++;
            }
            if ((logMessage.partition + logMessage.cpu).equals("gpu-k40c")) {
                countK40c++;
            }
        }
        totalCpu = countOpteron + countEpyc;
        totalGpu = countK10 + countK40c + countTitan + countV100s;
        totalCpuGpu = totalGpu + totalCpu;
        System.out.println();
        System.out.println("+------------+-------+");
        System.out.println("| CPU/GPU    | Count |");
        System.out.println("+------------+-------+");
        System.out.println("| Opteron    | " + countOpteron + "  |");
        System.out.println("| Epyc       | " + countEpyc + "  |");
        System.out.println("| Titan      | " + countTitan + "   |");
        System.out.println("| V100s      | " + countV100s + "   |");
        System.out.println("| K10        | " + countK10 + "   |");
        System.out.println("| K40c       | " + countK40c + "   |");
        System.out.println("+------------+-------+");
        System.out.println();
        System.out.println("Total count of cpu: " + totalCpu);
        System.out.println("Total count of gpu: " + totalGpu);
        System.out.println("Total count of partition(exclude backfill): " + totalCpuGpu);
        System.out.println();

        //backfill
        try ( BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            int titan = 0;
            int opteron = 0;
            int epyc = 0;
            int v100s = 0;
            int k40c = 0;
            int k10 = 0;

            while ((line = reader.readLine()) != null) {
                // Use a regex to match the Job ID and user ID in the log message
                Pattern pattern = Pattern.compile("^\\[(.*?)] sched/backfill: _start_job: Started JobId=(\\w+) in (\\w+).*(-\\w+) on (\\w+)");
                Matcher matcher = pattern.matcher(line);
                //sched/backfill: _start_job: Started JobId=42956 in gpu-titan on gpu02

                if (matcher.find()) {
                    // Extract the Job ID and user ID
                    String timestamp = matcher.group(1);
                    String jobId = matcher.group(2);
                    String nodeList = matcher.group(3);
                    String numCPUs = matcher.group(4);
                    String numCPUss = matcher.group(5);
                    if (numCPUs.equals("-titan")) {
                        titan++;
                    }
                    if (numCPUs.equals("-epyc")) {
                        epyc++;
                    }
                    if (numCPUs.equals("-v100s")) {
                        v100s++;
                    }
                    if (numCPUs.equals("-k10")) {
                        k10++;
                    }
                    if (numCPUs.equals("-k40c")) {
                        k40c++;
                    }
                    if (numCPUs.equals("-opteron")) {
                        opteron++;
                    }

                }
            }
            System.out.println("+------------+----------------+");
            System.out.println("| CPU/GPU    | Backfill Count |");
            System.out.println("+------------+----------------+");
            System.out.println("| Opteron    | " + opteron + "            |");
            System.out.println("| Epyc       | " + epyc + "            |");
            System.out.println("| Titan      | " + titan + "             |");
            System.out.println("| V100s      | " + v100s + "             |");
            System.out.println("| K10        | " + k10 + "             |");
            System.out.println("| K40c       | " + k40c + "              |");
            System.out.println("+------------+----------------+");
            count = opteron + epyc + titan + v100s + k10 + k40c;
            System.out.println("Total backfill: " + count);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void jobbyPartitions(int id) {
        System.out.printf("%-15s%25s%15s%20s%15s", "Timestamp", "Job ID", "Node List", "Number of CPUs", "Partition");
        System.out.println("\n----------------------------------------------------------------------------------------------");
        int count = 0;
        boolean findjobId = false;
        for (LogMessage logMessage : partitions) {
            if (logMessage.jobId == id) {
                System.out.printf("%-15s%16d%12s%18d%15s%s", logMessage.timestamp, logMessage.jobId, logMessage.nodeList, logMessage.numCPUs, logMessage.partition, logMessage.cpu);
                System.out.println();
                count++;
                findjobId = true;
            }

        }
        if (findjobId == false) {
            System.out.println("JobId not found due to:");
            printBackfillline(Integer.toString(id));
            printPickbestnodeline(Integer.toString(id));

        }
        System.out.println("Total count: " + count);

    }

    public static void jobbyPartitions(String cpu) {
        System.out.printf("%-15s%25s%15s%20s%15s", "Timestamp", "Job ID", "Node List", "Number of CPUs", "Partition");
        System.out.println("\n----------------------------------------------------------------------------------------------");
        int count = 0;
        boolean findjobId = false;
        for (LogMessage logMessage : partitions) {
            if ((logMessage.partition + logMessage.cpu).equals(cpu)) {
                System.out.printf("%-15s%16d%12s%18d%15s%s", logMessage.timestamp, logMessage.jobId, logMessage.nodeList, logMessage.numCPUs, logMessage.partition, logMessage.cpu);
                System.out.println();
                count++;
                findjobId = true;
            }

        }
        if (findjobId == false) {
            System.out.println("Cannot find JobId.");

        }
        System.out.println("Total count: " + count);
    }

    public static void printBackfillline(String abc) {
        try ( BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                // Use a regex to match the Job ID and user ID in the log message
                Pattern pattern = Pattern.compile("Started JobId=" + abc);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {

                    System.out.println(line);

//
                    System.out.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printPickbestnodeline(String abc) {
        try ( BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                // Use a regex to match the Job ID and user ID in the log message
                Pattern pattern = Pattern.compile(abc + " never");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    System.out.println(line);
                    System.out.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void jobNeverrunnable() {
        System.out.printf("%-15s%24s%19s", "Timestamp", "JobId", "CPU/GPU");
        System.out.println("\n----------------------------------------------------------------------------------------------");
        try ( BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                // Use a regex to match the Job ID and user ID in the log message
                Pattern pattern = Pattern.compile("^\\[(.*?)].*_pick_best_nodes:.*JobId=(\\w+).*never.*runnable.*in.*partition (\\w+).*(-\\w+)");
                //[2022-06-04T11:05:48.955] _pick_best_nodes: JobId=42984 never runnable in partition gpu-k10
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // Extract the Job ID and user ID
                    String timestamp = matcher.group(1);
                    String jobId = matcher.group(2);
                    String jobI = matcher.group(3);
                    String jod = matcher.group(4);
                    count++;
                    System.out.printf("%-15s%16s%15s%s", timestamp, jobId, jobI, jod);

                    System.out.println();
                }
            }
            System.out.println("Total count: " + count);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<LogMessage> partitions = readLogFile(filename);

    private static List<LogMessage> readLogFile(String path) {
        List<LogMessage> logMessages = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Use a regex to match the log message pattern
                Pattern pattern = Pattern.compile("^\\[(.*?)] sched: Allocate JobId=(\\d+).*NodeList=(\\w+).*#CPUs=(\\d+).*Partition=(\\w+).*(-\\w+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // Extract the log message data
                    String timestamp = matcher.group(1);
                    int jobId = Integer.parseInt(matcher.group(2));
                    String nodeList = matcher.group(3);
                    int numCPUs = Integer.parseInt(matcher.group(4));
                    String partition = matcher.group(5);
                    String cpu = matcher.group(6);

                    // Add the log message to the list
                    logMessages.add(new LogMessage(timestamp, jobId, nodeList, numCPUs, partition, cpu));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logMessages;
    }

    private static class LogMessage {

        String timestamp;
        int jobId;
        String nodeList;
        int numCPUs;
        String partition;
        String cpu;

        public LogMessage(String timestamp, int jobId, String nodeList, int numCPUs, String partition, String cpu) {
            this.timestamp = timestamp;
            this.jobId = jobId;
            this.nodeList = nodeList;
            this.numCPUs = numCPUs;
            this.partition = partition;
            this.cpu = cpu;
        }
    }
    public static ArrayList<String> Line = new ArrayList<>();
    public static ArrayList<String> tempuser = new ArrayList<>();

    //method to calculate total error
    public static void totalError() {
        String keyword = "error: ";
        int totalError = 0;
        for (int i = 0; i < Line.size(); i++) {
            if (Line.get(i).contains(keyword)) {
                totalError++;
            }
        }
        System.out.println("Total Error: " + totalError);
    }

    //method to extract the corresponding user
    public static void getUserInfo() {

        String keyword1 = "error: ", keyword2 = "user=";
        for (int i = 0; i < Line.size(); i++) {
            if (Line.get(i).contains(keyword1) && Line.get(i).contains(keyword2)) {
                String[] temp;
                temp = Line.get(i).split(" ");
                tempuser.add(temp[5]);
            }
        }

        // Regular expression pattern to match the user
        Pattern pattern = Pattern.compile("user='([^']+)'");

        // HashMap to store the extracted users and errors
        HashMap<String, Integer> users = new HashMap<>();

        // Iterate through each tempuser
        for (String s : tempuser) {
            if (s != null && !s.isEmpty()) { //check if the tempuser element is empty or not
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    // Extract the user
                    String userString = matcher.group();
                    String user = userString.substring(6, userString.length() - 1);

                    // Check if the user already exists in the map
                    if (users.containsKey(user)) {
                        int count = users.get(user);
                        users.put(user, count + 1); //If the user already exists, increment the error count
                    } else {
                        users.put(user, 1); // If the user doesn't exist, add it to the map with a count of 1
                    }
                }
            }
        }

        // Print the map of users and errors
        System.out.println("Users: " + users);
    }

    //code to read the extracted_log
    public static void readFile() {
        try {
            FileReader fileOpen = new FileReader("C:\\Users\\Rachel\\IdeaProjects\\exercise\\src\\extracted_log"); //open file
            BufferedReader fileReader = new BufferedReader(fileOpen); //read file
            String line;
            while ((line = fileReader.readLine()) != null) {
                Line.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method to get each error
    public static int getErrorCount(String keyword1, String keyword2) {
        int totalError = 0;
        for (int i = 0; i < Line.size(); i++) {
            if (Line.get(i).contains(keyword1) && Line.get(i).contains(keyword2)) {
                totalError++;
            }
        }
        return totalError;
    }

    //method to print different types of error
    public static void displayAll() {
        System.out.println("\n---------------------------------------------------------------------");
        System.out.printf("%-50s%-20s\n", "Types of Error", "Number of Cases");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-50s%-20s\n", "Association Error", getErrorCount("error: ", "This association"));
        System.out.printf("%-50s%-20s\n", "Different Configuration Error", getErrorCount("error: ", "different slurm.conf"));
        System.out.printf("%-50s%-20s\n", "Invalid Node Error", getErrorCount("error: ", "invalid node specified"));
        System.out.printf("%-50s%-20s\n", "Invalid QoS Error", getErrorCount("error", "Invalid qos"));
        System.out.printf("%-50s%-20s\n", "Lookup Failure Error", getErrorCount("error", "lookup failure for node"));
        System.out.printf("%-50s%-20s\n", "Not Responding Error", getErrorCount("error", "not responding"));
        System.out.printf("%-50s%-20s\n", "Registered PENDING Error", getErrorCount("error", "Registered PENDING"));
        System.out.printf("%-50s%-20s\n", "Remove Time Error", getErrorCount("error", "grp_used_tres_run_secs underflow"));
        System.out.printf("%-50s%-20s\n", "Security Violation Error", getErrorCount("error", "Security violation"));
        System.out.printf("%-50s%-20s\n", "Socket Error", getErrorCount("error", "Socket timed out"));
        System.out.printf("%-50s%-20s\n", "Underflow Error", getErrorCount("error", "underflow (0 1)"));
        System.out.printf("%-50s%-20s\n", "User Not Found Error", getErrorCount("error", "User 548300548 not found"));
        System.out.printf("%-50s%-20s\n", "Zero Bytes Error", getErrorCount("error", "Zero Bytes were transmitted or received"));
    }

    public static void getMonthlyJobCreated(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            Scanner logSc = new Scanner(fis);
            while (logSc.hasNextLine()) {
                String[] line = logSc.nextLine().split(" ");
                String date = line[0];
                int month = Integer.parseInt(date.split("-")[1]);
                String jobType = line[1];

                if (jobType.compareTo("_slurm_rpc_submit_batch_job:") == 0) {
                    cMonthlyCount[month - 6] += 1;
                }
            }
            logSc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("+" + "-".repeat(35) + "+");
        System.out.printf("| %-10s | %-20s |\n", "Month", "No. of Jobs Created");
        System.out.println("+" + "-".repeat(35) + "+");

        for (int i = 0; i < cMonthlyCount.length; i++) {
            System.out.printf("| %-10s | %-20s |\n", monthStrings[i], cMonthlyCount[i]);
        }
    }

    public static void getMonthlyJobEnded(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            Scanner logSc = new Scanner(fis);
            while (logSc.hasNextLine()) {
                String[] line = logSc.nextLine().split(" ");
                String date = line[0];
                int month = Integer.parseInt(date.split("-")[1]);
                try {
                    String type = line[3];

                    if (type.compareTo("done") == 0) {
                        eMonthlyCount[month - 6] += 1;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
            logSc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("+" + "-".repeat(35) + "+");
        System.out.printf("| %-10s | %-20s |\n", "Month", "No. of Jobs Ended");
        System.out.println("+" + "-".repeat(35) + "+");

        for (int i = 0; i < eMonthlyCount.length; i++) {
            System.out.printf("| %-10s | %-20s |\n", monthStrings[i], eMonthlyCount[i]);
        }
    }

    final static int[] cleanup = new int[7];

    public static void cleanup(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            Scanner logSc = new Scanner(fis);

            while (logSc.hasNextLine()) {
                String[] line = logSc.nextLine().split(" ");
                String date = line[0];
                int month = Integer.parseInt(date.split("-")[1]);
                String jobType = line[1];

                if (jobType.compareTo("cleanup_completing:") == 0) {
                    cleanup[month - 6] += 1;
                }
            }
            logSc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("+" + "-".repeat(40) + "+");
        System.out.printf("| %-10s | %-20s |\n", "Month", "No. of Cleanup Completing");
        System.out.println("+" + "-".repeat(40) + "+");

        for (int i = 0; i < cleanup.length; i++) {
            System.out.printf("| %-10s | %-25s |\n", monthStrings[i], Integer.toString(cleanup[i]));
        }
    }

    public static void cleanupt(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            Scanner logSc = new Scanner(fis);

            while (logSc.hasNextLine()) {
                try {
                    String[] line = logSc.nextLine().split(" ");
                    String date = line[0];
                    int month = Integer.parseInt(date.split("-")[1]);
                    String[] parts = new String[0];
                    try {
                        parts = logSc.nextLine().split("\\s+");
                    } catch (NoSuchElementException e) {

                    }
                    char[] splitDate = new char[0];

                    splitDate = parts[0].toCharArray();

                    int datee = Character.getNumericValue(splitDate[6]) * 1000 + Character.getNumericValue(splitDate[7]) * 100
                            + Character.getNumericValue(splitDate[9]) * 10 + Character.getNumericValue(splitDate[10]);

                    if (datee >= startDate && datee <= endDate) {
                        if (logSc.nextLine().contains("cleanup_completing:")) {
                            cleanup[month - 6] += 1;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException a) {

                }

            }
            logSc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("+" + "-".repeat(40) + "+");
        System.out.printf("| %-10s | %-20s |\n", "Month", "No. of Cleanup Completing");
        System.out.println("+" + "-".repeat(40) + "+");

        for (int i = 0; i < cleanup.length; i++) {
            System.out.printf("| %-10s | %-25s |\n", monthStrings[i], Integer.toString(cleanup[i]));
        }
    }

    public static void input() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter start date (MMDD):");
        startDate = sc.nextInt();
        System.out.println("Enter end date (MMDD): ");
        endDate = sc.nextInt();
    }

    private static class AverageExTime {

        public static void readFile(String filename) {
            try ( FileReader fileOpen = new FileReader(filename); //open file
                      BufferedReader fileReader = new BufferedReader(fileOpen)) { //read file
                String line;
                while ((line = fileReader.readLine()) != null) {
                    int num = line.split(" ").length;
                    String[] content = new String[num];
                    content = line.split(" ");

                    // Create job
                    for (int i = 0; i < content.length; i++) {
                        if (content[i].equals("Allocate")) {
                            content[0] = content[0].replaceAll("[\\[\\](){}]", "");
                            createJobTime.add(content[0]);
                            createJobId.add(content[i + 1]);
                        }

                        // End job
                        if (content[i].equals("done")) {
                            content[0]=content[0].replaceAll("[\\[\\](){}]", "");
                            endJobTime.add(content[0]);
                            endJobId.add(content[i - 1]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static LocalDateTime convertToLDT(String dateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return LocalDateTime.parse(dateTime, formatter);
        }
    }

    public static void averageExecutionTime() {
        int No = 0;
        String[] createJobTime2 = new String[createJobTime.size()];
        String[] endJobTime2 = new String[endJobTime.size()];
        createJobTime.toArray(createJobTime2);
        endJobTime.toArray(endJobTime2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        for (int i = 0; i < createJobId.size(); i++) {
            for (int j = 0; j < endJobId.size(); j++) {
                if (createJobId.get(i).equals(endJobId.get(j))) {
                    LocalDateTime createTime = LocalDateTime.parse(createJobTime2[i], formatter);
                    LocalDateTime endTime = LocalDateTime.parse(endJobTime2[j], formatter);
                    long milliBetween = ChronoUnit.MILLIS.between(createTime, endTime);

                    if (milliBetween < 0) {
                        milliBetween *= -1;
                    }

                    double milli = (double) milliBetween;

                    exeTime.add(milli);

                    double output = (double) ((milliBetween / 1000.0) / 60);
                    No++;

                    totalExecutionTime += (double) ((milliBetween / 1000.0));
                }
            }
        }
        System.out.println("Total number of completed Job : " + No);

        System.out.printf("Total execution time : %.2f Days\n", ((totalExecutionTime / 3600) / 24));
        System.out.printf("Total execution time : %.2f Hours\n", totalExecutionTime / 3600);
        System.out.printf("Total execution time : %.2f Mins\n", totalExecutionTime / 60);
        System.out.printf("Total execution time : %.2f Seconds\n", totalExecutionTime);

        System.out.println();

        System.out.printf("Average execution time : %.2f Days\n", (((totalExecutionTime / 3600) / 24) / No));
        System.out.printf("Average execution time : %.2f Hours\n", (totalExecutionTime / 3600) / No);
        System.out.printf("Average execution time : %.2f Mins\n", (totalExecutionTime / 60) / No);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JobTimeRangeBarChart jobTimeRange = new JobTimeRangeBarChart();
        MergedJobRateBarChart jobKilledMonthly = new MergedJobRateBarChart();
        JobKilledTimeRangeBarChart jobKilledTR = new JobKilledTimeRangeBarChart();
        ErrorBarGraph obj = new ErrorBarGraph();
        CleanUpMonthly monthly = new CleanUpMonthly();
        JobCreatedEndedMonthly job = new JobCreatedEndedMonthly();
        CleanUpTimeRange time = new CleanUpTimeRange();
        AverageExTime AET = new AverageExTime();
        JobPartition jpartition = new JobPartition();
        
        Scanner input = new Scanner(System.in);

        int command = 0;
        int detail = 0;
        int others = 0;
        int jobkill = 0;

        System.out.println("                   FOP Assignment                       ");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Total number of completed and ended jobs");
        System.out.println("2. Total number of jobs by partition");
        System.out.println("3. Number of jobs causing error and the corresponding user");
        System.out.println("4. Average execution time of the jobs submitted to UMHPC");
        System.out.println("5. Other statistical data");
        System.out.println("###Any number to QUIT");
        System.out.print("Command -> ");
        command = input.nextInt();

        while (command > 0 && command <= 6) {
            System.out.println();

            if (command == 1) {
                System.out.println("1. Monthly");
                System.out.println("2. Time Range");
                System.out.println("command -> ");
                detail = input.nextInt();

                if (detail == 1) {
                    job.launch(JobCreatedEndedMonthly.class, args);
                    break;
                } else if (detail == 2) {
                    jobTimeRange.launch(JobTimeRangeBarChart.class, args);
                    break;
                }
                
            } else if (command == 2) {
                System.out.println("1. All partitions" + "\n2. By JobId" + "\n3. By partition" + "\n4. Never runnable job.");
                int command1 = input.nextInt();
                if (command1 == 1) {
                    jobbyPartitions();
                    jpartition.launch(JobPartition.class, args);
                    break;
                }
                if (command1 == 2) {
                    System.out.println("Enter JobId:");
                    int jobid = input.nextInt();
                    jobbyPartitions(jobid);
                    
                }
                if (command1 == 3) {
                    System.out.println("Choose the partition:\n1. Cpu-Opteron\n2. Cpu-Epyc\n3. GPU-titan\n4. GPU-k40c\n5. GPU-k10\n6. GPU-V100s");
                    int partition = input.nextInt();
                    String partitionname = "";
                    if (partition == 1) {
                        partitionname = "cpu-opteron";
                    }
                    if (partition == 2) {
                        partitionname = "cpu-epyc";
                    }
                    if (partition == 3) {
                        partitionname = "gpu-titan";
                    }
                    if (partition == 4) {
                        partitionname = "gpu-k40c";
                    }
                    if (partition == 5) {
                        partitionname = "gpu-k10";
                    }
                    if (partition == 6) {
                        partitionname = "gpu-v100s";
                    }
                    jobbyPartitions(partitionname);
                    
                }
                if (command1 == 4) {
                    jobNeverrunnable();
                   
                }
                System.out.println("\n");
               
            } else if (command == 3) {
                readFile();
                totalError();
                getUserInfo();
                displayAll();
                TableError.main("");
                obj.launch(ErrorBarGraph.class);
                break;

            } else if (command == 4){
                AET.readFile("C:\\Users\\Rachel\\IdeaProjects\\exercise\\src\\extracted_log");
                averageExecutionTime();
                System.out.println();

            }
            else if (command == 5) {
                System.out.println("1. Number of jobs clean up completing");
                System.out.println("2. Job Killed");
                System.out.print("command -> ");
                //if got others only add
                others = input.nextInt();

                if (others == 1) {
                    System.out.println("1. Monthly");
                    System.out.println("2. Time Range");
                    System.out.print("command -> ");
                    detail = input.nextInt();

                    if (detail == 1) {
                        monthly.launch(CleanUpMonthly.class, args);
                        break;
                    } else if (detail == 2) {
                        time.launch(CleanUpTimeRange.class, args);
                        break;
                    }

                } else if (others == 2) {
                    System.out.println("1. Monthly");
                    System.out.println("2. Time Range");
                    System.out.println("command -> ");
                    jobkill = input.nextInt();

                    if (jobkill == 1) {
                        jobKilledMonthly.launch(MergedJobRateBarChart.class, args);
                        break;
                  
                    } else if (jobkill == 2) {
                        jobKilledTR.launch(JobKilledTimeRangeBarChart.class, args);
                        break;
                    }
                }
            }
        System.out.println("                   FOP Assignment                       ");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Total number of completed and ended jobs");
        System.out.println("2. Total number of jobs by partition");
        System.out.println("3. Number of jobs causing error and the corresponding user");
        System.out.println("4. Average execution time of the jobs submitted to UMHPC");
        System.out.println("5. Other statistical data");
        System.out.println("###Any number to QUIT");
        System.out.print("Command -> ");
        command = input.nextInt();
        }

    }

    @Override
    public void start(Stage stage) throws Exception {

    }

}