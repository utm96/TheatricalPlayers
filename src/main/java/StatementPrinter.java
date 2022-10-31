import calculator.CalculatorFactory;
import calculator.Calculator;
import result.Line;
import result.StatementResult;

import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {
    public String print(Invoice invoice, Map<String, Play> plays) {
        StatementResult statementResult = new StatementResult();
        int totalAmount = 0;
        int volumeCredit = 0;
        statementResult.setCustomer(invoice.customer);
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            Calculator calculator = CalculatorFactory.amountCalculatorFor(play.type);
            volumeCredit += calculator.creditFromPerformance(perf.audience);
            int thisAmount = calculator.calculateAmount(perf.audience);
            statementResult.getLines().add(new Line(play.name, thisAmount, perf.audience));
            totalAmount += thisAmount;
        }
        statementResult.setTotalAmount(totalAmount);
        statementResult.setVolumeCredit(volumeCredit);
        return printText(statementResult);
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
