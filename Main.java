import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Subscription> subscriptions = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Subscription Renewal Tracker");

            while (true) {
                printMenu();
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addSubscription(scanner, subscriptions);
                        break;
                    case "2":
                        printSubscriptions(subscriptions);
                        break;
                    case "3":
                        printMonthlySpending(subscriptions);
                        break;
                    case "4":
                        printUpcomingRenewals(subscriptions);
                        break;
                    case "5":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Please choose a number from 1 to 5.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add a subscription");
        System.out.println("2. View all subscriptions");
        System.out.println("3. Show estimated monthly spending");
        System.out.println("4. Show upcoming renewals");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addSubscription(Scanner scanner,
                                        ArrayList<Subscription> subscriptions) {
        String name;
        do {
            System.out.print("Subscription name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Please enter a name.");
            }
        } while (name.isEmpty());

        double price = readPrice(scanner);

        String billingCycle;
        while (true) {
            System.out.print("Billing cycle (monthly or yearly): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("monthly")) {
                billingCycle = "monthly";
                break;
            } else if (input.equalsIgnoreCase("yearly")) {
                billingCycle = "yearly";
                break;
            }
            System.out.println("Please enter monthly or yearly.");
        }

        LocalDate renewalDate = readRenewalDate(scanner);
        subscriptions.add(new Subscription(name, price, billingCycle, renewalDate));
        System.out.println("Added " + name + ".");
    }

    private static double readPrice(Scanner scanner) {
        while (true) {
            System.out.print("Price per billing cycle (for example, 11.99): ");
            String input = scanner.nextLine().trim();

            try {
                double price = Double.parseDouble(input);
                if (Double.isFinite(price) && price >= 0) {
                    return price;
                }
            } catch (NumberFormatException e) {
                // The message below also covers prices that are out of range.
            }

            System.out.println("Please enter a valid price of 0 or more.");
        }
    }

    private static LocalDate readRenewalDate(Scanner scanner) {
        while (true) {
            System.out.print("Next renewal date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Enter a valid date, such as 2027-02-15.");
            }
        }
    }

    private static void printSubscriptions(ArrayList<Subscription> subscriptions) {
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions added yet.");
            return;
        }

        for (Subscription subscription : subscriptions) {
            System.out.println(subscription);
        }
    }

    private static void printMonthlySpending(ArrayList<Subscription> subscriptions) {
        double total = 0;

        for (Subscription subscription : subscriptions) {
            total += subscription.getMonthlyCost();
        }

        System.out.printf("Estimated monthly spending: $%.2f%n", total);
    }

    private static void printUpcomingRenewals(ArrayList<Subscription> subscriptions) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(7);
        ArrayList<Subscription> upcoming = new ArrayList<>();

        for (Subscription subscription : subscriptions) {
            LocalDate renewalDate = subscription.getNextRenewalDate();

            // Include renewals today and exactly seven days from today.
            if (!renewalDate.isBefore(today) && !renewalDate.isAfter(endDate)) {
                upcoming.add(subscription);
            }
        }

        upcoming.sort(Comparator.comparing(Subscription::getNextRenewalDate));

        if (upcoming.isEmpty()) {
            System.out.println("No renewals from today through the next seven days.");
            return;
        }

        System.out.println("Upcoming renewals:");
        printSubscriptions(upcoming);
    }
}