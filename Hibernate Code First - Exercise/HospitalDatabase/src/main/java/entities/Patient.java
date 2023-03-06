package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column
    private String email;

    @Column(name = "birthDate",nullable = false)
    private Date birthDate;

    @Column
    private String picture;

    @Column(name = "medical_insurance",nullable = false)
    private Boolean hasMedicalInsurance;

    @ManyToMany
    @JoinTable(name = "patients_diagnoses",joinColumns = @JoinColumn(referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Diagnose> diagnoses;

    @ManyToMany
    @JoinTable(name = "patients_medicaments",joinColumns = @JoinColumn(referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Medicament> medicaments;

    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;

    public Patient() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
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

    public String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return birthDate;
    }

    protected void setDateOfBirth(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPicture() {
        return picture;
    }

    protected void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getHasMedicalInsurance() {
        return hasMedicalInsurance;
    }

    protected void setHasMedicalInsurance(Boolean hasMedicalInsurance) {
        this.hasMedicalInsurance = hasMedicalInsurance;
    }

    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    protected void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    protected void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }

    protected void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }
}
