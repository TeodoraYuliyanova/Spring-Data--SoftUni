package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visitation")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(nullable = false)
    private Date date;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Patient patient;

    public Visitation() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    protected void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    protected void setComments(String comments) {
        this.comments = comments;
    }

    public Patient getPatient() {
        return patient;
    }

    protected void setPatient(Patient patient) {
        this.patient = patient;
    }
}
