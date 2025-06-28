import csv
import time
import random

class DataPair:
    def __init__(self, id_val, value):
        self.id = id_val
        self.value = value
    
    def __str__(self):
        return f"{self.id}/{self.value}"

class BinarySearch:
    def read_csv(self, filename):
        """Read CSV file and return list of DataPair objects"""
        data = []
        try:
            with open(filename, 'r', newline='', encoding='utf-8') as file:
                csv_reader = csv.reader(file)
                for row in csv_reader:
                    if len(row) >= 2:
                        data.append(DataPair(int(row[0]), row[1]))
        except FileNotFoundError:
            print(f"Error: File {filename} not found")
        except ValueError as e:
            print(f"Error parsing data: {e}")
        return data
    
    def binary_search(self, data, target):
        """Perform binary search and return index if found, -1 otherwise"""
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
    
    def measure_binary_search(self, data, target):
        """Measure the execution time of binary search in nanoseconds"""
        start_time = time.perf_counter_ns()
        self.binary_search(data, target)
        end_time = time.perf_counter_ns()
        return end_time - start_time

def main():
    # Get input from user
    csv_file = input("Enter the path to the CSV file: ")
    
    # Create binary search instance
    binary_searcher = BinarySearch()
    data = binary_searcher.read_csv(csv_file)
    
    if not data:
        print("No data to process.")
        return
    
    # Sort data by ID for binary search (assuming it might not be sorted)
    data.sort(key=lambda x: x.id)
    
    # Define test cases
    data_size = len(data)
    
    # Best case: middle element (O(1) - found immediately)
    best_case_target = data[data_size // 2].id
    
    # Worst case: element not in array (O(log n) - full traversal)
    worst_case_target = -1  # Use -1 as it's unlikely to be in the dataset
    
    # Average case: random element from the dataset
    min_index = data_size // 4
    max_index = 3 * data_size // 4
    random_index = random.randint(min_index, max_index)
    average_case_target = data[random_index].id
    
    # Measure performance for each case
    best_time = binary_searcher.measure_binary_search(data, best_case_target)
    worst_time = binary_searcher.measure_binary_search(data, worst_case_target)
    average_time = binary_searcher.measure_binary_search(data, average_case_target)
    
    # Convert nanoseconds to seconds
    best_time_seconds = best_time / 1_000_000
    worst_time_seconds = worst_time / 1_000_000
    average_time_seconds = average_time / 1_000_000
    
    # Write results to file
    output_filename = f"binary_search_{data_size}.txt"
    
    try:
        with open(output_filename, 'w') as writer:
            writer.write(f"Binary Search Performance Analysis\n")
            writer.write(f"Dataset size: {data_size}\n")
            writer.write(f"Best Case O(1): {best_time_seconds:.9f} ms\n")
            writer.write(f"Worst Case O(log n): {worst_time_seconds:.9f} ms\n")
            writer.write(f"Average Case O(log n): {average_time_seconds:.9f} ms\n")
            writer.write(f"\nTest Details:\n")
            writer.write(f"Best case target: {best_case_target} (middle element)\n")
            writer.write(f"Worst case target: {worst_case_target} (not found)\n")
            writer.write(f"Average case target: {average_case_target} (random element)\n")
        
        print(f"Performance analysis completed and saved to {output_filename}")
        print(f"Best: {best_time_seconds:.9f}s, Average: {average_time_seconds:.9f}s, Worst: {worst_time_seconds:.9f}s")
        
    except IOError as e:
        print(f"Error writing to file: {e}")

if __name__ == "__main__":
    main()