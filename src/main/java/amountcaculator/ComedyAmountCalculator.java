package amountcaculator;

public class ComedyAmountCalculator implements AmountCalculator{
    public final Integer BASE_AMOUNT = 30000;

    @Override
    public Integer calculateAmount(Integer audience) {
        int amount = BASE_AMOUNT;
        if (audience > 20) {
            amount += 10000 + 500 * (audience - 20);
        }
        amount += 300 * audience;
        return amount;
    }
}
