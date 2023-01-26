package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

    @Query("SELECT a from Apartment as a where a.area = :area and a.town.townName = :town")
    Optional<Apartment> findApartmentByAreaAndTownName(Double area, String town);

    Optional<Apartment> findFirstById(Integer id);

}
