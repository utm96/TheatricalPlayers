package result;

public class Line {
    private String name;
    private Integer amount;
    private Integer audience;

    public Line(String name, Integer amount, Integer audience) {
        this.name = name;
        this.amount = amount;
        this.audience = audience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAudience() {
        return audience;
    }

    public void setAudience(Integer audience) {
        this.audience = audience;
    }
}
