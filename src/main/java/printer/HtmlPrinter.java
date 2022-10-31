package printer;

import result.Line;
import result.StatementResult;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HtmlPrinter implements Printer {
    @Override
    public String print(StatementResult statementResult) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder result = new StringBuilder(printCustomer(statementResult));
        List<Line> lines = statementResult.getLines();
        for (Line line : lines) {
            result.append(printLine(line, numberFormat));
        }
        result.append(printTotalAmount(statementResult, numberFormat));
        result.append(printVolumeCredit(statementResult));
        result.append(endOfFile());
        return result.toString();
    }

    private String printCustomer(StatementResult statementResult) {
        return String.format("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "table, th, td {\n" +
                "border: 1px solid black;\n" +
                "}\n" +
                "th, td {\n" +
                "padding: 5px;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Invoice</h1>\n" +
                "<p><strong>Client:</strong> %s</p>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th>Piece</th>\n" +
                "        <th>Seats Sold</th>\n" +
                "        <th>Price</th>\n" +
                "    </tr>\n", statementResult.getCustomer());
    }

    private String printLine(Line line, NumberFormat frmt) {
        return String.format("    <tr>\n" +
                "        <td>%s</td>\n" +
                "        <td>%s</td>\n" +
                "        <td>%s</td>\n" +
                "    </tr>\n", line.getName(), line.getAudience(), frmt.format(line.getAmount() / 100));
    }

    private String printTotalAmount(StatementResult statementResult, NumberFormat frmt) {
        return String.format("    <tr>\n" +
                "        <td colspan=\"2\" style=\"text-align: right;\"><strong>Total Owed:</strong></td>\n" +
                "        <td>%s</td>\n" +
                "    </tr>\n", frmt.format(statementResult.getTotalAmount() / 100));
    }

    private String printVolumeCredit(StatementResult statementResult) {
        return String.format("    <tr>\n" +
                "        <td colspan=\"2\" style=\"text-align: right;\"><strong>Fidelity Points Earned:</strong></td>\n" +
                "        <td>%s</td>\n" +
                "    </tr>\n", statementResult.getVolumeCredit());
    }
    private String endOfFile() {
        return "</table>\n" +
                "\n" +
                "<p><strong>Payment is required under 30 day. We can break your knees if you don't do so</strong></p>\n" +
                "</body>\n" +
                "</html>";
    }
}
