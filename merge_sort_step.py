import time
import csv

# Divide and Conquer Process
def merge_sort(data, aux, left, right, snapshots):

    if right - left <= 1:
        return
    # Calculate the mid-point
    mid = (left + right + 1) // 2
    # recursive call
    merge_sort(data, aux, left, mid, snapshots) #recursive call on left side
    merge_sort(data, aux, mid, right, snapshots) #recursive call on right side

    # return merge(left, right)
    return merge_and_snapshot(data, aux, left, mid, right, snapshots)

def merge_and_snapshot(data, aux, left, mid, right, snapshots):
    i, j, k = left, mid, left
    while i < mid and j < right:
        if data[i][0] <= data[j][0]:
            aux[k] = data[i]
            i += 1
        else:
            aux[k] = data[j]
            j += 1
        k += 1

    while i < mid:
        aux[k] = data[i]
        i += 1
        k += 1

    while j < right:
        aux[k] = data[j]
        j += 1
        k += 1

    for t in range(left, right):
        data[t] = aux[t]
    # Take a snapshot of the current state of the data
    snapshots.append(list(data))

def merge(data, aux, left, right):
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
    for item in result:
        print(f"Sorted Item: {item[0]}, {item[1]}")
    print(f"Total Sorted Items: {len(result)}")
    return result

def get_input_dataset_from_file(filename, startingRow, endingRow):
    dataset = []
    try:
        with open(filename, 'r') as file:
            # i = startingRow
            i = 0
            # Skip lines until the starting row
            while i < startingRow-1:
                next(file)
                i += 1
            # Read the dataset from the file
            for line in file:
                if i >= endingRow and endingRow > 0:
                    break
                if ',' not in line:
                    continue 
                i += 1
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

def write_sorted_output_to_csv(sorted_data, startingRow, endingRow):
    output_filename = f"python_merge_sort_Step_{startingRow}_{endingRow}.txt"
    try:
        with open(output_filename, mode='w', newline='') as file:
            # writer = csv.writer(file)
            for data in sorted_data:
                file.write("[")
                for number, text in data:
                    file.write(f"{number}/{text}, ")
                if data:
                    file.seek(file.tell() - 2)  # Move back to remove last ", "
                file.write("]\n")
        print(f"Sorted data successfully written to '{output_filename}'.")
    except Exception as e:
        print(f"Error writing to file: {e}")

# Main Process
if __name__ == "__main__":
    # Reading Input Parameters
    filename = input("Enter the dataset CSV filename: ").strip()
    startingRow = int(input("Enter the starting row number (1-based index): ").strip())
    if startingRow < 0:
        print("Starting row number must be 1 or greater. Exiting.")
        exit()
    endingRow = int(input("Enter the ending row number (1-based index, 0 for no limit): ").strip())
    if endingRow < 0:
        print("Ending row number must be 0 or greater. Exiting.")
        exit()
    dataset = []
    dataset = get_input_dataset_from_file(filename, startingRow, endingRow)
    aux = dataset.copy()
    # Creating smpty list to hold snapshotscls

    snapshots = []
    if not dataset:
        print("No valid data to sort. Exiting.")
    else:
        snapshots.append(list(dataset))
        start = time.time()
        # Ensure 'right' does not exceed the length of the dataset
        # right_bound = min(len(dataset), endingRow - startingRow + 1 if endingRow > 0 else len(dataset))
        right_bound = len(dataset)
        sorted_dataset = merge_sort(dataset, aux, 0, right_bound, snapshots)
        end = time.time()
        runtime = end - start

        if runtime > 60:
            mins = int(runtime // 60)
            secs = runtime % 60
            print(f"\nTotal Sorting Runtime: {mins} minutes {secs:.2f} seconds")
        else:
            print(f"\nTotal Sorting Runtime: {runtime:.7f} seconds")

        write_sorted_output_to_csv(snapshots, startingRow, endingRow)
