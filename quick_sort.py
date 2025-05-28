import csv
import time

class CsvReader:
    class DataPair:
        def __init__(self, id_: int, value: str):
            self.id = int(id_)
            self.value = value

        def __repr__(self):
            return f"({self.id},{self.value})"

    def read_csv(self, filepath):
        data = []
        with open(filepath, newline='', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                if len(row) >= 2:
                    data.append(self.DataPair(row[0], row[1]))
        return data

    def write_values_to_txt(self, data, filename):
        with open(filename, 'w', encoding='utf-8') as f:
            for pair in data:
                f.write(f"{pair.id},{pair.value}\n")


class QuickSort:
    def quick_sort(self, data):
        if len(data) <= 1:
            return data
        pivot_index = len(data) - 1
        pivot = data[pivot_index]
        left = []
        right = []

        for i in range(pivot_index):
            if data[i].id <= pivot.id:
                left.append(data[i])
            else:
                right.append(data[i])

        sorted_data = []
        sorted_data.extend(self.quick_sort(left))
        sorted_data.append(pivot)
        sorted_data.extend(self.quick_sort(right))
        return sorted_data


def main():
    reader = CsvReader()
    try:
        csv_file = input("Enter the path to the CSV file: ")
        data = reader.read_csv(csv_file)
    except Exception as e:
        print(f"Error reading file: {e}")
        return

    if data:
        sorter = QuickSort()
        start_time = time.time_ns()
        data = sorter.quick_sort(data)
        end_time = time.time_ns()
        print(f"Execution time: {end_time - start_time} ns")

        try:
            reader.write_values_to_txt(data, "output_quicksort.txt")
        except Exception as e:
            print(f"Error writing to file: {e}")
    else:
        print("No data to display.")


if __name__ == "__main__":
    main()
