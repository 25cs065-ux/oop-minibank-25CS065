import java.util.Scanner;
import java.util.Random;

public class RPSLS {

    enum Move {ROCK, PAPER, SCISSORS, LIZARD, SPOCK}

    static int winner(Move a, Move b) {

        if(a==b) {
            return 0;   // Tie
        }

        return switch(a) {

            case SCISSORS ->{
                if(b== Move.PAPER||b==Move.LIZARD)
                    yield 1;
                else
                    yield -1;
            }

            case PAPER ->{
                if(b==Move.ROCK||b==Move.SPOCK)
                    yield 1;
                else
                    yield -1;
            }

            case ROCK ->{
                if(b==Move.LIZARD||b==Move.SCISSORS)
                    yield 1;
                else
                    yield -1;
            }

            case LIZARD ->{
                if(b==Move.SPOCK||b==Move.PAPER)
                    yield 1;
                else
                    yield -1;
            }

            case SPOCK ->{
                if(b==Move.SCISSORS||b==Move.ROCK)
                    yield 1;
                else
                    yield -1;
            }
        };
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;

        System.out.println("Rock-Paper-Scissors-Lizard-Spock");
        System.out.println("Moves: ROCK, PAPER, SCISSORS, LIZARD, SPOCK");

        for(int round=1;round<=5;round++) {

            System.out.println("\nRound " + round);

            Move computerMove=Move.values()[random.nextInt(5)];

            System.out.print("Enter your move: ");
            String input = sc.nextLine().toUpperCase();

            Move playerMove;

            try {
                playerMove = Move.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move! Please enter again.");
                round--;
                continue;
            }

            int result = winner(playerMove, computerMove);

            System.out.println("Your move: " + playerMove);
            System.out.println("Computer move: " + computerMove);

            if(result==1) {
                System.out.println("You win this round!");
                playerScore++;
            } 
            else if(result==-1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            } 
            else{
                System.out.println("It's a tie!");
            }
        }

        System.out.println("\n===== FINAL RESULT =====");

        if(playerScore>computerScore) {
            System.out.println("You win " + playerScore + "-" + computerScore);
        }
        else if(computerScore>playerScore) {
            System.out.println("Computer wins " + computerScore + "-" + playerScore);
        }
        else{
            System.out.println("It's a tie! " + playerScore + "-" + computerScore);
        }

        sc.close();
    }
}

