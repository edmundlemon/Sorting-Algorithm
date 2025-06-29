import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class binary_search_step extends CsvReader {
    
    public void binarySearch(List<DataPair> data, int target, BufferedWriter writer) throws IOException {
        int left = 0;
        int right = data.size() - 1;
        int mid;
        
        while (left <= right){
            mid = (left + right) / 2;
            writer.write((mid + 1) + ": " + data.get(mid).id + "/" + data.get(mid).value);
            writer.newLine();
            
            if (data.get(mid).id == target){
                return; // Target found
            }
            else if (data.get(mid).id < target){
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        
        // Target not found
        writer.write("-1");
        writer.newLine();
    }
    
    public static void main(String[] args) {
        CsvReader reader = new CsvReader();
        List<DataPair> data = null;
        int target = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to the CSV file (sorted): ");
            String csvFile = scanner.nextLine();
            data = reader.readCsv(csvFile);
            System.out.print("Enter the Integer that you wish to search: ");
            target = scanner.nextInt();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        if (data != null) {
            binary_search_step binary = new binary_search_step();
            String filename = "binary_search_step_" + target + ".txt";
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                binary.binarySearch(data, target, writer);
                System.out.println("Binary search steps saved to: " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No data to display.");
        }
    }
}
