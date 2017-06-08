package pl.cinema.controller;

import javax.validation.Valid;

import pl.cinema.model.User;
import pl.cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public ModelAndView mainPage(){
        ModelAndView modelandview = new ModelAndView();
        modelandview.setViewName("index");
        modelandview.addObject("hello", "Witaj w kinie!");
        return modelandview;
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("log in", "Prosimy się zalogować");
        return modelAndView;
    }

    @RequestMapping(value={"/registration"}, method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelandview = new ModelAndView();
        User user = new User();
        modelandview.addObject("user", user);
        modelandview.setViewName("registration");
        return modelandview;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Wybrany adres e-mail jest zajęty");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Rejestracja przebiegła pomyślnie");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
}