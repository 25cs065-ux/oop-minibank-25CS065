interface switchable {
public void on();
   public void off();
   public boolean isOn();
    default void toggle(){
        if(isOn()){
            off();
        }
        else{
            on();
        }
    }
}
interface SwitchOnRule{
    boolean mayswitchon(switchable device, int hour);
}
//class DayTimeRule implements SwitchOnRule{
  //  public boolean mayswitchon(switchable device, int hour){
    //    return (hour>=6 && hour<=22);
    //}
//}
class Light implements switchable{
    boolean state=false;
    public void on(){
        state=true;
        System.out.println("Light is on");
    }
    public void off(){
        System.out.println("Light is off");
    }
    @Override
    public boolean isOn(){
        return state;
    }

}
class Fan implements switchable{
    boolean state=false;
    public void on(){
        state=true;
        System.out.println("Fan is on");
    }
    public void off(){
        System.out.println("Fan is off");
    }
    @Override
    public boolean isOn(){
        return state;
    }

}
public class remotecontrol{
    public static void main(String args[]){
        switchable[] devices={new Light(), new Fan()};
        for(switchable d:devices){
            d.toggle();
        }
        
        //SwitchOnRule rule=new DayTimeRule();
        SwitchOnRule rule=new SwitchOnRule(){
            public boolean mayswitchon(switchable device, int hour){
        return (hour>=6 && hour<=22);
    }
        };
        if(rule.mayswitchon(devices[0], 24)){
            devices[0].on();
        }
        else{
            System.out.println("Cannot turn on light this time");
        }
    }
}