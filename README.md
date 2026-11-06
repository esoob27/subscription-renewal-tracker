# Subscription Renewal Tracker

A Java command-line app that tracks subscription renewal dates and estimates monthly spending.

## Features

- Add subscriptions with a name, price, billing cycle, and next renewal date.
- View all subscriptions in one place.
- Estimate monthly spending by dividing yearly prices by 12 and adding them to monthly prices.
- Show renewals from today through seven days from today, sorted by date.

## How to Run

**In IntelliJ IDEA:** Put `Main.java` and `Subscription.java` in the same `src` folder. Open `Main.java` and click the green Run button next to `main`.

**In a terminal:** From the folder containing both Java files, run:

```bash
javac Main.java Subscription.java
java Main
```

Use the numbered menu to add or view subscriptions. Enter prices without a dollar sign and renewal dates in `YYYY-MM-DD` format, such as `2027-01-06`.

## Example

| Subscription | Price | Billing cycle |
|---|---:|---|
| Music Plan | $12.00 | Monthly |
| Cloud Storage | $60.00 | Yearly |

Choosing **Show estimated monthly spending** displays:

```text
Estimated monthly spending: $17.00
```

The yearly plan contributes $5.00 per month to the estimate.

## Current Limitations

Subscriptions are stored in memory and reset when the program closes. Renewal dates are entered manually.
