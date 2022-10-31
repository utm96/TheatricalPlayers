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
        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                table, th, td {
                border: 1px solid black;
                }
                th, td {
                padding: 5px;
                }
                </style>
                </head>
                <body>
                <h1>Invoice</h1>
                <p><strong>Client:</strong> %s</p>
                <table>
                    <tr>
                        <th>Piece</th>
                        <th>Seats Sold</th>
                        <th>Price</th>
                    </tr>
                """, statementResult.getCustomer());
    }

    private String printLine(Line line, NumberFormat frmt) {
        return String.format("""
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                    </tr>
                """, line.getName(), line.getAudience(), frmt.format(line.getAmount() / 100));
    }

    private String printTotalAmount(StatementResult statementResult, NumberFormat frmt) {
        return String.format("""
                    <tr>
                        <td colspan="2" style="text-align: right;"><strong>Total Owed:</strong></td>
                        <td>%s</td>
                    </tr>
                """, frmt.format(statementResult.getTotalAmount() / 100));
    }

    private String printVolumeCredit(StatementResult statementResult) {
        return String.format("""
                    <tr>
                        <td colspan="2" style="text-align: right;"><strong>Fidelity Points Earned:</strong></td>
                        <td>%s</td>
                    </tr>
                """, statementResult.getVolumeCredit());
    }
    private String endOfFile() {
        return """
                </table>

                <p><strong>Payment is required under 30 day. We can break your knees if you don't do so</strong></p>
                </body>
                </html>""";
    }
}
