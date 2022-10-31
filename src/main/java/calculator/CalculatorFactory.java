package calculator;

public class CalculatorFactory {
    public static final String TRAGEDY = "tragedy";
    public static final String COMEDY = "comedy";

    public static Calculator amountCalculatorFor(String playType) {
        switch (playType) {
            case TRAGEDY -> {
                return new TragedyCalculator();
            }
            case COMEDY -> {
                return new ComedyCalculator();
            }
            default -> {
                throw new Error("unknown type: " + playType);
            }
        }
    }
}
