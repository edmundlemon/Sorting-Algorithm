import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class quick_sort_step extends CsvReader {
    
    public static void quickSort(List<DataPair> data, int low, int high, BufferedWriter writer) throws IOException {
        if (low < high) {
            int pi = partition(data, low, high);

            // Print full snapshot after partition
            writer.write("pi=" + pi + " " + data);
            writer.newLine();

            // Recur on left and right partitions
            quickSort(data, low, pi - 1, writer);
            quickSort(data, pi + 1, high, writer);
        }
    }

    public static int partition(List<DataPair> data, int low, int high) {
        DataPair pivot = data.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (data.get(j).id <= pivot.id) {
                i++;
                // Manual swap instead of Collections.swap()
                DataPair temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
        }
        
        // Manual swap instead of Collections.swap()
        DataPair temp = data.get(i + 1);
        data.set(i + 1, data.get(high));
        data.set(high, temp);
        
        return i + 1; // New pivot index
    }

    public static void main(String[] args) {
        CsvReader reader = new CsvReader();
        int startingRow = 0;
        int endingRow = 0;
        
        List<DataPair> data = null;
        List<DataPair> aux = null;
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to the CSV file: ");
            String csvFile = scanner.nextLine();
            data = reader.readCsv(csvFile);
            
            System.out.print("Enter the starting row: ");
            startingRow = scanner.nextInt();
            System.out.print("Enter the ending row: ");
            endingRow = scanner.nextInt();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        if (data != null) {
            // Extract subset of data
            aux = new ArrayList<>();
            for (int i = startingRow - 1; i < endingRow && i < data.size(); i++) {
                aux.add(data.get(i));
            }
            
            String filename = "quick_sort_step_" + startingRow + "_" + endingRow + ".txt";
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                // Write initial state
                writer.write(aux.toString());
                writer.newLine();
                
                // Perform quick sort with steps
                quickSort(aux, 0, aux.size() - 1, writer);
                
                System.out.println("Quick sort steps saved to: " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No data to display.");
        }
    }
}
