import csv
from typing import List, Tuple

class CsvReader:
    class DataPair:
        def __init__(self, key: str, value: str):
            self.key = key
            self.value = value

        def __lt__(self, other):
            return self.key < other.key

        def __le__(self, other):
            return self.key <= other.key

        def __repr__(self):
            return f"({self.key},{self.value})"

    def read_csv_with_start_and_end(self, filepath: str, start: int, end: int) -> List['CsvReader.DataPair']:
        data = []
        with open(filepath, newline='', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            rows = list(reader)[start:end+1]
            for row in rows:
                if len(row) >= 2:
                    data.append(self.DataPair(row[0], row[1]))
        return data


def quick_sort(data: List[CsvReader.DataPair], low: int, high: int, writer):
    if low < high:
        pi = partition(data, low, high)

        # Print full snapshot after partition
        writer.write(f"pi={pi} {data}\n")

        quick_sort(data, low, pi - 1, writer)
        quick_sort(data, pi + 1, high, writer)


def partition(data: List[CsvReader.DataPair], low: int, high: int) -> int:
    pivot = data[high]
    i = low - 1

    for j in range(low, high):
        if data[j] <= pivot:
            i += 1
            data[i], data[j] = data[j], data[i]

    data[i + 1], data[high] = data[high], data[i + 1]
    return i + 1


def main():
    reader = CsvReader()
    starting_row = int(input("Enter the starting row: "))
    ending_row = int(input("Enter the ending row: "))
    csv_file = input("Enter the path to the CSV file: ")

    try:
        data = reader.read_csv_with_start_and_end(csv_file, starting_row, ending_row)
        aux = list(data)  # Unused but included as per original
    except Exception as e:
        print(f"Error reading file: {e}")
        return

    if data:
        filename = f"quick_sort_step_{starting_row}_{ending_row}.txt"
        try:
            with open(filename, 'w', encoding='utf-8') as writer:
                writer.write(f"{data}\n")  # initial state
                quick_sort(data, 0, len(data) - 1, writer)
        except Exception as e:
            print(f"Error writing to file: {e}")
    else:
        print("No data to display.")


if __name__ == "__main__":
    main()
