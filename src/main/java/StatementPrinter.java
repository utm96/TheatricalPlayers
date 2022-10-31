import calculator.CalculatorFactory;
import calculator.Calculator;
import printer.TextPrinter;
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
        return new TextPrinter().print(statementResult);
    }


}
