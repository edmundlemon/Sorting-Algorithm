package utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    
    // Static nested class to hold data
    public static class DataPair implements Comparable<DataPair> {
        public final int id;
        public final String value;

        public DataPair(int id, String value) {
            this.id = id;
            this.value = value;
        }
            
        @Override
        public int compareTo(DataPair other) {
            return Integer.compare(this.id, other.id);
        }

        @Override
        public String toString() {
            return id + "/" + value;
        }
    }

    // Reads CSV and returns List<DataPair>
    public List<DataPair> readCsv(String filePath) throws IOException {
        List<DataPair> data = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String value = parts[1].trim();
                data.add(new DataPair(id, value));
            }
        }
        return data;
    }

    public List<DataPair> readCsvWithStartAndEnd(String filePath, int startingRow, int endingRow) throws IOException {
        List<DataPair> data = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 1;
            while ((line = br.readLine()) != null) {
                if (currentRow >= startingRow && currentRow <= endingRow) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0].trim());
                    String value = parts[1].trim();
                    data.add(new DataPair(id, value));
                }
                currentRow++;
            }
        }
        return data;
    }

    public void writeValuesToTxt(List<DataPair> data, String filePath) throws IOException {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(filePath))) {
            for (DataPair pair : data) {
                writer.write(pair.id + "," + pair.value);
                writer.newLine();
            }
        }
    }

    public List<DataPair> MergeSort(List<DataPair> data) {
        if (data.size() <= 1) {
            return data;
        }
        
        int mid = data.size() / 2;
        List<DataPair> left = MergeSort(data.subList(0, mid));
        List<DataPair> right = MergeSort(data.subList(mid, data.size()));
        
        return merge(left, right);
    }
    public List<DataPair> merge(List<DataPair> left, List<DataPair> right) {
        List<DataPair> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()){
            if (left.get(i).id < right.get(j).id){
                merged.add(left.get(i));
                i++;
            }else{
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


}