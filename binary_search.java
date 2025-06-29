import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Comparator;

public class binary_search extends CsvReader {

	public void binarySearch(List<DataPair> data, int target){
		int left = 0;
		int right = data.size() - 1;
		int mid;
		while (left <= right){
			mid = (left + right) / 2;
			// writer.write((mid + 1)  + ": " + data.get(mid).id + "/" + data.get(mid).value);
			// writer.newLine();
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
		// writer.write("-1");
		// writer.newLine();
		return;
	}
	public static void main(String[] args) {
		// Initialize all the variables.
		CsvReader reader = new CsvReader();
		List<DataPair> data = null;
		int target = 0;

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter the path to the CSV file: ");
			String csvFile = scanner.nextLine();
			data = reader.readCsv(csvFile);
			// Now you can merge-sort this data
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		if (data != null) {
			binary_search binary = new binary_search();
			// Output file name
			String filename = "binary_search_" + data.size() + ".txt";
			// Creating Pointer to write the file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename));){
				int bestCase = data.get((data.size()-1) / 2).id;
				int worstCase = data.get(0).id;
				int min = 249;
				int max = 749;
				int randomIndex = min + (int)(Math.random() * ((max - min) + 1)) ;
				int averageCase = data.get(randomIndex).id;
				long bestStartTime = System.nanoTime();
				binary.binarySearch(data, bestCase);
				long bestEndTime = System.nanoTime();
				long duration = bestEndTime - bestStartTime;
				float bestDurationInSeconds = (float) duration / 1000000; // convert to seconds
				writer.write("Best Case O(1) " + bestDurationInSeconds + " ms.");
				writer.newLine();

				long worstStartTime = System.nanoTime();
				binary.binarySearch(data, worstCase);
				long worstEndTime = System.nanoTime();
				duration = worstEndTime - worstStartTime;
				float worstDurationInSeconds = (float) duration / 1000000; 
				writer.write("Worst Case O(log n) " + worstDurationInSeconds + " ms.");
				writer.newLine();

				long averageStartTime = System.nanoTime();
				binary.binarySearch(data, averageCase);
				long averageEndTime = System.nanoTime();
				duration = averageEndTime - averageStartTime;
				float averageDurationInSeconds = (float) duration / 1000000; // convert to seconds
				writer.write("Average Case O(log n) " + averageDurationInSeconds + " ms.");
				writer.newLine();
				writer.newLine();
				writer.write("Test Details:\n");
				writer.write("Best Case Target: " + bestCase + " Middle Element\n");
				writer.write("Worst Case Target: " + worstCase + " First Element Element\n");
				writer.write("Average Case Target: " + averageCase + " Random Element\n");
			} catch (IOException e){
				e.printStackTrace();
			}
		} else {
				System.out.println("No data to display.");
			}
	}
}
