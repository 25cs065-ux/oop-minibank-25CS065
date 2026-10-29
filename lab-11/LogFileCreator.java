import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class LogFileCreator {
    public static void main(String[] args) throws IOException {
        Path folder = Path.of("logs");
        Files.createDirectories(folder);

        Files.writeString(folder.resolve("app1.txt"),
                "Application started\nERROR: Connection failed\nApplication stopped\n");

        Files.writeString(folder.resolve("app2.txt"),
                "User logged in\nWARNING: Low memory\nERROR: File not found\n");

        Files.writeString(folder.resolve("app3.txt"),
                "System ready\nUser logged out\n");

        System.out.println("Log files created successfully.");
    }
}