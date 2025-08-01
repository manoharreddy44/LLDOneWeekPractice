
interface PaymentGateway {
    void submitPayment(double amount);
}

class LegacyPaymentSystem {
    public void processTransaction(String currency, double value){
        System.out.println("Processing the transaction of "+ currency + " with value : "+ value);
    }
}

public class LegacyPaymentAdapter implements PaymentGateway {
    private LegacyPaymentSystem instance;

    LegacyPaymentAdapter(LegacyPaymentSystem instance) {
        this.instance = instance;
    }

    public void submitPayment(double amount) {
        instance.processTransaction("INR", amount);
    }
}

class Main{
    public static void main(String[] args) {
        LegacyPaymentSystem instance = new LegacyPaymentSystem();
        LegacyPaymentAdapter adapter = new LegacyPaymentAdapter(instance);
        adapter.submitPayment(2000.00);
    }
}





