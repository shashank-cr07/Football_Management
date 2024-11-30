package com.example.demo.Controllers;

import com.example.demo.bases.Club;
import com.example.demo.bases.Login;
import com.example.demo.Repositories.Login_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Repositories.Club_Repo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class Login_Controller {

    @Autowired
    private Login_Repo Login_Repo;

    @Autowired
    private Club_Repo clubRepo;
    // Get all login records
    @GetMapping
    public List<Login> getAllLogins() {
        return Login_Repo.findAll();
    }

    // Get a single login record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Login> getLoginById(@PathVariable Integer id) {
        Optional<Login> login = Login_Repo.findById(id);
        return login.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new login record
    @PostMapping
    public Login createLogin(@RequestBody Login login) {
        return Login_Repo.save(login);
    }

    // Update an existing login record by ID
    @PutMapping("/{id}")
    public ResponseEntity<Login> updateLogin(@PathVariable Integer id, @RequestBody Login loginDetails) {
        Optional<Login> optionalLogin = Login_Repo.findById(id);

        if (optionalLogin.isPresent()) {
            Login login = optionalLogin.get();
            login.setUsername(loginDetails.getUsername());
            login.setPassword(loginDetails.getPassword());
            login.setRole(loginDetails.getRole());
            login.setClubId(loginDetails.getClubId());

            Login updatedLogin = Login_Repo.save(login);
            return ResponseEntity.ok(updatedLogin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a login record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Integer id) {
        Optional<Login> login = Login_Repo.findById(id);

        if (login.isPresent()) {
            Login_Repo.delete(login.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Authenticate user by username and password
    @PostMapping("/authenticate")
    public ResponseEntity<List<String>> authenticateUser(@RequestParam String username, @RequestParam String password) {
        Optional<Login> login = Login_Repo.findByUsernameAndPassword(username, password);

        if (login.isPresent()) {
            Login userLogin = login.get();
            String clubName = ""; // Default value if the club is not found
            String clubId ="";
            // Check if clubId is null before fetching
            if (userLogin.getClubId() != null) {
                // Fetch club name based on clubId
                Optional<Club> club = clubRepo.findById(userLogin.getClubId());
                if (club.isPresent()) {
                    clubName = club.get().getName();
                    clubId=String.valueOf(club.get().getClubId());
                }
            }

            // Return a list with clubName and role
            List<String> response = Arrays.asList(username,clubName, userLogin.getRole(),clubId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

}

