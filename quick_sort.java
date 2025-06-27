import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class quick_sort {
	public List<DataPair> QuickSort(List<DataPair> data){
		
		if (data.size() <= 1){
			return data;
		}
		int pivotIndex = data.size() - 1;
		DataPair pivot = data.get(pivotIndex);
		List<DataPair> left = new ArrayList<>();
		List<DataPair> right = new ArrayList<>();
		for (int i = 0; i < pivotIndex; i++){
			if (data.get(i).id <= pivot.id){
				left.add(data.get(i));
			} else {
				right.add(data.get(i));
			}
		}
		List<DataPair> sorted = new ArrayList<>();
		sorted.addAll(QuickSort(left));
		sorted.add(pivot);
		sorted.addAll(QuickSort(right));
		return sorted;
	}

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

			// System.out.println("ID" + data.get(2).id + ", Value: " + data.get(2).value);
			long startTime = System.nanoTime();
			data = new quick_sort().QuickSort(data);
			long endTime = System.nanoTime();
			float timeTaken = (float) (endTime - startTime)/1000000000;
			System.out.println("Execution time: " + timeTaken + " seconds");
			
			try {
				reader.writeValuesToTxt(data, "output_quicksort.txt");
			} catch (IOException e) {
				System.err.println("Error writing to file: " + e.getMessage());
			}
		} else {
			System.out.println("No data to display.");
		}
		}
}
