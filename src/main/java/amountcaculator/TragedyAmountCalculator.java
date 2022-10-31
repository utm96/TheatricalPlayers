package amountcaculator;

public class TragedyAmountCalculator implements AmountCalculator {
    public final Integer BASE_AMOUNT = 40000;

    @Override
    public Integer calculateAmount(Integer audience) {
        int amount = BASE_AMOUNT;
        if (audience > 30) {
            amount += 1000 * (audience - 30);
        }
        return amount;
    }
}
