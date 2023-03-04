package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetail{

    @Column(name = "card_type",nullable = false)
    private String cardType;

    @Column(name = "expiration_month",nullable = false)
    private Month expirationMonth;

    @Column(name = "expiration_year",nullable = false)
    private Year expirationYear;

    public CreditCard() {
    }

    public String getCardType() {
        return cardType;
    }

    protected void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Month getExpirationMonth() {
        return expirationMonth;
    }

    protected void setExpirationMonth(Month expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Year getExpirationYear() {
        return expirationYear;
    }

    protected void setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
    }
}
