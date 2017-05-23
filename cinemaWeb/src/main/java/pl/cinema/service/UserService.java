package pl.cinema.service;

import pl.cinema.model.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    void updatePremium(int y);
}