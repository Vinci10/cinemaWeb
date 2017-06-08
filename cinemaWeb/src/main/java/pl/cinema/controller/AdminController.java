package pl.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cinema.model.Movie;
import pl.cinema.model.Repertoire;
import pl.cinema.model.RepertoireId;
import pl.cinema.model.Snack;
import pl.cinema.repository.MovieRepository;
import pl.cinema.repository.RepertoireRepository;
import pl.cinema.repository.SnackRepository;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private SnackRepository snackRepository;
    @Autowired
    private RepertoireRepository repertoireRepository;

    @RequestMapping(value="/admin")
    public String admin(Model model){
        model.addAttribute("snacks",snackRepository.findBy());
        return "admin";
    }

    @RequestMapping(value="/admin/addMovie", method = RequestMethod.GET)
    public String addGet (Model model){
        model.addAttribute("movie",new Movie());
        model.addAttribute("message", "");
        return "admin/addMovie";
    }

    @RequestMapping(value = "/admin/addMovie", method = RequestMethod.POST)
    public String addPost (@Valid Movie movie, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            if(movieRepo.findByName(movie.getName()) != null){
                model.addAttribute("message", "Film o takiej nazwie już istnieje");
            }
            else{
                movie.setNumberofvotes(0);
                movie.setRating(0);
                movieRepo.save(movie);
                model.addAttribute("message", "Pomyślnie dodano film");
            }
        }
        else model.addAttribute("message", "");
        return "/admin/addMovie";
    }
    @RequestMapping(value="/admin/editMovie/{name}", method = RequestMethod.GET)
    public String editGet (@PathVariable("name") String name,Model model){
        model.addAttribute("movie",movieRepo.findByName(name));
        model.addAttribute("message", "");
        return "admin/editMovie";
    }

    @RequestMapping(value = "/admin/editMovie/{name}", method = RequestMethod.POST)
    public String editPost (@PathVariable("name") String name, @Valid Movie movie, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            Movie m = movieRepo.findByName(name);
            m.setPrice(movie.getPrice());
            m.setName(movie.getName());
            m.setPremiere(movie.getPremiere());
            m.setDuration(movie.getDuration());
            m.setSubtitles(movie.getSubtitles());
            m.setTd(movie.getTd());
            m.setDirector(movie.getDirector());
            m.setType(movie.getType());
            m.setProduction(movie.getProduction());
            m.setDescription(movie.getDescription());
            movieRepo.save(m);
            model.addAttribute("message", "Edycja zakończona pomyślnie");
        }
        else model.addAttribute("message", "");
        return "/admin/editMovie";
    }

    @RequestMapping(value = "/admin/deleteMovie/{name}", method = RequestMethod.GET)
    public String Delete(@PathVariable("name") String name, Model model) {
        try {
            Movie findmovie = movieRepo.findByName(name);
            movieRepo.delete(findmovie);
        }
        catch (Exception ex) {
            return "Error deleting the user:" + ex.toString();
        }

        model.addAttribute("hello", "User succesfully deleted!");
        return "admin/deleteMovie";
    }
    @RequestMapping(value="/admin/addSnack", method = RequestMethod.GET)
    public String addSnackGet (Model model){
        model.addAttribute("snack",new Snack());
        model.addAttribute("message", "");
        return "admin/addSnack";
    }

    @RequestMapping(value = "/admin/addSnack", method = RequestMethod.POST)
    public String addSnackPost (@Valid Snack snack, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            if(snackRepository.findByName(snack.getName()) != null){
                model.addAttribute("message", "Przekąska o takiej nazwie już istnieje");
            }
            else{
                snackRepository.save(snack);
                model.addAttribute("message", "Pomyślnie dodano przekąskę");
            }
        }
        else model.addAttribute("message", "");
        return "/admin/addSnack";
    }
    @RequestMapping(value="/admin/editSnack/{name}", method = RequestMethod.GET)
    public String editSnackGet (@PathVariable("name") String name, Model model){
        model.addAttribute("snack",snackRepository.findByName(name));
        model.addAttribute("message", "");
        return "admin/editSnack";
    }

    @RequestMapping(value = "/admin/editSnack/{name}", method = RequestMethod.POST)
    public String editSnackPost (@PathVariable("name") String name, @Valid Snack snack, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            Snack s = snackRepository.findByName(name);
            s.setName(snack.getName());
            s.setPrice(snack.getPrice());
            snackRepository.save(s);
            model.addAttribute("message", "Edycja zakończona pomyślnie");
        }
        else model.addAttribute("message", "");
        return "/admin/editSnack";
    }

    @RequestMapping(value="/admin/addTicket",method = RequestMethod.GET)
    public String addTicketGet(
            @RequestParam(value="day", required=false) String day,
            @RequestParam(value="room", required=false) String room,
            @RequestParam(value="time", required=false) String time,
            @RequestParam(value="name", required=false) String name,
            Model model) {
        List<String> days = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyy-MM-dd");
        Calendar date = Calendar.getInstance();
        for(int i = 0; i < 7;i++){
            days.add(df.format(date.getTime()));
            date.add(Calendar.DAY_OF_MONTH , 1);
        }
        model.addAttribute("days",days);
        model.addAttribute("movies",movieRepo.findByNameContainingOrderByNameAsc(""));
        if(day == null){
            model.addAttribute("message","");
        }
        else if(day.contains("dzie") || room.equals("sala") || time.equals("godzina") || name.equals("film")){
            model.addAttribute("message","Wszystkie pola muszą być wybrane");
        }
        else{
            int year = Integer.parseInt(day.substring(0,4));
            int month = Integer.parseInt(day.substring(5,7));
            int dday = Integer.parseInt(day.substring(8,10));
            Time t = Time.valueOf(time);
            if(repertoireRepository.findByDayAndTimeAndRoom(Integer.parseInt(room),dday,month,year,t) != null){
                model.addAttribute("message","Wybrana data jest zajęta");
            }
            else{
                Repertoire r = new Repertoire();
                RepertoireId rr = new RepertoireId();
                rr.setDay(Date.valueOf(day));
                rr.setRoom(Integer.parseInt(room));
                rr.setTime(t);
                r.setId(rr);
                r.setMoviename(name);
                repertoireRepository.save(r);
                model.addAttribute("message","Pomyślnie dodano bilet");
            }
        }
        return "admin/addTicket";
    }
}
