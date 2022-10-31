import amountcaculator.AmountCaculatorFactory;
import amountcaculator.AmountCalculator;
import result.Line;
import result.StatementResult;

import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {
    public final String TRAGEDY = "tragedy";
    public final String COMEDY = "comedy";

    public String print(Invoice invoice, Map<String, Play> plays) {
        StatementResult statementResult = new StatementResult();
        int totalAmount = 0;
//        int volumeCredits = 0;
//        StringBuilder result = Optional.ofNullable(String.format("Statement for %s\n", invoice.customer)).map(StringBuilder::new).orElse(null);
        statementResult.setCustomer(invoice.customer);
//        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            //calculate amount

            AmountCalculator amountCalculator = AmountCaculatorFactory.amountCalculatorFor(play.type);
            int thisAmount = amountCalculator.calculateAmount(perf.audience);


//            // add volume credits
//            volumeCredits += Math.max(perf.audience - 30, 0);
//            // add extra credit for every ten comedy attendees
//            if (COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

            // print line for this order
//            result = (result == null ? new StringBuilder("null") : result).append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount / 100), perf.audience));
            statementResult.getLines().add(new Line(play.name, thisAmount, perf.audience));
            totalAmount += thisAmount;
        }
        statementResult.setTotalAmount(totalAmount);
        statementResult.setVolumeCredit(calculateVolumeCredit(invoice, plays));
//        result = (result == null ? new StringBuilder("null") : result).append(String.format("Amount owed is %s\n", frmt.format(totalAmount / 100)));
//        result.append(String.format("You earned %s credits\n", calculateVolumeCredit(invoice, plays)));
        return printText(statementResult);
    }

    public int calculateVolumeCredit(Invoice invoice, Map<String, Play> plays) {
        int credit = 0;
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            credit += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if (COMEDY.equals(play.type)) credit += Math.floor(perf.audience / 5);
        }
        return credit;
    }

    public String printText(StatementResult statementResult) {
        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder result = Optional.ofNullable(String.format("Statement for %s\n", statementResult.getCustomer())).map(StringBuilder::new).orElse(null);
        List<Line> lines = statementResult.getLines();
        for (Line line : lines) {
            result = (result == null ? new StringBuilder("null") : result).append(String.format("  %s: %s (%s seats)\n", line.getName(), frmt.format(line.getAmount() / 100), line.getAudience()));
        }
        result = (result == null ? new StringBuilder("null") : result).append(String.format("Amount owed is %s\n", frmt.format(statementResult.getTotalAmount() / 100)));
        result.append(String.format("You earned %s credits\n", statementResult.getVolumeCredit()));
        return result.toString();
    }
}
