import time
import csv

# Divide and Conquer Process
def merge_sort(data):
    if len(data) <= 1:
        return data

    # recursive call
    mid = len(data) // 2
    left = merge_sort(data[:mid]) #recursive call on left side
    right = merge_sort(data[mid:]) #recursive call on right side

    return merge(left, right)

def merge(left, right):
    result = []
    i = j = 0

    while i < len(left) and j < len(right):
        if left[i][0] <= right[j][0]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1

    result.extend(left[i:])
    result.extend(right[j:])
    return result

def get_input_dataset_from_file():
    filename = input("Enter the dataset CSV filename: ").strip()
    dataset = []
    try:
        with open(filename, 'r') as file:
            for line in file:
                if ',' not in line:
                    continue 
                number_str, text = line.strip().split(',')
                number = int(number_str)
                dataset.append((number, text))
    except FileNotFoundError:
        print(f"File '{filename}' not found.")
        return []
    except ValueError:
        print("File contains invalid data. Ensure format is 'number,string' per line.")
        return []
    return dataset

def write_sorted_output_to_csv(sorted_data):
    output_filename = f"python_merge_sort_{len(sorted_data)}.csv"
    try:
        with open(output_filename, mode='w', newline='') as file:
            writer = csv.writer(file)
            for number, text in sorted_data:
                writer.writerow([number, text])
        print(f"Sorted data successfully written to '{output_filename}'.")
    except Exception as e:
        print(f"Error writing to file: {e}")

# Main Process
if __name__ == "__main__":
    dataset = get_input_dataset_from_file()

    if not dataset:
        print("No valid data to sort. Exiting.")
    else:
        start = time.time()
        sorted_dataset = merge_sort(dataset)
        end = time.time()
        runtime = end - start

        if runtime > 60:
            mins = int(runtime // 60)
            secs = runtime % 60
            print(f"\nTotal Sorting Runtime: {mins} minutes {secs:.2f} seconds")
        else:
            print(f"\nTotal Sorting Runtime: {runtime:.7f} seconds")

        write_sorted_output_to_csv(sorted_dataset)
