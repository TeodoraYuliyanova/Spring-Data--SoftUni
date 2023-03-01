import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class ContainsEmployee {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        String[] employeeName = scanner.nextLine().split(" ");
        String firstName = employeeName[0];
        String lastName = employeeName[1];

        Long result = entityManager.createQuery(Queries.CONTAINS_EMPLOYEE_NAME, Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();

        if (result > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
