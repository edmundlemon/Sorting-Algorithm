import csv
import time
import random

class DataPair:
    def __init__(self, id, value):
        self.id = id
        self.value = value

class CsvReader:
    def read_csv(self, csv_file):
        data = []
        try:
            with open(csv_file, 'r') as file:
                csv_reader = csv.reader(file)
                next(csv_reader)  # Skip header if exists
                for row in csv_reader:
                    if len(row) >= 2:
                        data.append(DataPair(int(row[0]), row[1]))
            return data
        except Exception as e:
            print(f"Error reading file: {e}")
            return None

class BinarySearch(CsvReader):
    def binary_search(self, data, target):
        left = 0
        right = len(data) - 1
        
        while left <= right:
            mid = (left + right) // 2
            
            if data[mid].id == target:
                return mid
            elif data[mid].id < target:
                left = mid + 1
            else:
                right = mid - 1
        
        return -1

def main():
    # Initialize variables
    reader = CsvReader()
    data = None
    
    # Get CSV file path from user
    csv_file = input("Enter the path to the CSV file: ")
    data = reader.read_csv(csv_file)
    
    if data is not None:
        binary = BinarySearch()
        
        # Output file name
        filename = f"python_binary_search_{len(data)}.txt"
        
        try:
            with open(filename, 'w') as writer:
                # Test cases
                best_case = data[(len(data) - 1) // 2].id
                worst_case = data[0].id
                min_val = 249
                max_val = 749
                random_index = random.randint(min_val, max_val)
                average_case = data[random_index].id
                
                # Best case timing
                best_start_time = time.perf_counter_ns()
                binary.binary_search(data, best_case)
                best_end_time = time.perf_counter_ns()
                duration = best_end_time - best_start_time
                best_duration_in_seconds = duration / 1000000  # convert to ms
                writer.write(f"Best Case O(1) {best_duration_in_seconds} ms.\n")
                
                # Worst case timing
                worst_start_time = time.perf_counter_ns()
                binary.binary_search(data, worst_case)
                worst_end_time = time.perf_counter_ns()
                duration = worst_end_time - worst_start_time
                worst_duration_in_seconds = duration / 1000000  # convert to ms
                writer.write(f"Worst Case O(log n) {worst_duration_in_seconds} ms.\n")
                
                # Average case timing
                average_start_time = time.perf_counter_ns()
                binary.binary_search(data, average_case)
                average_end_time = time.perf_counter_ns()
                duration = average_end_time - average_start_time
                average_duration_in_seconds = duration / 1000000  # convert to ms
                writer.write(f"Average Case O(log n) {average_duration_in_seconds} ms.\n")

        except IOError as e:
            print(f"Error writing to file: {e}")
    else:
        print("No data to display.")

if __name__ == "__main__":
    main()