package printer;

import result.Line;
import result.StatementResult;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TextPrinter implements Printer {
    @Override
    public String print(StatementResult statementResult) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder result = new StringBuilder(printCustomer(statementResult));
        List<Line> lines = statementResult.getLines();
        for (Line line : lines) {
            result.append(printLine(line, numberFormat));
        }
        result.append(printTotalAmount(statementResult, numberFormat));
        result.append(printVolumeCredit(statementResult, numberFormat));
        return result.toString();
    }

    private String printCustomer(StatementResult statementResult) {
        return String.format("Statement for %s\n", statementResult.getCustomer());
    }

    private String printLine(Line line, NumberFormat frmt) {
        return String.format("  %s: %s (%s seats)\n", line.getName(), frmt.format(line.getAmount() / 100), line.getAudience());
    }

    private String printTotalAmount(StatementResult statementResult, NumberFormat frmt) {
        return String.format("Amount owed is %s\n", frmt.format(statementResult.getTotalAmount() / 100));
    }

    private String printVolumeCredit(StatementResult statementResult, NumberFormat frmt) {
        return String.format("You earned %s credits\n", statementResult.getVolumeCredit());
    }
}
