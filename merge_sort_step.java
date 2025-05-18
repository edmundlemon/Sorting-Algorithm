import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class merge_sort_step extends CsvReader {

	public List<DataPair> MergeSortWithSteps(List<DataPair> data, int count){
		if (data.size() <= 1){
			return data;
		}
		int mid = (data.size()+1)/2;

		List<DataPair> left = MergeSortWithSteps(data.subList(0, mid), count + 1);

		List<DataPair> right = MergeSortWithSteps(data.subList(mid, data.size()), count + 1);
		System.out.println("Left Tree " + count);
		System.out.print('[');
		for (DataPair pair : left){
			System.out.print(pair.id + "/" + pair.value + ", ");
		}
		for (DataPair pair : right){
			System.out.print("ID: " + pair.id + "Value: " + pair.value + ", ");
		}
		System.out.print(']');
		System.out.println("");
		System.out.println("");


		return merge(left, right);
	}
	public static void main(String[] args) {
		CsvReader reader = new CsvReader();
		
		List<DataPair> data = null;
		int startingRow = 0;
		int endingRow = 0;
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter the path to the CSV file: ");
			String csvFile = scanner.nextLine();
			data = reader.readCsv(csvFile);
			System.out.print("Enter the starting row: ");
			startingRow = scanner.nextInt();
			System.out.print("Enter the ending row: ");
			endingRow = scanner.nextInt();
			data = reader.readCsvWithStartAndEnd(csvFile, startingRow, endingRow);

			// Now you can merge-sort this data
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		if (data != null ) {

			System.out.println("ID" + data.get(2).id + ", Value: " + data.get(2).value);
			// data = reader.MergeSort(data);
			merge_sort_step sorter = new merge_sort_step();
			data = sorter.MergeSortWithSteps(data, 1);
			System.out.print('[');
			for (DataPair pair : data){
				System.out.print(pair.id + "/" + pair.value + ", ");
			}
			System.out.print(']');
			System.out.println("");
			System.out.println("");

			try {
				reader.writeValuesToTxt(data, "merge_sort_step_" + startingRow + "_" + endingRow + ".txt");
			} catch (IOException e) {
				System.err.println("Error writing to file: " + e.getMessage());
			}
		} else {
			System.out.println("No data to display.");
		}
	}
	
}
