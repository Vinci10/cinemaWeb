package pl.cinema.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cinema.model.PriceList;

@Repository("PriceListRepository")
public interface PriceListRepository extends CrudRepository<PriceList,String>{
    PriceList findByMoviename (String name);
}
