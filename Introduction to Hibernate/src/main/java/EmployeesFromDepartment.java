import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeesFromDepartment {

    private static final String PRINT_RESULT_FORMAT = "%s %s from Research and Development - $%.2f%n";

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery(Queries.EMPLOYEES_FROM_DEPARTMENT, Employee.class)
                .setParameter("dn" , "Research and Development").getResultList();

        employees.forEach(e -> System.out.printf(PRINT_RESULT_FORMAT , e.getFirstName(),e.getLastName() , e.getSalary()));


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
