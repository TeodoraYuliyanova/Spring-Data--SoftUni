package exam.repository;

import exam.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop,Integer> {

    Optional<Laptop> findFirstByMacAddress(String macAddress);

    Optional<List<Laptop>> getAllByOrderByCpuSpeedDescRamDescStorageDescMacAddress();
}
