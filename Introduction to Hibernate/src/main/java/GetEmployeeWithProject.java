import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class GetEmployeeWithProject {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = Utils.createEntityManager();

        int employeeId = Integer.parseInt(scanner.nextLine());

        entityManager.getTransaction().begin();

        System.out.println(entityManager.createQuery(Queries.GET_EMPLOYEE_BY_ID, Employee.class)
                .setParameter("id", employeeId).getSingleResult().toString());


        entityManager.close();
    }
}
