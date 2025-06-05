import csv
import time

class DataPair:
    def __init__(self, id_val, value):
        self.id = id_val
        self.value = value
    
    def __str__(self):
        return f"{self.id}/{self.value}"
    
    def __lt__(self, other):
        return self.id < other.id
    
    def __le__(self, other):
        return self.id <= other.id
    
    def __eq__(self, other):
        return self.id == other.id

class BinarySearchStep:
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
    
    def merge_sort(self, data):
        """Sort the data using merge sort"""
        if len(data) <= 1:
            return data
        
        mid = len(data) // 2
        left = self.merge_sort(data[:mid])
        right = self.merge_sort(data[mid:])
        
        return self.merge(left, right)
    
    def merge(self, left, right):
        """Merge two sorted lists"""
        merged = []
        i = j = 0
        
        while i < len(left) and j < len(right):
            if left[i].id <= right[j].id:
                merged.append(left[i])
                i += 1
            else:
                merged.append(right[j])
                j += 1
        
        merged.extend(left[i:])
        merged.extend(right[j:])
        return merged
    
    def binary_search_with_steps(self, data, target, writer):
        """Perform binary search with step-by-step logging - Java format"""
        left = 0
        right = len(data) - 1
        
        while left <= right:
            mid = (left + right) // 2
            
            # Write in Java format: "step_number: id/value"
            writer.write(f"{mid + 1}: {data[mid].id}/{data[mid].value}\n")
            
            if data[mid].id == target:
                return mid
            elif data[mid].id < target:
                left = mid + 1
            else:
                right = mid - 1
        
        # Write -1 if not found (Java format)
        writer.write("-1\n")
        return -1

def main():
    # Get input from user
    csv_file = input("Enter the path to the CSV file: ")
    target = int(input("Enter the integer that you wish to search: "))
    
    # Create binary search instance
    binary_searcher = BinarySearchStep()
    data = binary_searcher.read_csv(csv_file)
    
    if not data:
        print("No data to process.")
        return
    
    # Check if data is already sorted (since you're using pre-sorted files)
    is_sorted = all(data[i].id <= data[i+1].id for i in range(len(data)-1))
    
    if not is_sorted:
        print("Sorting data using merge sort...")
        data = binary_searcher.merge_sort(data)
        print("Data sorted successfully.")
    else:
        print("Data is already sorted.")
    
    # Perform binary search with step tracking
    output_filename = f"binary_search_step_{target}.txt"
    
    try:
        with open(output_filename, 'w', encoding='utf-8') as writer:
            result = binary_searcher.binary_search_with_steps(data, target, writer)
        
        print(f"Step-by-step search process saved to {output_filename}")
        
        if result != -1:
            print(f"Target {target} found at index {result}")
        else:
            print(f"Target {target} not found in the dataset")
            
    except IOError as e:
        print(f"Error writing to file: {e}")

if __name__ == "__main__":
    main()