public class carddriver {
    public static void main(String[] args) {

        Card[] cards = new Card[10];

        int count = 0;

        Card c = new Card("Ace", "Spades");

        boolean duplicate = false;

        for (int i = 0; i < count; i++) {
            if (c.equals(cards[i])) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            System.out.println("Duplicate found: " + c);
        } else {
            cards[count] = c;
            count++;
        }

        c = new Card("King", "Hearts");

        duplicate = false;

        for (int i = 0; i < count; i++) {
            if (c.equals(cards[i])) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            System.out.println("Duplicate found: " + c);
        } else {
            cards[count] = c;
            count++;
        }

        c = new Card("Ace", "Spades");

        duplicate = false;

        for (int i = 0; i < count; i++) {
            if (c.equals(cards[i])) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            System.out.println("Duplicate found: " + c);
        } else {
            cards[count] = c;
            count++;
        }
    }
}