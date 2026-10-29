
package persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    public static String generate(Path logsFolder, Path reportFile) throws IOException {
        Files.createDirectories(logsFolder);

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        List<String> fileDetails = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsFolder)) {
            for (Path file : stream) {
                if (!Files.isRegularFile(file)
                        || !file.getFileName().toString().endsWith(".log")
                        || Files.size(file) == 0) {
                    continue;
                }

                BasicFileAttributes attributes =
                        Files.readAttributes(file, BasicFileAttributes.class);

                fileDetails.add(
                    "File: " + file.getFileName()
                    + " | Size: " + attributes.size() + " bytes"
                    + " | Last modified: " + attributes.lastModifiedTime()
                );

                try (BufferedReader reader =
                        Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length != 3) {
                            continue;
                        }

                        try {
                            long amount = Long.parseLong(parts[2]);

                            if (amount < 0) {
                                continue;
                            }

                            if (parts[0].equals("DEPOSIT")) {
                                totalDeposits += amount;
                            } else if (parts[0].equals("WITHDRAW")) {
                                totalWithdrawals += amount;
                            }
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                }
            }
        }

        long netChange = totalDeposits - totalWithdrawals;

        StringBuilder report = new StringBuilder();
        report.append("MINIBANK END-OF-DAY REPORT\n");
        report.append("=========================\n\n");
        report.append("LOG FILE DETAILS\n");

        for (String detail : fileDetails) {
            report.append(detail).append('\n');
        }

        report.append("\nTotal deposits: Rs. ").append(totalDeposits).append('\n');
        report.append("Total withdrawals: Rs. ").append(totalWithdrawals).append('\n');
        report.append("Net change: Rs. ").append(netChange).append('\n');

        Path parent = reportFile.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(reportFile, report.toString(), StandardCharsets.UTF_8);

        return report.toString();
    }
}
