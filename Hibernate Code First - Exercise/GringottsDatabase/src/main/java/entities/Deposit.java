package entities;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String group;

    @Column(name = "start_date")
    private Date startDate;

    @Column
    private Double amount;

    @Column
    private Double interest;


    @Column
    private Double charge;


    @Column(name = "expiration_date")
    private Date expirationDate;


    @Column(name = "is_deposit_expired")
    private Boolean isExpired;


    public Deposit() {
    }


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    protected void setGroup(String group) {
        this.group = group;
    }

    public Date getStartDate() {
        return startDate;
    }

    protected void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getAmount() {
        return amount;
    }

    protected void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterest() {
        return interest;
    }

    protected void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getCharge() {
        return charge;
    }

    protected void setCharge(Double charge) {
        this.charge = charge;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    protected void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    protected void setExpired(Boolean expired) {
        isExpired = expired;
    }
}
