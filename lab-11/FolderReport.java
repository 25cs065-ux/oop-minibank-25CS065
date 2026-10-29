import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class FolderReport {
    public static void main(String[] args) throws IOException {
        Path folder = Path.of("logs");
        Path report = Path.of("folder-report.txt");

        Files.createDirectories(folder);

        try (Stream<Path> paths = Files.walk(folder);
             BufferedWriter writer = Files.newBufferedWriter(report, StandardCharsets.UTF_8)) {

            for (Path path : paths.toList()) {
                if (Files.isRegularFile(path)) {
                    BasicFileAttributes attributes =
                            Files.readAttributes(path, BasicFileAttributes.class);

                    String details = "Name: " + path.getFileName()
                            + ", Size: " + attributes.size()
                            + " bytes, Modified: " + attributes.lastModifiedTime();

                    writer.write(details);
                    writer.newLine();
                    System.out.println(details);
                }
            }
        }

        System.out.println("Report saved to " + report.toAbsolutePath());
    }
}