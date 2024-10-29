package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bases.Match;
import com.example.demo.bases.Club;
import com.example.demo.Repositories.Match_Repo;
import com.example.demo.Repositories.Club_Repo;

@RestController
public class Match_Controller {

    @Autowired
    private Match_Repo matchRepo;

    @Autowired
    private Club_Repo clubRepo;

    // Get all matches
    @GetMapping("/Match")
    public List<Match> getAllMatches() {
        return matchRepo.findAll();
    }

    // Get a single match by ID
    @GetMapping("/Match-find/{id}")
    public Match getSingleMatch(@PathVariable("id") Integer id) {
        return matchRepo.findById(id).orElse(null);
    }

    // Delete a match by ID
    @DeleteMapping("/Match-remove/{id}")
    public boolean deleteMatch(@PathVariable("id") Integer id) {
        if (matchRepo.findById(id).isPresent()) {
            matchRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update match details
    @PutMapping("/Match-update/{id}")
    public Match updateMatch(@PathVariable("id") Integer id, 
                             @RequestBody Map<String, String> body) {

        Optional<Match> currentOpt = matchRepo.findById(id);
        if (currentOpt.isPresent()) {
            Match current = currentOpt.get();

            Integer homeClubId = Integer.parseInt(body.get("Home_club_ID"));
            Integer awayClubId = Integer.parseInt(body.get("Away_club_ID"));

            Club homeClub = clubRepo.findById(homeClubId).orElseThrow(() ->
                    new RuntimeException("Home club not found with ID " + homeClubId));
            Club awayClub = clubRepo.findById(awayClubId).orElseThrow(() ->
                    new RuntimeException("Away club not found with ID " + awayClubId));

            current.setHomeClub(homeClub);
            current.setAwayClub(awayClub);
            current.setGoals_scored(Integer.parseInt(body.get("Goals_scored")));
            current.setGoals_conceded(Integer.parseInt(body.get("Goals_conceded")));
            current.setDate(body.get("Date"));

            matchRepo.save(current);
            return current;
        } else {
            throw new RuntimeException("Match not found with ID " + id);
        }
    }

    // Add a new match
    @PostMapping("/Match-add")
    public Match createMatch(@RequestBody Map<String, String> body) {
        try {
            Integer homeClubId = Integer.parseInt(body.get("Home_club_ID"));
            Integer awayClubId = Integer.parseInt(body.get("Away_club_ID"));

            Club homeClub = clubRepo.findById(homeClubId).orElseThrow(() ->
                    new RuntimeException("Home club not found with ID " + homeClubId));
            Club awayClub = clubRepo.findById(awayClubId).orElseThrow(() ->
                    new RuntimeException("Away club not found with ID " + awayClubId));

            Integer goalsScored = Integer.parseInt(body.get("Goals_scored"));
            Integer goalsConceded = Integer.parseInt(body.get("Goals_conceded"));
            String date = body.get("Date");

            Match newMatch = new Match(homeClub, awayClub, goalsScored, goalsConceded, date);
            return matchRepo.save(newMatch);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating match", e);
        }
    }
}
