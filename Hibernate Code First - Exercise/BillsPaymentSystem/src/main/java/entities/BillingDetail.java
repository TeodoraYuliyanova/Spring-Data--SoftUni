package entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetail {

    @Id
    @Column
    private int number;

    @ManyToOne
    @JoinTable(name = "user_billing_details", joinColumns = @JoinColumn(referencedColumnName = "number"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private User owner;


    public int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    protected void setOwner(User owner) {
        this.owner = owner;
    }
}
