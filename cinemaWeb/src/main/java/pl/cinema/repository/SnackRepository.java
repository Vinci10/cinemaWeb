package pl.cinema.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cinema.model.Snack;

import java.util.List;

@Repository("SnackRepository")
public interface SnackRepository extends CrudRepository<Snack, Integer> {
    Snack findById (int id);
    Snack findByName (String name);
    List<Snack> findBy();
}
