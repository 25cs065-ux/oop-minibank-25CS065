import java.util.*;
record Vehicle(String number, String type){}
class veh{
    public static void main(String []args){
        Vehicle v=new Vehicle("123", "bike");
        System.out.println(v);
        int totaltoll=0;
        int car=0;
        int bus=0;
        int bike=0;
        Scanner sc = new Scanner(System.in);
        while(true){
            String type=sc.next();
            if(type.equalsIgnoreCase("done")){
                break;
            }
            String number=sc.next();
            v=new Vehicle(number, type);
            int toll=0;
            switch(v.type()){
                case "Bike":
                    toll=10;
                    bike++;
                    break;
                case "Car":
                    toll=20;
                    car++;
                    break;
                case "Bus":
                    toll=30;
                    bus++;
                    break;
                default: toll=0;
            }
            System.out.println(v.type()+" passed with toll= "+ toll);
            totaltoll+=toll;
        }
        System.out.println("Total Bikes passed: "+bike);
        System.out.println("Total Cars passed: "+car); 
        System.out.println("Total Buses passed: "+bus);
        System.out.println("Total Toll collected: "+totaltoll);
        sc.close();
    }
}