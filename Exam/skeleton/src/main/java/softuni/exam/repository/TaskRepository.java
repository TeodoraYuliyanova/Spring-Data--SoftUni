package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;

import java.util.List;
import java.util.Optional;

// TODO:
@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Optional<List<Task>> findAllByCar_CarTypeOrderByPriceDesc(CarType carType);

}
