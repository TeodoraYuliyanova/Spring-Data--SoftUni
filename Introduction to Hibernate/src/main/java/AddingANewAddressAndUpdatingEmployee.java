import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class AddingANewAddressAndUpdatingEmployee {

    private static final String ADDRESS_TEXT = "Vitoshka 15";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        final Address address = new Address();
        address.setText(ADDRESS_TEXT);
        entityManager.persist(address);

        String employeeLastName = scanner.nextLine();

        int countUpdates = entityManager.createQuery(Queries.UPDATE_EMPLOYEE_ADDRESS)
                .setParameter("newAddress", address).setParameter("ln", employeeLastName).executeUpdate();

        if (countUpdates > 0) {
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();

    }
}
