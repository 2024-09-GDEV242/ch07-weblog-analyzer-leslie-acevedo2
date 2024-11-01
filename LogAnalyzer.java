/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @Leslie Acevedo
 * @version 2024.10.29
 */

public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts = new int[24];
    // day access counts 1-28
    private int[] dayCounts = new int[28];
    //month access counts 0-11
    private int [] monthCounts = new int[12];
    // year access count 2018 - 2024
    private int [] yearCounts = new int[7];
     // Use a LogfileReader to access the data.
    private LogfileReader reader;
    
    // added string logFile
    public LogAnalyzer()
    { 
        // Create the reader to obtain the data.
        reader = new LogfileReader("weblog.txt");
    }
    // accesses method 
    public int numberOfAccesses() {
        int total = 0;
        for (int count : hourCounts) {
            total += count;
        }
        return total;
    }
    // analyze daily data 
    public void analyzeDailyData() {
        reader.reset();
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay() -1;
            if (day >=0 && day < 28) {
                dayCounts[day]++;
            }
        }
    }
    // analyze monthly data 
    public void analyzeMonthlyData() {
        reader.reset();
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            if (month >=0 && month < 12) {
                monthCounts[month]++;
            }
        }
    }
    // analyze yearly data 
    public void analyzeYearlyData() {
        reader.reset();
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int year = entry.getYear() -1;
            if (year >= 2018 && year < 2024) {
                yearCounts[year - 2018]++;
            }
        }
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
            // count for the month 
        }
    }
    // busiest and quietest hour 
    public int busiestHour() {
        int busiest = 0;
        for (int i = 1; i < hourCounts.length; i++) {
            if (hourCounts[i] > hourCounts[busiest]) {
                busiest = i;
            }
        }
        return busiest;
    }
    public int quietestHour() {
        int quietest = 0;
        for (int i = 1; i < hourCounts.length; i++) {
            if (hourCounts[i] < hourCounts[quietest]) {
                quietest = i;
            }
        }
        return quietest;
    }
    // busiest two - hour period 
    public int busiestTwoHour() {
        int maxSum = 0;
        int busiestStart = 0;
        for (int i = 0; i <hourCounts.length -1; i++) {
            int sum = hourCounts[i] + hourCounts[i + 1];
            if (sum > maxSum) {
                maxSum = sum;
                busiestStart = i;
            }
        }
        return busiestStart;
    }
    // busiest day
    public int busiestDay() {
        int busiest = 0;
        for (int i = 1; i < dayCounts.length; i++) {
            if (dayCounts[i] > dayCounts[busiest]) {
                busiest = i;
            }
        }
        return busiest + 1;
    }
    // quietest day
    public int quietestDay() {
        int quietest = 0;
        for(int i = 1; i <dayCounts.length; i++) {
            if (dayCounts[i] < dayCounts[quietest]) {
                quietest = i;
            }
        }
        return quietest + 1;
    }
   // busiest month
   public int busiestMonth() {
       int busiest = 0;
       for (int i = 1; i < monthCounts.length; i++) {
           if (monthCounts[i] > monthCounts[busiest]) {
               busiest = i;
           }
       }
       return busiest;
   }
   // quietest month
   public int quietestMonth() {
       int quietest = 0;
       for (int i = 1; i <monthCounts.length; i++) {
           if (monthCounts[i] < monthCounts[quietest]) {
               quietest = i;
           }
       }
       return quietest;
   }
   //busiest year
   public int busiestYear() {
       int busiest = 0;
       for (int i = 1; i <yearCounts.length; i++) {
           if (yearCounts[i] > yearCounts[busiest]) {
               busiest = i;
           }
       }
       return busiest + 2018;
   }
   //quietest year 
   public int quietestYear() {
       int quietest = 0;
       for (int i = 1; i <yearCounts.length; i++) {
           if (yearCounts[i] < yearCounts[quietest]) {
               quietest = i;
           }
       }
       return quietest + 2018;
   }
   // total accesses per month 
   public int totalAccessesPerMonth(int month) {
       if (month >= 0 && month < monthCounts.length) {
           return monthCounts[month];
       }
       return 0;
   }
   // average accesses per month 
   public double averageAccessesPerMonth() {
       int total = 0;
       for (int count : monthCounts) {
           total += count;
       }
       return total / (double) monthCounts.length;
   }
   
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
