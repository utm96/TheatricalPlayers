package printer;

import result.Line;
import result.StatementResult;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TextPrinter implements Printer{
    @Override
    public String print(StatementResult statementResult) {
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