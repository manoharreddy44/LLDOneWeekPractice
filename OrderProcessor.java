// STEP 2: Define strategy for OCP
interface PaymentStrategy {
    void processPayment();
}

// Concrete Strategies
class CreditCardStrategy implements PaymentStrategy {
    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment...");
    }
}

class PayPalStrategy implements PaymentStrategy {
    @Override
    public void processPayment() {
        System.out.println("Processing PayPal payment...");
    }
}

// STEP 3: Define Abstractions for DIP
interface IValidator {
    void validate();
}

interface IPaymentProcessor {
    void process(PaymentStrategy strategy);
}

interface INotifier {
    void sendNotification();
}

// STEP 1: Implement SRP with the abstractions
class OrderValidator implements IValidator {
    @Override
    public void validate() {
        System.out.println("Validating the order data...");
    }
}

class PaymentProcessor implements IPaymentProcessor {
    @Override
    public void process(PaymentStrategy strategy) {
        strategy.processPayment();
    }
}

class EmailNotifier implements INotifier {
    @Override
    public void sendNotification() {
        System.out.println("Sending confirmation email...");
    }
}

// High-level service that depends on abstractions
class OrderService {
    private final IValidator validator;
    private final IPaymentProcessor processor;
    private final INotifier notifier;

    // Dependencies are "injected" via the constructor
    public OrderService(IValidator validator, IPaymentProcessor processor, INotifier notifier) {
        this.validator = validator;
        this.processor = processor;
        this.notifier = notifier;
    }

    // THE FIX IS HERE: We accept the strategy for this specific order
    public void processOrder(PaymentStrategy strategy) {
        validator.validate();
        processor.process(strategy); // Pass the strategy down
        notifier.sendNotification();
    }
}

// Main class to run the code
public class OrderProcessor {
    public static void main(String[] args) {
        // 1. Create the low-level components
        IValidator validator = new OrderValidator();
        IPaymentProcessor processor = new PaymentProcessor();
        INotifier notifier = new EmailNotifier();

        // 2. Create the high-level service and inject dependencies
        OrderService orderService = new OrderService(validator, processor, notifier);

        // 3. Process an order using a specific payment strategy
        System.out.println("--- Processing a Credit Card Order ---");
        orderService.processOrder(new CreditCardStrategy());

        System.out.println("\n--- Processing a PayPal Order ---");
        orderService.processOrder(new PayPalStrategy());
    }
}