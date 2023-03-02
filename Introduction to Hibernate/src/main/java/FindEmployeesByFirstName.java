import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class FindEmployeesByFirstName {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        String firstNamePattern = scanner.nextLine();

        List<Employee> employees = entityManager.createQuery(Queries.EMPLOYEES_WITH_FIRST_FOUND_BY_PATTERN, Employee.class)
                .setParameter("fn", firstNamePattern + "%").getResultList();

        for (Employee employee : employees) {
            System.out.printf("%s %s - %s - ($%.2f)%n" , employee.getFirstName() , employee.getLastName(),
                    employee.getJobTitle() , employee.getSalary());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
