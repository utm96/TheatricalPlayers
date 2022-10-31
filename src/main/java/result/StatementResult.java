package result;

import java.util.ArrayList;
import java.util.List;

public class StatementResult {
    private String customer;
    private List<Line> lines = new ArrayList<>();
    private Integer volumeCredit;
    private Integer totalAmount;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Integer getVolumeCredit() {
        return volumeCredit;
    }

    public void setVolumeCredit(Integer volumeCredit) {
        this.volumeCredit = volumeCredit;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}
