package calculator;

public class TragedyCalculator implements Calculator {
    public final Integer BASE_AMOUNT = 40000;

    @Override
    public Integer calculateAmount(Integer audience) {
        int amount = BASE_AMOUNT;
        if (audience > 30) {
            amount += 1000 * (audience - 30);
        }
        return amount;
    }
    @Override
    public Integer creditFromPerformance(Integer audience) {
        int credit = 0;
        credit += Math.max(audience - 30, 0);
        return credit;
    }
}
