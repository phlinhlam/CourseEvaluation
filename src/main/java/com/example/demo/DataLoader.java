package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public void run(String... strings) throws Exception {
        //repository for both user and admin, but this application is for user only
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        //create an account with default to user
        User user1 = new User("phlinhlam@gmail.com", "phlinhlam", passwordEncoder.encode("password"), "Phuong Linh", "Lam", "301-123-1234", true);
        user1.setRoles(Arrays.asList(userRole));
        userRepository.save(user1);

        //Review constructor:
        // String q1, String q2, String q3, String q4, String q5, String q6,
        // String qs1, String qs2, String qs3, String qs4, String qs5, String section
        Review rev1 =  new Review("Excellent","Above Average","Excellent","Fair","Excellent","Average",
                "The intensive of it","I don't like to have challenge every week","Improvements to have more time with JS",
                "I would like to see a SQL Development class","Reason for taking is I like to do full stack Java","Section: OCT19");
        rev1.setUsername(user1.getUsername());
        reviewRepository.save(rev1);
        //create review class that's linked with user (one to one relationship) (maybe?)

    }
}
