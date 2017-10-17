package pl.cinema.service;

import pl.cinema.model.Reservation;
import pl.cinema.model.Snack;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ReservationService {
    void saveReservation(Reservation reservation,  Set<Snack> snacks);
    Reservation findReservation(String moviename, String day, String time, int room, int place);
    List<Integer> findPlaces(String moviename, int day, int month, int year, Time time, int room);
}
