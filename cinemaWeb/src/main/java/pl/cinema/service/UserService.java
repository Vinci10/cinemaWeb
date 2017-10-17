package pl.cinema.service;

import pl.cinema.model.Reservation;
import pl.cinema.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    void updatePremium(int y);
    List<Reservation> userReservations(int id);
}