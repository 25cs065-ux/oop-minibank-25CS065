import java.util.*;
class Wm{
    enum coin{ONE,TWO,FIVE,TEN}
    public static void main(String []args){
        final int price=15;
        int total=0;
        coin c=null;
        Scanner sc = new Scanner(System.in);
        while(total<=15){
            System.out.println("Enter the coin: ");
            String input=sc.nextLine();
            try{
                c=coin.valueOf(input.toUpperCase());
            }
            catch(Exception e){
                System.out.println("Invalid coin");
                continue;   
            }
            switch(c){
                case ONE:
                    total+=1;
                    break;  
                case TWO:
                    total+=2;
                    break;
                case FIVE:
                    total+=5;
                    break;
                case TEN:
                    total+=10;
                    break;
            }
             System.out.println("Total amount inserted: "+total);
        }
        System.out.println("Total amount inserted: "+total+"Difference is: "+(price-total));
        sc.close();
    }
}