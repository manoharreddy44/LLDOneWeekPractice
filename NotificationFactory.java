interface INotification {
    void send();
}

class EmailNotification implements INotification{
    public void send() {
        System.out.println("Sending an Email...");
    }
}
class SmsNotification implements INotification{
    public void send() {
        System.out.println("Sending an Sms...");
    }
}
class PushNotification implements INotification{
    public void send() {
        System.out.println("Pushing Notification...");
    }
}



public class NotificationFactory {
    public static INotification createNotification(String channel) {
        INotification instance;
        switch (channel) {
            case "EMAIL" : {
                instance = new EmailNotification();
            } break;
            case "SMS" : {
                instance = new SmsNotification();
            } break;

            case "PUSH" : {
                instance = new PushNotification();
            } break;

            default: instance = null;
        }

        return instance;
    }
}

class Main {
    public static void main(String[] args) {
        INotification email = NotificationFactory.createNotification("EMAIL");
        INotification sms = NotificationFactory.createNotification("SMS");
        INotification push = NotificationFactory.createNotification("PUSH");

        email.send();
        sms.send();
        push.send();

    }
}
