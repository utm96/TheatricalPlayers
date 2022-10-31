package amountcaculator;

public class AmountCaculatorFactory {
    public static final String TRAGEDY = "tragedy";
    public static final String COMEDY = "comedy";

    public static AmountCalculator amountCalculatorFor(String playType) {
        switch (playType) {
            case TRAGEDY -> {
                return new TragedyAmountCalculator();
            }
            case COMEDY -> {
                return new ComedyAmountCalculator();
            }
            default -> {
                throw new Error("unknown type: " + playType);
            }
        }
    }
}
