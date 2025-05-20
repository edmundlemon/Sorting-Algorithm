import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class merge_sort extends CsvReader {
	public List<DataPair> MergeSort(List<DataPair> data){
		if (data.size() <= 1){
			return data;
		}
		int mid = data.size()/2;
		List<DataPair> left = MergeSort(data.subList(0, mid));
		List<DataPair> right = MergeSort(data.subList(mid, data.size()));

		return merge(left, right);
	}

	public List<DataPair> merge(List<DataPair> left, List<DataPair> right){
		List<DataPair> merged = new ArrayList<>();
		int i = 0, j = 0;
		while (i < left.size() && j < right.size()){
			if (left.get(i).id <= right.get(j).id){
				merged.add(left.get(i));
				i++;
			} else {
				merged.add(right.get(j));
				j++;
			}
		}
		while (i < left.size()){
			merged.add(left.get(i));
			i++;
		}
		while (j < right.size()){
			merged.add(right.get(j));
			j++;
		}
		return merged;
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
			data = reader.MergeSort(data);
			System.out.println("Data Size: " + data.size());
			long endTime = System.nanoTime();
			long duration = endTime - startTime; // duration in nanoseconds
			// convert the time to seconds with float
			float durationInSeconds = (float) duration / 1000000000; // convert to seconds
			System.out.println("Sorting took " + durationInSeconds + " seconds.");
			// System.out.println("Sorted data:");

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
