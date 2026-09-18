
abstract class Media {
    protected String title;
    protected int id;

    Media(String title, int id) {
        this.title = title;
        this.id = id;
    }

    abstract double lateFee(int lateDays);

    void display() {
        System.out.println("Title: " + title);
        System.out.println("ID: " + id);
    }
}

class Book extends Media {

    Book(String title, int id) {
        super(title, id);
    }

    @Override
    double lateFee(int lateDays) {
        return lateDays * 2.0;
    }
}

class DVD extends Media {

    DVD(String title, int id) {
        super(title, id);
    }

    @Override
    double lateFee(int lateDays) {
        return lateDays * 5.0;
    }
}

class Magazine extends Media {

    Magazine(String title, int id) {
        super(title, id);
    }

    @Override
    double lateFee(int lateDays) {
        return lateDays * 1.5;
    }
}

public class MediaLateFee {

    public static void main(String[] args) {

        Media[] mediaList = {
            new Book("Java Programming", 101),
            new DVD("Inception", 102),
            new Magazine("Tech Monthly", 103),
            new Book("Data Structures", 104)
        };

        int[] lateDays = {3, 2, 4, 5};

        double totalFees = 0;

        for (int i = 0; i < mediaList.length; i++) {

            double fee = mediaList[i].lateFee(lateDays[i]);

            System.out.println("Title: " + mediaList[i].title);
            System.out.println("Late Days: " + lateDays[i]);
            System.out.println("Late Fee: Rs. " + fee);

            if (mediaList[i] instanceof DVD) {
                System.out.println("Note: This is a DVD.");
            }

            totalFees += fee;

            System.out.println("-------------------------");
        }

        System.out.println("Total Late Fees: Rs. " + totalFees);
    }
}