import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;

public class FindLatest10Projects {

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

       entityManager.createQuery(Queries.GET_LATEST_10_PROJECTS, Project.class).setMaxResults(10)
                .getResultList()
               .stream().sorted(Comparator.comparing(Project::getName)).forEach(System.out::println);

        entityManager.close();
    }
}
