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

public class binary_search_step extends CsvReader {
	public List<DataPair> MergeSort(List<DataPair> data){
		if (data.size() <= 1){
			return data;
		}
		int mid = data.size()/2;
		List<DataPair> left = MergeSort(data.subList(0, mid));
		List<DataPair> right = MergeSort(data.subList(mid, data.size()));

		return merge(left, right);
	}

	public void binarySearch(List<DataPair> data, int target, BufferedWriter writer) throws IOException {
		int left = 0;
		int right = data.size() - 1;
		int mid;
		while (left <= right){
			mid = (left + right) / 2;
			writer.write((mid + 1)  + ": " + data.get(mid).id + "/" + data.get(mid).value);
			writer.newLine();
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
		writer.write("-1");
		writer.newLine();
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
			System.out.print("Enter the Integer that you wish to search: ");
			target = scanner.nextInt();
			// Now you can merge-sort this data
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		if (data != null) {
			data = reader.MergeSort(data);
			binary_search_step binary = new binary_search_step();
			// Output file name
			String filename = "binary_search_step_" + target + ".txt";
			// Creating Pointer to write the file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename));){
				binary.binarySearch(data, target, writer);
			} catch (IOException e){
				e.printStackTrace();
			}
		} else {
				System.out.println("No data to display.");
			}
	}
}
