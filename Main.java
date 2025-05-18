import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CsvReader reader = new CsvReader();
        
        List<DataPair> data = null;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to the CSV file: ");
            String csvFile = scanner.nextLine();
            data = reader.readCsv(csvFile);
            // Now you can merge-sort this data
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        if (data != null) {

			System.out.println("ID" + data.get(2).id + ", Value: " + data.get(2).value);
			data = reader.MergeSort(data);

            try {
                reader.writeValuesToTxt(data, "output.txt");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
		} else {
			System.out.println("No data to display.");
        }
    }
}