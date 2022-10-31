package printer;

public class PrinterFactory {
    public static Printer printerFor(FormatOutput formatOutput) {
        switch (formatOutput) {
            case HTML -> {
                return new HtmlPrinter();
            }
            default -> {
                return new TextPrinter();
            }
        }
    }
}
