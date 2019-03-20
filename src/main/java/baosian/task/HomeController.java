package baosian.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

   @GetMapping("/authorisation")
   public String showAuthorisationForm(Model model) {
        model.addAttribute("authorisation");
        return "authorisation";
   }

    @PostMapping("/authorisation")
    public String openAuthorisationForm(){
        return "redirect:/authorisation";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("registration", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String openRegistrationForm(){
        return "redirect:/registration";
    }

    @PostMapping("/")
    public String backToHomePage(){
        return "redirect:/";
    }
}
