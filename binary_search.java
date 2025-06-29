import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class binary_search extends CsvReader {

    public void binarySearch(List<DataPair> data, int target){
        int left = 0;
        int right = data.size() - 1;
        int mid;
        
        while (left <= right){
            mid = (left + right) / 2;
            if (data.get(mid).id == target){
                return;
            }
            else if (data.get(mid).id < target){
                left = mid + 1;
            }
            else if (data.get(mid).id > target){
                right = mid - 1;
            }
        }
        return;
    }
    
    public static void main(String[] args) {
        // Initialize all the variables.
        CsvReader reader = new CsvReader();
        List<DataPair> data = null;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to the CSV file: ");
            String csvFile = scanner.nextLine();
            data = reader.readCsv(csvFile);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        if (data != null) {
            binary_search binary = new binary_search();
            // Output file name
            String filename = "binary_search_" + data.size() + ".txt";
            // Creating Pointer to write the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
                // Best case: middle element
                int bestCase = data.get((data.size()-1) / 2).id;
                // Worst case: element not in dataset 
                int worstCase = -1;
                // Average case: random element
                int min = 249;
                int max = 749;
                int randomIndex = min + (int)(Math.random() * ((max - min) + 1));
                int averageCase = data.get(randomIndex).id;
                
                // Measure best case
                long bestStartTime = System.nanoTime();
                binary.binarySearch(data, bestCase);
                long bestEndTime = System.nanoTime();
                long bestDuration = bestEndTime - bestStartTime;
                float bestDurationInMs = (float) bestDuration / 1000000;
                
                // Measure average case
                long averageStartTime = System.nanoTime();
                binary.binarySearch(data, averageCase);
                long averageEndTime = System.nanoTime();
                long averageDuration = averageEndTime - averageStartTime;
                float averageDurationInMs = (float) averageDuration / 1000000;
                
                // Measure worst case
                long worstStartTime = System.nanoTime();
                binary.binarySearch(data, worstCase);
                long worstEndTime = System.nanoTime();
                long worstDuration = worstEndTime - worstStartTime;
                float worstDurationInMs = (float) worstDuration / 1000000;
                
                // Write results in the specified format
                writer.write("Best case time : " + bestDurationInMs + " ms");
                writer.newLine();
                writer.write("Average case time: " + averageDurationInMs + " ms");
                writer.newLine();
                writer.write("Worst case time : " + worstDurationInMs + " ms");
                writer.newLine();
                
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("No data to display.");
        }
    }
}
