import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class dataset_generator {
    public static String generateRandomString(int minLen, int maxLen) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        int length = rand.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(letters.charAt(rand.nextInt(letters.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter dataset size: ");
        int size = scanner.nextInt();
        scanner.close();

        String filename = "dataset_" + size + ".csv";
        HashSet<Integer> generatedIntegers = new HashSet<>();
        Random random = new Random();

        try (FileWriter writer = new FileWriter(filename)) {
            int count = 0;

            while (count < size) {
                int num = random.nextInt(1_000_000_000);
                if (generatedIntegers.add(num)) {
                    String randomString = generateRandomString(3, 10);
                    writer.write(num + "," + randomString + "\n");
                    count++;
                }
            }

            System.out.println("Dataset generated successfully: " + filename);

        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }
    }
}
