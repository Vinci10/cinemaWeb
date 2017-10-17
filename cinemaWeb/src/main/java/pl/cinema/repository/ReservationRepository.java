package pl.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cinema.model.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository("ReservationRepository")
public interface ReservationRepository extends CrudRepository<Reservation,Integer>{
    Reservation findById(long id);
    Reservation findByMovienameAndDayAndTimeAndRoomAndPlace(String moviename, String day, String time, int room, int place);

    @Query("select place from Reservation r where movie_name = :name and room=:room and year(day)=:year and month(day)=:month and day(day)=:day and time=:time")
    List<Integer> getPlacesByMovienameAndDayAndTimeAndRoom(@Param("name") String name, @Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("time") Time time, @Param("room") int room);

    @Query("select r from Reservation r where ticket_id=:id")
    Reservation findReservation (@Param("id")int id);
}
