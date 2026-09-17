abstract class shape {
    abstract double area();
}
class circle extends shape {
    double radius;
    circle(double radius){
        this.radius=radius;
    }
    public double area(){
        return 3.14*radius*radius;
    }
}
class rectangle extends shape{
    double length;
    double width;
    rectangle(double length, double width){
        this.length=length;
        this.width=width;
    }
    public double area(){
        return length*width;
    }
}
class traingle extends shape{
    double base;
    double height;
    traingle(double base, double height){
        this.base=base;
        this.height=height;
    }
    public double area(){
        return 0.5*base*height;
    }
}
public class shapedemo{
    public static void main(String args[]){
        shape[] shapes={new circle(5), new rectangle(6,8), new traingle(3,8)};
        double total=0;
        double largest=-1;
        String label="";
        for(shape s:shapes){
            System.out.println("Area is "+s.area());
            total+=s.area();
            if(largest<s.area()){
                largest=s.area();
                label=s.getClass().getSimpleName();
            }
        }
        System.out.println("The total area is: " + total);
        System.out.println("The largest area is: " + largest + " of Shape " + label);
    }
}
