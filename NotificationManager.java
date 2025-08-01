import java.util.Map;
import java.util.HashMap;

// STEP 1: Fix ISP by creating lean interfaces
interface IMessageFormatter {
    String format(String message);
}

interface INotifier {
    void send(String formattedMessage);
}

// STEP 2: Implement SRP with concrete classes for each responsibility
// Formatters now RETURN the formatted string
class EmailFormatter implements IMessageFormatter {
    @Override
    public String format(String message) {
        return "<html><body>" + message + "</body></html>";
    }
}

class SmsFormatter implements IMessageFormatter {
    @Override
    public String format(String message) {
        return message.replaceAll("\\s+", " ").trim();
    }
}

// Notifiers now handle the final sending
class EmailNotifier implements INotifier {
    @Override
    public void send(String formattedMessage) {
        System.out.println("Sending Email: " + formattedMessage);
    }
}

class SmsNotifier implements INotifier {
    @Override
    public void send(String formattedMessage) {
        System.out.println("Sending SMS: " + formattedMessage);
    }
}

// STEP 3: Fix LSP with the Decorator Pattern (your excellent idea!)
class UrgentNotifierDecorator implements INotifier {
    private final INotifier wrappedNotifier; // Composition over inheritance

    public UrgentNotifierDecorator(INotifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String formattedMessage) {
        String urgentMessage = "!!! URGENT: " + formattedMessage;
        wrappedNotifier.send(urgentMessage);
    }
}

// STEP 4 & 5: High-level class fixing OCP and DIP
// This class is now open for extension (by adding new types to the map)
// but closed for modification. It only depends on abstractions.
class NotificationService {
    private final Map<String, IMessageFormatter> formatters;
    private final Map<String, INotifier> notifiers;

    public NotificationService() {
        // In a real app, this configuration would be injected or loaded from a file
        this.formatters = new HashMap<>();
        this.notifiers = new HashMap<>();

        // Configure standard notifiers
        INotifier emailNotifier = new EmailNotifier();
        INotifier smsNotifier = new SmsNotifier();

        // Configure with decorators for urgent messages
        this.notifiers.put("email", new UrgentNotifierDecorator(emailNotifier));
        this.notifiers.put("sms", smsNotifier); // SMS is not urgent in this example

        this.formatters.put("email", new EmailFormatter());
        this.formatters.put("sms", new SmsFormatter());
    }

    public void sendNotification(String type, String message) {
        IMessageFormatter formatter = formatters.get(type);
        INotifier notifier = notifiers.get(type);

        if (formatter == null || notifier == null) {
            System.out.println("Unsupported notification type: " + type);
            return;
        }

        String formattedMessage = formatter.format(message);
        notifier.send(formattedMessage);
    }
}


// Main class to demonstrate the final design
class Main {
    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService();

        System.out.println("--- Sending a standard SMS ---");
        notificationService.sendNotification("sms", "  Hello this is a test message with extra spaces  ");

        System.out.println("\n--- Sending an URGENT Email ---");
        notificationService.sendNotification("email", "Your build has failed!");

        System.out.println("\n--- Trying to send a PUSH notification ---");
        notificationService.sendNotification("push", "This will fail gracefully.");
    }
}