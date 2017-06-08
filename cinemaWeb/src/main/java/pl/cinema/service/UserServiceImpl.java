package pl.cinema.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.cinema.model.Reservation;
import pl.cinema.model.Role;
import pl.cinema.model.User;
import pl.cinema.repository.ReservationRepository;
import pl.cinema.repository.RoleRepository;
import pl.cinema.repository.UserRepository;
import pl.cinema.repository.UserTicketRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserTicketRepository userTicketRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("user");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updatePremium(int y){
        userRepository.updatePremium(y);
    }

    @Override
    public List<Reservation> userReservations(int id){
        List <Integer> ticketsId = userTicketRepository.findTicketsId(id);
        List <Reservation> reservations = new ArrayList<>();
        for(Integer i : ticketsId){
            reservations.add(reservationRepository.findReservation(i));
        }
        return reservations;
    }
}