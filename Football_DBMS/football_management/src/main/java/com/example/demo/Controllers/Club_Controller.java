package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bases.Club;
import com.example.demo.Repositories.Club_Repo;

@RestController
public class Club_Controller {

    @Autowired
    private Club_Repo m1;

    @GetMapping("/Club")
    public List<Club> getAll() {
        return m1.findAll();
    }

    @GetMapping("/Club-find/{identity}")
    public Club getSingleClub(@PathVariable("identity") Integer id) {
        return m1.findById(id).orElse(null);
    }

    @DeleteMapping("/Club-remove/{id}")
    public boolean deleteRow(@PathVariable("id") Integer id) {
        if (m1.findById(id).isPresent()) {
            m1.deleteById(id);
            return true;
        }
        return false;
    }

    @PutMapping("/Club-update/{id}")
    public Club updateClub(@PathVariable("id") Integer id, 
                           @RequestBody Map<String, String> body) {

        Optional<Club> currentOpt = m1.findById(id);
        if (currentOpt.isPresent()) {
            Club current = currentOpt.get();

            current.setClub_Name(body.get("Club_Name"));  // Correct field names
            current.setMarket_value(Integer.parseInt(body.get("Market_value")));
            current.setLeague(body.get("League"));
            current.setAvailable_Balance(Integer.parseInt(body.get("Available_Balance")));
            current.setCoach(body.get("Coach"));

            m1.save(current);
            return current;
        } else {
            throw new RuntimeException("Club not found with id " + id);
        }
    }

    @PostMapping("/Club-add")
    public Club create(@RequestBody Map<String, String> body) {
        try {
            String clubName = body.get("Club_Name");
            Integer marketValue = Integer.parseInt(body.get("Market_value"));
            String league = body.get("League");
            Integer availableBalance = Integer.parseInt(body.get("Available_Balance"));
            String coach = body.get("Coach");

            Club newClub = new Club(clubName,marketValue,league,availableBalance,coach);
            

            return m1.save(newClub);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Club", e);
        }
    }
}
