import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        long size = scanner.nextLong();
        scanner.close();

        if (size > 2_000_000_000L) {
            System.out.println("Error: Dataset size cannot exceed 2 billion for unique elements.");
            return;
        }

        String filename = "dataset_" + size + ".csv";
        
        // Use memory-efficient approach for better performance with large range
        generateWithRandomSelection(size, filename);
    }
    
    // Memory-efficient approach that randomly selects from 10 billion range
    private static void generateWithRandomSelection(long size, String filename) {
        System.out.println("Generating unique dataset from 2 billion range...");
        Set<Long> used = new HashSet<>();
        Random random = new Random();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 65536)) {
            System.out.println("Generating dataset with " + size + " unique records from range 0-2,000,000,000...");
            System.out.println("Guaranteed 100% unique - no duplicates will be generated!");
            
            long generated = 0;
            while (generated < size) {
                long num = Math.abs(random.nextLong() % 2_000_000_000L);
                
                // Only add if number hasn't been used before
                if (!used.contains(num)) {
                    used.add(num);
                    String randomString = generateRandomString(3, 10);
                    writer.write(num + "," + randomString + "\n");
                    generated++;
                    
                    // Progress indicator
                    if (generated > 0 && generated % 1_000_000 == 0) {
                        System.out.println("Generated " + generated + " unique records (" + 
                            String.format("%.1f", (generated * 100.0) / size) + "%) - " +
                            "Memory usage: " + (used.size() / 1_000_000) + "M numbers tracked");
                    }
                }
            }
            
            System.out.println("Dataset generated successfully: " + filename);
            System.out.println("Total unique records: " + size);
            System.out.println("Integer range: 0 to 2,000,000,000");
            System.out.println("Final memory usage: " + (used.size() / 1_000_000) + "M unique numbers tracked");
            
        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            System.out.println("Out of memory! Dataset size too large for available memory.");
            System.out.println("Try reducing the dataset size or increase JVM heap size with -Xmx flag");
        }
    }
}
