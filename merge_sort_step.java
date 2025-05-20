import utils.CsvReader;
import utils.CsvReader.DataPair;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class merge_sort_step extends CsvReader {

	public List<DataPair> MergeSortWithSteps(List<DataPair> data, List<List<DataPair>> snapshots){
		if (data.size() <= 1){
			return data;
		}
		int mid = (data.size()+1)/2;

		List<DataPair> left = MergeSortWithSteps(data.subList(0, mid), snapshots);

		List<DataPair> right = MergeSortWithSteps(data.subList(mid, data.size()), snapshots);
		// System.out.println("Left Tree " + count);

		List<DataPair> merged = merge(left, right, snapshots);
		// snapshots.add(new ArrayList<>(merged));
		return merged;
	}
	public void mergeSortInPlace(List<DataPair> list, List<DataPair> aux, int left, int right, List<List<DataPair>> snapshots) {
		if (right - left <= 1) return; // base case

		int mid = (left + right + 1) / 2;

		mergeSortInPlace(list, aux, left, mid, snapshots);
		mergeSortInPlace(list, aux, mid, right, snapshots);

		mergeAndSnapshot(list, aux, left, mid, right, snapshots);
	}


	private void mergeAndSnapshot(List<DataPair> list, List<DataPair> aux, int left, int mid, int right, List<List<DataPair>> snapshots) {
		int i = left, j = mid, k = left;

		while (i < mid && j < right) {
			if (list.get(i).id <= list.get(j).id) {
				aux.set(k++, list.get(i++));
			} else {
				aux.set(k++, list.get(j++));
			}
		}
		while (i < mid) aux.set(k++, list.get(i++));
		while (j < right) aux.set(k++, list.get(j++));

		for (int t = left; t < right; t++) {
			list.set(t, aux.get(t)); // copy back into original
		}

		snapshots.add(new ArrayList<>(list));
	}

	public List<DataPair> merge(List<DataPair> left, List<DataPair> right, List<List<DataPair>> snapshots){
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
		snapshots.add(new ArrayList<>(merged));
		return merged;
	}
	public static void main(String[] args) {
		CsvReader reader = new CsvReader();
		
		List<DataPair> data = null;
		List<DataPair> aux = null;
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
			aux = new ArrayList<>(data);
			// Now you can merge-sort this data
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		if (data != null ) {

			// System.out.println("ID" + data.get(2).id + ", Value: " + data.get(2).value);
			// data = reader.MergeSort(data);
			merge_sort_step sorter = new merge_sort_step();
			List<List<DataPair>> snapshots = new ArrayList<>();
			// data = sorter.MergeSortWithSteps(data, snapshots);
			snapshots.add(new ArrayList<>(data));
			sorter.mergeSortInPlace(data, aux, 0, data.size(), snapshots);
			String filename = "merge_sort_step_" + startingRow + "_" + endingRow + ".txt";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
				for (List<DataPair> snapshot : snapshots) {
					writer.write("[");
					for (int i = 0; i < snapshot.size(); i++) {
						DataPair pair = snapshot.get(i);
						writer.write(pair.id + "/" + pair.value);
						if (i != snapshot.size() - 1) {
							writer.write(", ");
						}
					}
					writer.write("]");
					writer.newLine();
				}
			} catch (IOException e) {
				System.err.println("Error writing snapshots: " + e.getMessage());
			}
		} else {
			System.out.println("No data to display.");
		}
	}
	
}
