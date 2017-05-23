package pl.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cinema.model.Reservation;
import pl.cinema.repository.ReservationRepository;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation findReservation(String moviename, String day, String time, int room, int place){
        return reservationRepository.findByMovienameAndDayAndTimeAndRoomAndPlace(moviename, day, time, room, place);
    }

    @Override
    public void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }
}
