package pl.cinema.service;

import pl.cinema.model.Reservation;

public interface ReservationService {
    void saveReservation(Reservation reservation);
    Reservation findReservation(String moviename, String day, String time, int room, int place);
}
