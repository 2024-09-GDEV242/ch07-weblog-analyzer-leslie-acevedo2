/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    // day access counts 1-365
    private Map<Integer, Integer> dayCounts;
    //month access counts 1-12
    private Map<Integer, Integer> monthCounts;
    /**
     * Create an object to analyze hourly web accesses.
     */
    // added string logFile
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new HashMap<>();
        monthCounts = new HashMap<>();
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            //assume day of the year 
            int day = entry.getDay();
            //assume month
            int month = entry.getMonth();
            hourCounts[hour]++;
            // count for the day
            dayCounts.put(day, dayCounts.getOrDefault(day, 0) + 1);
            // count for the month 
            monthCounts.put(month, monthCounts.getOrDefault(month, 0) + 1);
        }
    }
    //quietest day
    public int quietestDay() {
        int quietestDay = -1;
        int minAccesses = Integer.MAX_VALUE;
        
        for (Map.Entry<Integer, Integer> entry : dayCounts.entrySet()) {
            if (entry.getValue() < minAccesses) {
                minAccesses = entry.getValue();
                quietestDay = entry.getKey();
            }
        }
        return quietestDay;
    }
    // busiest day
    public int busiestDay() {
        int busiestDay = -1;
        int maxAccesses = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : dayCounts.entrySet()) {
            if (entry.getValue() > maxAccesses) {
                maxAccesses = entry.getValue();
                busiestDay = entry.getKey();
            }
        }
        return busiestDay;
    }
    // total accesses for a given month 
    public int totalAccessesPerMonth(int month) {
        return monthCounts.getOrDefault(month, 0);
    }
    //average number of accesses per month 
    public double averageAccessesPerMonth() {
        int totalAccesses = 0;
        for (int accesses : monthCounts.values()) {
            totalAccesses += accesses;
        }
        return totalAccesses / (double) monthCounts.size();
    }
    //the quietest month based on month counts 
    public int quietestMonth() {
        int quietestMonth = -1;
        int minAccesses = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : monthCounts.entrySet()) {
            if (entry.getValue() < minAccesses) {
                minAccesses = entry.getValue();
                quietestMonth = entry.getKey();
                }
            }
        return quietestMonth;
    }
    // the busiest month based on month counts
    public int busiestMonth() {
        int busiestMonth = -1;
        int maxAccesses = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : monthCounts.entrySet()) {
            if (entry.getValue() > maxAccesses) {
                maxAccesses = entry.getValue();
                busiestMonth = entry.getKey();
            }
        }
        return busiestMonth;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    // number of accesses method 
    public int numberOfAccesses() {
        int total = 0;
        for (int count : hourCounts) {
            total += count;
        }
        return total;
    }
    
    // busiest hour method 
    public int busiestHour() {
        int busiest = 0;
        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > hourCounts[busiest]) {
                busiest = hour;
            }
        }
        return busiest;
    }
    
    // quiestest hour method 
    public int quietestHour() {
        int quietest = 0;
        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < hourCounts[quietest]) {
                quietest = hour;
            }
        }
        return quietest; 
    }
    
    // busiestTwoHour method 
    public int busiestTwoHour() {
        int busiestStart = 0;
        int maxSum = hourCounts[0] + hourCounts[1];
        for (int hour = 1; hour < hourCounts.length - 1; hour++){
            int sum = hourCounts[hour] + hourCounts[hour + 1];
            if (sum > maxSum) {
                maxSum = sum;
                busiestStart = hour;
            }
        }
        return busiestStart;
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
