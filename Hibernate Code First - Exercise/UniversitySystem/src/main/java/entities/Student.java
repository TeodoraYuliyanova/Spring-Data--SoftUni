package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "avg_grade",nullable = false)
    private Double avgGrade;


    @Column(nullable = false)
    private String attendance;

    @ManyToMany
    @JoinTable(name = "students_courses",joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id"))
    private Set<Course> courses;


    public Student() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    protected void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getAttendance() {
        return attendance;
    }

    protected void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    protected void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
