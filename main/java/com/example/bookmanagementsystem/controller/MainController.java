package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.AppScopeBean;
import com.example.bookmanagementsystem.entity.User;
import com.example.bookmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppScopeBean applicationScopeBean;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            ModelMap model,
                            HttpSession session) {
        User currentUser = userService.findByUsernameAndPassword(username, password);
        if (currentUser == null) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        } else {
            String page;
            System.out.println(currentUser.getRole());
            System.out.println();
            page = switch(currentUser.getRole()) {
                case "ADMIN" -> "admin";
                case "LIBRARIAN" -> "librarian";
                default -> "user";
            };

            applicationScopeBean.setNumberofUsers(applicationScopeBean.getNumberofUsers() + 1);
            session.setAttribute("currentUser", currentUser);
            return "redirect:/" + page;
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "email") String email,
                               ModelMap model) {
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        } else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userService.saveUser(user);
            return "redirect:/login";
        }
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        applicationScopeBean.setNumberofUsers(applicationScopeBean.getNumberofUsers() - 1);
        return "redirect:/login";
    }
}


