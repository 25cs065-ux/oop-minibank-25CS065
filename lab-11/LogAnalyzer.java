import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        Path folder = Path.of("logs");
        String keyword = "ERROR";
        int totalLines = 0;
        int matchingLines = 0;

        try (Stream<Path> files = Files.list(folder)) {
            for (Path file : files.filter(Files::isRegularFile).toList()) {
                BasicFileAttributes attributes =
                        Files.readAttributes(file, BasicFileAttributes.class);

                int fileLines = 0;
                int fileMatches = 0;

                try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileLines++;
                        totalLines++;
                        if (line.contains(keyword)) {
                            fileMatches++;
                            matchingLines++;
                        }
                    }
                }

                System.out.println("File: " + file.getFileName());
                System.out.println("Size: " + attributes.size() + " bytes");
                System.out.println("Lines: " + fileLines);
                System.out.println("Matching lines: " + fileMatches);
                System.out.println();
            }
        }

        System.out.println("Total lines: " + totalLines);
        System.out.println("Lines containing " + keyword + ": " + matchingLines);
    }
}