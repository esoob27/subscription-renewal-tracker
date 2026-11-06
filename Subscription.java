import java.time.LocalDate;

public class Subscription {
    private final String name;
    private final double price;
    private final String billingCycle;
    private final LocalDate nextRenewalDate;

    public Subscription(String name, double price, String billingCycle,
                        LocalDate nextRenewalDate) {
        this.name = name;
        this.price = price;
        this.billingCycle = billingCycle;
        this.nextRenewalDate = nextRenewalDate;
    }

    public LocalDate getNextRenewalDate() {
        return nextRenewalDate;
    }

    public double getMonthlyCost() {
        if (billingCycle.equals("yearly")) {
            return price / 12.0;
        }
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s | $%.2f (%s) | Renews %s",
                name, price, billingCycle, nextRenewalDate);
    }
}
