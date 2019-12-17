package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "register";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "Admin Account Created");
        }
        return "login";
    }


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsernameIgnoreCase(username));
        //insert here a checking for username
        model.addAttribute("reviews", reviewRepository.findByUsername(username));
        return "secure";
    }

    @GetMapping("/add")
    public String reviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("user", userRepository.findAll());
        return "addReview";
    }


    @PostMapping("/process")
    public String processReview(@Valid @ModelAttribute("review") Review review, User user) {
        //est. review in db
        Review reviewDB;
        long revid = review.getReview_id();
        if (reviewRepository.existsById(revid)) {
            reviewDB = reviewRepository.findById(revid).get();
            reviewDB.setQ1(review.getQ1());
            reviewDB.setQ2(review.getQ2());
            reviewDB.setQ3(review.getQ3());
            reviewDB.setQ3(review.getQ4());
            reviewDB.setQ3(review.getQ5());
            reviewDB.setQ3(review.getQ6());

            reviewDB.setQs1(review.getQs1());
            reviewDB.setQs2(review.getQs2());
            reviewDB.setQs3(review.getQs3());
            reviewRepository.save(reviewDB);
        } else {
            //create a new review when user enter the information
            reviewDB = reviewRepository.save(review);
            User currentUser = userService.getAuthenticatedUser();
            currentUser.addReview(reviewDB);
            userRepository.save(currentUser);

            //set the review to the user that is currently logged in
            reviewDB.setUser(currentUser);
            reviewDB.setUsername(currentUser.getUsername());
            reviewRepository.save(reviewDB);
        }
        return "showReview";
    }


    @RequestMapping("/show/{id}")
    public String showRev(@PathVariable("id") long id, Model model) {
        if(userService.getAuthenticatedUser() !=null) {
            model.addAttribute("user", userService.getAuthenticatedUser());
        }
        model.addAttribute("reviews", reviewRepository.findById(id).get());
        return "showReview";
    }

}