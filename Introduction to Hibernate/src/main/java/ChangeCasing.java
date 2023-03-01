import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class ChangeCasing {

    public static void main(String[] args) {

       final EntityManager entityManager = Utils.createEntityManager();
       entityManager.getTransaction().begin();

       List<Town> towns = entityManager.createQuery(Queries.GET_ALL_TOWNS,Town.class).getResultList();

        for (Town town : towns) {
            if (town.getName().length() <= 5){
                town.setName(town.getName().toUpperCase());

                entityManager.persist(town);
            }else{
                entityManager.detach(town);
            }
        }


       entityManager.getTransaction().commit();
       entityManager.close();
    }
}
