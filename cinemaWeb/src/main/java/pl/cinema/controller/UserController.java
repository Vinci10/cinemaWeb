package pl.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.cinema.model.Reservation;
import pl.cinema.model.ReservationPrice;
import pl.cinema.model.User;
import pl.cinema.repository.UserRepository;
import pl.cinema.repository.UserTicketRepository;
import pl.cinema.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserTicketRepository userTicketRepository;

    @RequestMapping(value={"/user"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.setViewName("user");
        modelAndView.addObject("user", user );
        return modelAndView;
    }

    @RequestMapping(value="/user/history/{id}",method = RequestMethod.GET)
    public String showHistory(@PathVariable("id") int id, @RequestParam(value="name", required = false) String name,
                              @RequestParam(value="sort", required = false) String sort, Model model){
        List <ReservationPrice> rpList = new ArrayList<>();
        List <Reservation> list = userService.userReservations(id);
        for(Reservation r: list){
            ReservationPrice rp = new ReservationPrice();
            rp.setReservation(r);
            rp.setPrice(userTicketRepository.findByTicketid(r.getId()).getPrice());
            rpList.add(rp);
        }
        model.addAttribute("reservations",rpList);
        return "/user/history";
    }
}
