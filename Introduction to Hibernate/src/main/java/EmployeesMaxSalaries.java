import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;

public class EmployeesMaxSalaries {

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(Queries.DEPARTMENTS_MAX_SALARY, Employee.class).getResultList()
                .forEach(e -> System.out.printf("%s %.2f%n", e.getDepartment().getName(), e.getSalary()));


        entityManager.close();
    }
}
