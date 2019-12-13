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
            //use this line to save new user as user
            //userService.saveUser(user);
            //save new user as admin and allows it to have full access
            userService.saveUser(user);
            model.addAttribute("message", "Admin Account Created");
        }
        return "login";
    }


    @RequestMapping("/")
    public String index() {
//        model.addAttribute("reviews",reviewRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
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
        return "secure";
    }

    @GetMapping("/add")
    public String reviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("user", userRepository.findAll());
        return "addReview";
    }

    //PROBLEM!!!
    @PostMapping("/process")
    public String processReview(@Valid @ModelAttribute("review") Review review) {
        //est. review in db
        Review reviewDB;
        long revid = review.getReview_id();
        //option to edit review if the review id is already exist
        if (reviewRepository.existsById(revid)) {
            reviewDB = reviewRepository.findById(revid).get();
            reviewDB.setQ1(review.getQ1());
            reviewDB.setQ2(review.getQ2());
            reviewDB.setQ3(review.getQ3());
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

    @RequestMapping("/list")
    public String listRev(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "listReview";
    }


//    //show details of a specific message
//    @RequestMapping("/detail/{id}")
//    public String detailMessage(@PathVariable("id") long id, Model model)
//    {
//        model.addAttribute("messages", messageRepository.findById(id));
//        return "messageDetail";
//    }
//
//    @RequestMapping("/update/{id}")
//    public String updateMessage(@PathVariable("id") long messid, Model model)
//    {
//        model.addAttribute("messages", messageRepository.findById(messid));
//        return "addMessage";
//    }
}