package FoodSaver.foodsaver.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import FoodSaver.foodsaver.JPA.userLoginJPA;
import FoodSaver.foodsaver.JPA.SQL.userLogin;


@Controller
public class Maincontroller {

    @Autowired
    private userLoginJPA userLoginjpa;

    @GetMapping("/")
    public String mainpage() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";  // Return the login page view
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,@RequestParam("role") String role, Model model) {
        System.out.println("Role" + role);
        if(role.toLowerCase().equals("user")) {
            if (userLoginjpa.existsByEmail(email)) {
            userLogin user = userLoginjpa.findByEmail(email);
            System.out.println(user.getEmail());
            System.out.println(user.getPassword());
            if (user.getPassword().equals(password) && user.getRole().equals("USER")) {
                return "redirect:/userdashboard";
            } else {
                return "login";
            }
            } else {
                return "redirect:/login";
            }
        } else if(role.toLowerCase().equals("admin")) {
            if (userLoginjpa.existsByEmail(email)) {
                userLogin user = userLoginjpa.findByEmail(email);
                System.out.println(user.getEmail());
                System.out.println(user.getPassword());
            if (user.getPassword().equals(password) && user.getRole().equals("ADMIN")) {
                    return "redirect:/dashboard";
                } else {
                    return "login";
                }
            } else {
                return "redirect:/login";
            }
        } else {
            System.out.println("Invalid role: ");
            return "login"; // Invalid role, redirect to login
        }
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    @GetMapping("/userdashboard")
    public String userdashboard() {
        return "userdashboard";
    }



}
