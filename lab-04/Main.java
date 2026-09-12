import java.util.*;

public class Main {

    public static void main(String[] args) {

        String[] arr = {
            "12:00 urja Hello",
            "2:00 Panvi How are you",
            "Nandini",
            "Nothing"
        };

        Scanner sc = new Scanner(System.in);

        String keyword = sc.next();

        int match = 0;

        StringBuilder report = new StringBuilder();

        for (String line : arr) {

            String[] parts = chatfilter.splitline(line);

            if (parts == null)
                continue;

            String time = parts[0];
            String name = parts[1];
            String message = parts[2];

            if (chatfilter.hasKeyword(message, keyword)) {

                match++;

                report.append(time)
                      .append(" ")
                      .append(name)
                      .append(": ")
                      .append(message)
                      .append("\n");
            }
        }

        System.out.println("matches: " + match);
        System.out.println(report);

        sc.close();
    }
}