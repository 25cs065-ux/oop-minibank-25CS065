import java.util.*;

public class driver {

    public static void main(String[] args) {

        System.out.println("Please Enter the password to test");

        Scanner sc = new Scanner(System.in);

        String password = sc.next();

        System.out.println(passcheck.checkstrength(password));

        sc.close();
    }
}