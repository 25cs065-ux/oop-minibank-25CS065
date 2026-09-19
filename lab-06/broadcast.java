interface Urgent{}
interface Notifier{
    void send(String message);
}
class Sms implements Notifier,Urgent{
    @Override
    public void send(String message){
        System.out.println("SMS "+message);
    }
}
class Email implements Notifier{
    @Override
    public void send(String message){
        System.out.println("Email "+message);
    }
}
public class broadcast{
    public static void main(String args[]){
        Notifier[] notifiers={new Sms(), new Email()};
        for(Notifier n:notifiers){
            n.send("SMS Server down");
            if(n instanceof Urgent){
                n.send("Urgent Server down");
            }
        }
    }
}