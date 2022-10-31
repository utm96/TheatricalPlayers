package calculator;

public class ComedyCalculator implements Calculator {
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

    @Override
    public Integer creditFromPerformance(Integer audience) {
        int credit = 0;
        credit += Math.max(audience - 30, 0);
        credit += Math.floor(audience / 5);
        return credit;
    }
}
