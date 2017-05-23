package pl.cinema.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cinema.model.Reservation;

@Repository("ReservationRepository")
public interface ReservationRepository extends CrudRepository<Reservation,Integer>{
    Reservation findById(long id);
    Reservation findByMovienameAndDayAndTimeAndRoomAndPlace(String moviename, String day, String time, int room, int place);
}
