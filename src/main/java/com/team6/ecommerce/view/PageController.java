package com.team6.ecommerce.view;


import com.team6.ecommerce.user.dto.UserRegistrationDTO;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//mvc
@AllArgsConstructor
@Controller
public class PageController {

    private final UserRepository userRepo;
    private final UserService userService;

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        // Add UserRegistrationDTO to the model
        model.addAttribute("UserDTO", new UserRegistrationDTO());
        return "register";
    }

}
