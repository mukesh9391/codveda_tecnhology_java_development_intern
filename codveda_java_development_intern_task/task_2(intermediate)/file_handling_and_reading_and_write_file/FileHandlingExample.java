import java.io.*;

public class FileHandlingExample {
    public static void main(String[] args) {
        String inputFile = "input.txt";  
        String outputFile = "output.txt";

        try {
           
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            int lineCount = 0;
            int wordCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                String[] words = line.trim().split("\\s+");
                if (!line.trim().isEmpty()) {
                    wordCount += words.length;
                }
            }
            reader.close();

           
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write("File Processing Results:\n");
            writer.write("Total Lines: " + lineCount + "\n");
            writer.write("Total Words: " + wordCount + "\n");
            writer.close();

            System.out.println("Processing completed! Results written to " + outputFile);

        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found!");
        } catch (IOException e) {
            System.out.println("Error while reading/writing file: " + e.getMessage());
        }
    }
}
