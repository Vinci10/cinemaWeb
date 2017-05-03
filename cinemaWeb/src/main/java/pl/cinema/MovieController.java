package pl.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {
    @Autowired MovieRepository movieRepo;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String allMovies(@RequestParam(value = "name", required = true) String name,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "type", required = false) String type,Model model){
        if(sort==null && type==null) model.addAttribute("movies",movieRepo.findByNameContainingOrderByNameAsc(name));
        else if(sort!=null && type==null){
            if(sort.equals("asc")) model.addAttribute("movies",movieRepo.findByNameOrderByAverageRatingAsc(name));
            else model.addAttribute("movies",movieRepo.findByNameOrderByAverageRatingDesc(name));
        }
        else if(sort==null && type!=null) model.addAttribute("movies",movieRepo.findByNameContainingAndTypeContainingOrderByNameAsc(name,type));
        else{
            if(sort.equals("asc")) model.addAttribute("movies",movieRepo.findByNameContainingAndTypeContainingOrderByAverageRatingAsc(name,type));
            else model.addAttribute("movies",movieRepo.findByNameContainingAndTypeContainingOrderByAverageRatingDesc(name,type));
        }
        return "movies";
    }

    /*@RequestMapping(value = "/movies", method = RequestMethod.POST)
    public String findMovie(@ModelAttribute("name") @Valid String name, Model model){
      /*  if(name.equals("Nazwa_rosnąco")){
            model.addAttribute("movies",movieRepo.OrderByNameAsc());
        }
        else{*/
            //model.addAttribute("movies",movieRepo.findByNameContainingOrderByNameAsc(name));
     //   }
       /* List <Movie> m = (List<Movie>) movieRepo.findAll();
        ArrayList<String> d = new ArrayList<>();
        for(Movie movie: m){
            d.add(movie.getName());
        }*/
        //return "movies";
    //}*/
}
