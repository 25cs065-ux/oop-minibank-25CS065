public class thermostat {
    private String location;
    private int temp;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activecount = 0;

    thermostat(String location, int starttemp) {
        this.location = location;
        if (starttemp > MIN && starttemp < MAX) {
            temp = starttemp;
        } else {
            temp = 22;
            activecount++;
        }
    }

    thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (temp < MAX) {
            temp = temp + 1;
        } else {
            System.out.println("Already at maximum(30)");
        }
    }

    public void lower() {
        if (temp > MIN) {
            temp = temp - 1;
        } else {
            System.out.println("Already at minimum(16)");
        }
    }

    public int gettemp() {
        return temp;
    }

    public static int getactivecount() {
        return activecount;
    }

    public static void main(String args[]) {
        thermostat t1 = new thermostat("India", 21);
        thermostat t2 = new thermostat("China");
        for (int i = 0; i < 10; i++) {
            t1.raise();
        }
        for (int i = 0; i < 20; i++) {
            t1.lower();
        }
        System.out.println("Temperature: " + t1.gettemp());
        System.out.println("Active Count: " + t1.getactivecount());
    }
}
