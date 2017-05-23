package pl.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cinema.model.Repertoire;
import pl.cinema.model.RepertoireId;

import java.util.List;

@Repository("RepertoireRepository")
public interface RepertoireRepository extends CrudRepository<Repertoire,RepertoireId> {
    @Query("Select count(r) from Repertoire r where movie_name=:name and (day > current_date or (day = current_date and time > current_time))")
    int countByIdMoviename (@Param("name") String name);

    @Query("Select r from Repertoire r where movie_name=:name and (day > current_date or (day = current_date and time > current_time))")
    List<Repertoire> findByIdMoviename(@Param("name") String name);
}
