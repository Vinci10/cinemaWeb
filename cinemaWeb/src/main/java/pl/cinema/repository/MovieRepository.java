package pl.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.cinema.model.Movie;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    List<Movie> findByNameContainingOrderByNameAsc(String name);

    Movie findByName(String name);

    @Query("select m from Movie m where name like %:movie% order by rating/numberofvotes desc")
    List<Movie> findByNameOrderByAverageRatingDesc(@Param("movie") String movie);

    @Query("select m from Movie m where name like %:movie% order by rating/numberofvotes asc")
    List<Movie> findByNameOrderByAverageRatingAsc(@Param("movie") String movie);

    List<Movie> findByNameContainingAndTypeContainingOrderByNameAsc(String name, String type);

    @Query("select m from Movie m where (m.name like %:name%) and (m.type like %:type%) order by rating/numberofvotes asc")
    List<Movie> findByNameContainingAndTypeContainingOrderByAverageRatingAsc(@Param("name") String name, @Param("type") String type);

    @Query("select m from Movie m where (m.name like %:name%) and (m.type like %:type%) order by rating/numberofvotes desc")
    List<Movie> findByNameContainingAndTypeContainingOrderByAverageRatingDesc(@Param("name") String name, @Param("type") String type);
}

