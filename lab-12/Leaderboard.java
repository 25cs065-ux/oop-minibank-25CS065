
import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {
        HashMap<String, Integer> scores = new HashMap<>();

        scores.put("Khushi", 950);
        scores.put("Aarav", 850);
        scores.put("Priya", 950);
        scores.put("Rahul", 700);
        scores.put("Neha", 900);

        int topN = 3;

        List<Map.Entry<String, Integer>> players = new ArrayList<>(scores.entrySet());

        players.sort((a, b) -> {
            int result = b.getValue().compareTo(a.getValue());

            if (result == 0) {
                return a.getKey().compareTo(b.getKey());
            }

            return result;
        });

        System.out.println("Top " + topN + " Players:");

        for (int i = 0; i < Math.min(topN, players.size()); i++) {
            System.out.println(
                players.get(i).getKey() + " : " + players.get(i).getValue()
            );
        }
    }
}
