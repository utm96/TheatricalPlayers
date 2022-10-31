import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {
    public final String TRAGEDY = "tragedy";
    public final String COMEDY = "comedy";

    public String print(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = Optional.ofNullable(String.format("Statement for %s\n", invoice.customer)).map(StringBuilder::new).orElse(null);
        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            //calculate amount
            int thisAmount;
            switch (play.type) {
                case TRAGEDY -> {
                    thisAmount = calculateAmountTradegy(perf);
                }
                case COMEDY -> {
                    thisAmount = calculateAmountComedy(perf);
                }
                default -> {
                    throw new Error("unknown type: ${play.type}");
                }
            }

            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if (COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

            // print line for this order
            result = (result == null ? new StringBuilder("null") : result).append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount / 100), perf.audience));
            totalAmount += thisAmount;
        }
        result = (result == null ? new StringBuilder("null") : result).append(String.format("Amount owed is %s\n", frmt.format(totalAmount / 100)));
        result.append(String.format("You earned %s credits\n", volumeCredits));
        return result.toString();
    }


    public int calculateAmountTradegy(Performance perf) {
        int amount = 40000;
        if (perf.audience > 30) {
            amount += 1000 * (perf.audience - 30);
        }
        return amount;
    }
    public int calculateAmountComedy(Performance perf) {
        int amount = 30000;
        if (perf.audience > 20) {
            amount += 10000 + 500 * (perf.audience - 20);
        }
        amount += 300 * perf.audience;
        return amount;
    }
}
