package pl.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cinema.model.*;
import pl.cinema.repository.*;
import pl.cinema.service.ReservationService;
import pl.cinema.service.UserService;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private GuestTicketRepository guestTicketRepository;
    @Autowired
    private RepertoireRepository repertoireRepository;
    @Autowired
    private SnackRepository snackRepository;
    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(value = "/buyticket/{name}", method = RequestMethod.GET)
    public String getDaysByMovieName(@PathVariable("name") String name,Model model){
        List <Repertoire> repertoires = repertoireRepository.findByMoviename(name);
        List <Date> days = new ArrayList<>();
        for(Repertoire r: repertoires){
            if(!days.contains(r.getId().getDay())) days.add(r.getId().getDay());
        }
        model.addAttribute("days",days);
        model.addAttribute("successMessage","days");
        model.addAttribute("url","/buyticket/"+name);
        return "buyticket";
    }

    @RequestMapping(value = "/buyticket/{name}/{day}", method = RequestMethod.GET)
    public String getHoursByDay(@PathVariable("name") String name,
                                @PathVariable("day") String day,
                                Model model){
        List <Repertoire> repertoires = repertoireRepository.findByMoviename(name);
        List <Date> days = new ArrayList<>();
        for(Repertoire r: repertoires){
            if(!days.contains(r.getId().getDay())) days.add(r.getId().getDay());
        }
        model.addAttribute("days",days);
        int year= Integer.parseInt(day.substring(0,4));
        int month= Integer.parseInt(day.substring(5,7));
        int dday= Integer.parseInt(day.substring(8,10));
        model.addAttribute("url1","/buyticket/"+name);
        model.addAttribute("times",repertoireRepository.findTimesByDay(name,dday,month,year));
        model.addAttribute("successMessage","times");
        model.addAttribute("url","/buyticket/"+name+"/"+day);
        model.addAttribute("name",name);
        return "buyticket";
    }

    @RequestMapping(value = "/buyticket/{name}/{day}/{time}", method = RequestMethod.GET)
    public String getPlace(@PathVariable("name") String name,
                                @PathVariable("day") String day,
                                @PathVariable("time") String time,
                                Model model){
        Time t = Time.valueOf(time);
        List <Repertoire> repertoires = repertoireRepository.findByMoviename(name);
        List <Date> days = new ArrayList<>();
        for(Repertoire r: repertoires){
            if(!days.contains(r.getId().getDay())) days.add(r.getId().getDay());
        }
        model.addAttribute("days",days);
        int year= Integer.parseInt(day.substring(0,4));
        int month= Integer.parseInt(day.substring(5,7));
        int dday= Integer.parseInt(day.substring(8,10));
        model.addAttribute("url1","/buyticket/"+name);
        model.addAttribute("times",repertoireRepository.findTimesByDay(name,dday,month,year));
        model.addAttribute("url","/buyticket/"+name+"/"+day);
        model.addAttribute("name",name);
        int roomId = repertoireRepository.findRoomId(name,dday,month,year,t);
        List <Integer> busyPlaces = reservationService.findPlaces(name,dday,month,year,t,roomId);
        List <Integer> availablePlaces = new ArrayList<>();
        for(int i=1; i <=30; i++){
            if(!busyPlaces.contains(i)) availablePlaces.add(i);
        }
        model.addAttribute("places",availablePlaces);
        model.addAttribute("successMessage","places");
        model.addAttribute("url2","/buyticket/"+name+"/"+day+"/"+time);
        return "buyticket";
    }

    @RequestMapping(value="/buyticket/{name}/{day}/{time}/{place}", method = RequestMethod.GET)
    public String makeReservation(@PathVariable("name") String name,
                           @PathVariable("day") String day,
                           @PathVariable("time") String time,
                           @PathVariable("place") int place,
                           @RequestParam(value = "login", required = true) String login,
                           Model model){
        model.addAttribute("successMessage","buy");
        Time t = Time.valueOf(time);
        List <Snack> snacks = snackRepository.findBy();
        List <Repertoire> repertoires = repertoireRepository.findByMoviename(name);
        List <Date> days = new ArrayList<>();
        for(Repertoire r: repertoires){
            if(!days.contains(r.getId().getDay())) days.add(r.getId().getDay());
        }
        model.addAttribute("days",days);
        int year= Integer.parseInt(day.substring(0,4));
        int month= Integer.parseInt(day.substring(5,7));
        int dday= Integer.parseInt(day.substring(8,10));
        model.addAttribute("url1","/buyticket/"+name);
        model.addAttribute("times",repertoireRepository.findTimesByDay(name,dday,month,year));
        model.addAttribute("url","/buyticket/"+name+"/"+day);
        model.addAttribute("name",name);
        model.addAttribute("snacks",snacks);
        int roomId = repertoireRepository.findRoomId(name,dday,month,year,t);
        List <Integer> busyPlaces = reservationService.findPlaces(name,dday,month,year,t,roomId);
        List <Integer> availablePlaces = new ArrayList<>();
        for(int i=1; i <=30; i++){
            if(!busyPlaces.contains(i)) availablePlaces.add(i);
        }
        model.addAttribute("places",availablePlaces);
        model.addAttribute("url2","/buyticket/"+name+"/"+day+"/"+time);
        return "buyticket";
    }

    @RequestMapping(value="/buyticket/{name}/{day}/{time}/{place}", method = RequestMethod.POST)
    public String buyTicket(@PathVariable("name") String name,
                                  @PathVariable("day") String day,
                                  @PathVariable("time") String time,
                                  @PathVariable("place") int place,
                                  @RequestParam(value = "login", required = true) String login,
                                  @RequestParam(value = "snack", required = false) String snack,
                                  Model model, final RedirectAttributes redirectAttributes){
        if (login==null) {
            return "buyticket";
        }
        else{
            Reservation reservation = new Reservation();
            Time t = Time.valueOf(time);
            int year = Integer.parseInt(day.substring(0,4));
            int month = Integer.parseInt(day.substring(5,7));
            int dday = Integer.parseInt(day.substring(8,10));
            reservation.setRoom(repertoireRepository.findRoomId(name,dday,month,year,t));
            reservation.setMoviename(name);
            reservation.setPlace(place);
            Date d = Date.valueOf(day);
            reservation.setDay(d);
            reservation.setTime(t);
            if(login.equals("yes")){
                double price = movieRepository.findByName(reservation.getMoviename()).getPrice();
                if(snack != null){
                    StringBuilder str = new StringBuilder();
                    Set<Snack> snacks = new HashSet<>();
                    for(int i = 0; i < snack.length(); i++){
                        if(snack.charAt(i)==','){
                            Snack s = snackRepository.findByName(str.toString());
                            snacks.add(s);
                            price += s.getPrice();
                            str = new StringBuilder();
                        }
                        else{
                            str.append(snack.charAt(i));
                        }
                    }
                    Snack s = snackRepository.findByName(str.toString());
                    snacks.add(s);
                    price += s.getPrice();
                    reservationService.saveReservation(reservation,snacks);
                }
                else reservationService.saveReservation(reservation,null);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user = userService.findUserByEmail(auth.getName());
                UserTicket userTicket = new UserTicket();
                userTicket.setPrice(price);
                userTicket.setTicketid(reservation.getId());
                userTicket.setUserid(user.getId());
                userTicketRepository.save(userTicket);
                int premium = (int) price/8;
                user.setPremium(premium);
                userService.updatePremium(user.getId());
                model.addAttribute("successMessage", "success");
                model.addAttribute("id",  reservation.getId());
                model.addAttribute("premium",  premium);
                model.addAttribute("price",  price);
                model.addAttribute("movieprice",  movieRepository.findByName(name).getPrice());
                model.addAttribute("name",  name);
                model.addAttribute("day",  reservation.getDay());
                model.addAttribute("time",  reservation.getTime().toString().substring(0,reservation.getTime().toString().length()-3));
                model.addAttribute("place",  reservation.getPlace());
                model.addAttribute("room",  reservation.getRoom());
                model.addAttribute("snacks",  reservation.getSnacks());
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
        model.addAttribute("name", reservation.getMoviename());
        model.addAttribute("guestTicket",guestTicket);
        model.addAttribute("successMessage","null");
        return "buyticketdata";
    }

    @RequestMapping(value = "/buyticketdata",method = RequestMethod.POST)
    public ModelAndView saveGuestData(@Valid GuestTicket guestTicket, BindingResult bindingResult, @ModelAttribute("reservation") final Reservation reservation){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "null");
            modelAndView.setViewName("buyticketdata");
            modelAndView.addObject("name", reservation.getMoviename());
        }
        else{
            reservationService.saveReservation(reservation,null);
            double price = movieRepository.findByName(reservation.getMoviename()).getPrice();
            guestTicket.setTicketid(reservation.getId());
            guestTicket.setPrice(price);
            guestTicketRepository.save(guestTicket);
            modelAndView.addObject("id",  reservation.getId());
            modelAndView.addObject("nam",  guestTicket.getName());
            modelAndView.addObject("lastname",  guestTicket.getLastName());
            modelAndView.addObject("email",  guestTicket.getEmail());
            modelAndView.addObject("movieprice",  movieRepository.findByName(reservation.getMoviename()).getPrice());
            modelAndView.addObject("name",  reservation.getMoviename());
            modelAndView.addObject("day",  reservation.getDay());
            modelAndView.addObject("time",  reservation.getTime().toString().substring(0,reservation.getTime().toString().length()-3));
            modelAndView.addObject("place",  reservation.getPlace());
            modelAndView.addObject("room",  reservation.getRoom());
            modelAndView.addObject("successMessage", "Reservation has been registered successfully.Number of ticket "+ reservation.getId()+".");
        }
        return modelAndView;
    }
}
