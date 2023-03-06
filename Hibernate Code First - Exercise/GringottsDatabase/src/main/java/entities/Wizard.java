package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wizard")
public class Wizard {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fist_name", length = 50)
    private String firstName;

    @Column(name = "last_name",length = 60,nullable = false)
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private Long age;

    @OneToOne
    @JoinColumn
    private MagicWand magicWand;

    @OneToMany
    @JoinTable(name = "wizard_deposit")
    private List<Deposit> deposits;


    public Wizard() {
    }


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNotes() {
        return notes;
    }

    protected void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getAge() {
        return age;
    }

    protected void setAge(Long age) {
        this.age = age;
    }

    public MagicWand getMagicWand() {
        return magicWand;
    }

    protected void setMagicWand(MagicWand magicWand) {
        this.magicWand = magicWand;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    protected void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
}
