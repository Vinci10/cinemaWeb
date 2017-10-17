package pl.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cinema.model.Repertoire;
import pl.cinema.model.RepertoireId;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository("RepertoireRepository")
public interface RepertoireRepository extends CrudRepository<Repertoire,RepertoireId> {
    @Query("Select count(r) from Repertoire r where movie_name=:name and (day > current_date or (day = current_date and time > current_time))")
    int countByMoviename (@Param("name") String name);

    @Query("Select r from Repertoire r where movie_name=:name and (day > current_date or (day = current_date and time > current_time)) ")
    List<Repertoire> findByMoviename(@Param("name") String name);

    @Query("Select time from Repertoire r where movie_name=:name and year(day)=:year and month(day)=:month and day(day)=:day ")
    List<Time> findTimesByDay(@Param("name") String name,@Param("day") int day, @Param("month") int month, @Param("year") int year);

    @Query("Select r.id.room from Repertoire r where movie_name=:name and year(day)=:year and month(day)=:month and day(day)=:day and time=:time")
    int findRoomId(@Param("name") String name,@Param("day") int day, @Param("month") int month, @Param("year") int year,@Param("time") Time time);

    @Query("Select r from Repertoire r where room=:room and year(day)=:year and month(day)=:month and day(day)=:day and time=:time")
    Repertoire findByDayAndTimeAndRoom(@Param("room") int room,@Param("day") int day, @Param("month") int month, @Param("year") int year,@Param("time") Time time);
}
