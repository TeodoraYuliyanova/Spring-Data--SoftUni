import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeesWithSalaryOver50000 {

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        List<String> employees = entityManager.createQuery(Queries.ALL_EMPLOYEES_WITH_SALARY_OVER_50000, String.class).getResultList();

        employees.forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
