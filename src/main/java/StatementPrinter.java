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
        int volumeCredit = 0;
        statementResult.setCustomer(invoice.customer);
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            AmountCalculator amountCalculator = AmountCaculatorFactory.amountCalculatorFor(play.type);
            int thisAmount = amountCalculator.calculateAmount(perf.audience);
            statementResult.getLines().add(new Line(play.name, thisAmount, perf.audience));
            totalAmount += thisAmount;
            volumeCredit += creditFromPerformance(perf, play);
        }
        statementResult.setTotalAmount(totalAmount);
        statementResult.setVolumeCredit(volumeCredit);
        return printText(statementResult);
    }

    public int creditFromPerformance(Performance performance, Play play) {
        int credit = 0;
        credit += Math.max(performance.audience - 30, 0);
        if (COMEDY.equals(play.type)) credit += Math.floor(performance.audience / 5);
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
