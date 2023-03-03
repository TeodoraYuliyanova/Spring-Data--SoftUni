import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class IncreaseSalaries {

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        int updatedSalaries = entityManager.createQuery(Queries.UPDATE_SALARIES).executeUpdate();

        if (updatedSalaries == 0) {
            entityManager.getTransaction().rollback();
        } else {
            entityManager.getTransaction().commit();

            List<Employee> employeesWithUpdatedSalary = entityManager.createQuery(Queries.EMPLOYEES_WITH_UPDATED_SALARY, Employee.class)
                    .getResultList();

            for (Employee employee : employeesWithUpdatedSalary) {
                System.out.printf("%s %s ($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
            }
        }


        entityManager.close();

    }
}
