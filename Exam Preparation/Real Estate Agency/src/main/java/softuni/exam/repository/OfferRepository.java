package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.ApartmentType;
import softuni.exam.models.entity.Offer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    Optional<List<Offer>> findAllByApartment_ApartmentTypeIsOrderByApartmentAreaDescPriceAsc(
             ApartmentType apartmentType);
}
