package pl.cinema.controller;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cinema.model.*;
import pl.cinema.repository.GuestTicketRepository;
import pl.cinema.repository.PriceListRepository;
import pl.cinema.repository.RepertoireRepository;
import pl.cinema.repository.UserTicketRepository;
import pl.cinema.service.ReservationService;
import pl.cinema.service.UserService;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
//@RestController
@Controller
@SessionAttributes("reservation")
public class TicketController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserTicketRepository userTicketRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PriceListRepository priceListRepository;
    @Autowired
    private GuestTicketRepository guestTicketRepository;
    @Autowired
    private RepertoireRepository repertoireRepository;

    @RequestMapping(value="/buyticket", method = RequestMethod.GET)
    public String buyTicket(@RequestParam(value = "name", required = true) String movie, Model model){
        Reservation reservation = new Reservation();
        model.addAttribute("reservation", reservation);
        List<Repertoire> repertoires = repertoireRepository.findByIdMoviename(movie);
        List <Date> days = new ArrayList<>();
        //List <Time> times = new ArrayList<>();
        for(Repertoire r : repertoires){
            if(!days.contains(r.getId().getDay())) days.add(r.getId().getDay());
           // if(!times.contains(r.getId().getTime())) times.add(r.getId().getTime());
        }
        model.addAttribute("repertoires", repertoires);
        model.addAttribute("days", days);
        //model.addAttribute("times", times);
        model.addAttribute("successMessage","null");
        return "buyticket";
    }

    @RequestMapping(value="/buyticket", method = RequestMethod.POST)
    public String buyNewTicket(@RequestParam(value = "name", required = true) String movie,
                               @RequestParam(value = "login", required = true) String login,
                               @Valid @ModelAttribute("reservation") Reservation reservation,
                               Model model, final RedirectAttributes redirectAttributes, BindingResult bindingResult){
        if (bindingResult.hasErrors() || movie==null || login==null) {
            return "buyticket";
        }
       else{
            reservation.setMoviename(movie);
            if(login.equals("yes")){
                PriceList priceList = priceListRepository.findByMoviename(reservation.getMoviename());
                double price = priceList.getPrice();
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user = userService.findUserByEmail(auth.getName());
                reservationService.saveReservation(reservation);
                UserTicket userTicket = new UserTicket();
                userTicket.setPrice(price);
                userTicket.setTicketid(reservation.getId());
                userTicket.setUserid(user.getId());
                userTicketRepository.save(userTicket);
                int premium = (int) price/8;
                user.setPremium(premium);
                userService.updatePremium(user.getId());
                model.addAttribute("successMessage", "Reservation has been registered successfully.Number of ticket "+ reservation.getId()+". You received "+premium+" premium points");
            }
            else{
                redirectAttributes.addFlashAttribute("reservation", reservation);
                return "redirect:/buyticketdata";
            }
        }
        return "buyticket";
    }

    @RequestMapping(value = "/buyticketdata",method = RequestMethod.GET)
    public String getGuestData(@ModelAttribute("reservation") final Reservation reservation, Model model){
        GuestTicket guestTicket = new GuestTicket();
        model.addAttribute("reservation", reservation);
        model.addAttribute("guestTicket",guestTicket);
        model.addAttribute("successMessage","null");
        return "buyticketdata";
    }

    @RequestMapping(value = "/buyticketdata",method = RequestMethod.POST)
    public String saveGuestData(@Valid GuestTicket guestTicket,@ModelAttribute("reservation") final Reservation reservation,Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            model.addAttribute("successMessage","null");
            return "buyticketdata";
        }
        else{
            reservationService.saveReservation(reservation);
            PriceList priceList = priceListRepository.findByMoviename(reservation.getMoviename());
            double price = priceList.getPrice();
            guestTicket.setTicketid(reservation.getId());
            guestTicket.setPrice(price);
            guestTicketRepository.save(guestTicket);
            model.addAttribute("successMessage", "Reservation has been registered successfully.Number of ticket "+ reservation.getId()+".");
        }
        return "buyticketdata";
    }
}
