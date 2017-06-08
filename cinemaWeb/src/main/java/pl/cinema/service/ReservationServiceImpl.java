package pl.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cinema.model.Reservation;
import pl.cinema.model.Snack;
import pl.cinema.repository.ReservationRepository;
import pl.cinema.repository.SnackRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SnackRepository snackRepository;

    @Override
    public Reservation findReservation(String moviename, String day, String time, int room, int place){
        return reservationRepository.findByMovienameAndDayAndTimeAndRoomAndPlace(moviename, day, time, room, place);
    }

    @Override
    public void saveReservation(Reservation reservation, Set<Snack> snacks){
        if(snacks != null) reservation.setSnacks(snacks);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Integer> findPlaces(String moviename, int day, int month, int year, Time time, int room){
        return reservationRepository.getPlacesByMovienameAndDayAndTimeAndRoom(moviename,day,month,year,time,room);
    }
}
