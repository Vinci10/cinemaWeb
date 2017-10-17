package pl.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.cinema.model.Reservation;
import pl.cinema.model.ReservationPrice;
import pl.cinema.model.User;
import pl.cinema.repository.MovieRepository;
import pl.cinema.repository.ReservationRepository;
import pl.cinema.repository.UserRepository;
import pl.cinema.repository.UserTicketRepository;
import pl.cinema.service.UserService;

import java.util.ArrayList;
import java.util.List;
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserTicketRepository userTicketRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.setViewName("user");
        modelAndView.addObject("info", user.getName()+" "+user.getLastName());
        modelAndView.addObject("user", user );
        return modelAndView;
    }

    @RequestMapping(value="/user/history/{id}",method = RequestMethod.GET)
    public String showHistory(@PathVariable("id") int id, Model model){
        List <ReservationPrice> rpList = new ArrayList<>();
        List <Reservation> list = userService.userReservations(id);
        for(Reservation r: list){
            ReservationPrice rp = new ReservationPrice();
            rp.setReservation(r);
            rp.setPrice(userTicketRepository.findByTicketid(r.getId()).getPrice());
            rpList.add(rp);
        }
        model.addAttribute("reservations",rpList);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("info", user.getName()+" "+user.getLastName());
        return "/user/history";
    }
    @RequestMapping(value="/user/history/check/{id}",method = RequestMethod.GET)
    public String checkTicket(@PathVariable("id") int id, Model model){
        Reservation reservation = reservationRepository.findReservation(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("info", user.getName()+" "+user.getLastName());
        model.addAttribute("id",  reservation.getId());
        model.addAttribute("uid",  user.getId());
        model.addAttribute("price",  userTicketRepository.findByTicketid(id).getPrice());
        model.addAttribute("movieprice",  movieRepository.findByName(reservation.getMoviename()).getPrice());
        model.addAttribute("name",  reservation.getMoviename());
        model.addAttribute("day",  reservation.getDay());
        model.addAttribute("time",  reservation.getTime().toString().substring(0,reservation.getTime().toString().length()-3));
        model.addAttribute("place",  reservation.getPlace());
        model.addAttribute("room",  reservation.getRoom());
        model.addAttribute("snacks",  reservation.getSnacks());
        return "/user/check";
    }
    @RequestMapping(value="/user/changePassword/{id}",method = RequestMethod.GET)
    public String changGet(@PathVariable("id") int id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("info", user.getName()+" "+user.getLastName());
        return "/user/change";
    }
    @RequestMapping(value="/user/changePassword/{id}",method = RequestMethod.POST)
    public String changPost(@PathVariable("id") int id,
                            @RequestParam(value = "password2",required = false) String p2,
                            @RequestParam(value = "password3",required = false) String p3, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("info", user.getName()+" "+user.getLastName());
        if(p2.equals("") || p3.equals("") ){
            model.addAttribute("message","Proszę wypełnić wszystkie pola");
        }
        else{
            if(!p2.equals(p3)) model.addAttribute("message","Podane hasła różnią się");
            else{
                user.setPassword(bCryptPasswordEncoder.encode(p2));
                userRepository.save(user);
                model.addAttribute("message","Pomyślnie zmieniono hasło");
            }
        }
        return "/user/change";
    }
}
