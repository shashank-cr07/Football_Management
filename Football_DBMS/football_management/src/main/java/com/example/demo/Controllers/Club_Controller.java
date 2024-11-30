package com.example.demo.Controllers;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.Club;
import com.example.demo.bases.League;
import com.example.demo.Repositories.Club_Repo;
import com.example.demo.Repositories.League_Repo;

@RestController
@RequestMapping("/Club")
public class Club_Controller {

    @Autowired
    private Club_Repo clubRepo;

    @Autowired
    private League_Repo leagueRepo;

    // Get all clubs
    @GetMapping
    public List<Club> getAllClubs(@RequestParam(value = "league_id", required = false) Integer leagueId) {
        if (leagueId != null) {
            // Fetch clubs by league ID if provided
            Optional<League> league = leagueRepo.findById(leagueId);
            return league.map(clubRepo::findByLeagueId).orElseGet(List::of);
        }
        return clubRepo.findAll(); // Return all clubs if no league ID is provided
    }

    // Get a club by ID
    @GetMapping("/find")
    public Club getClubById(@RequestParam("id") Integer id) {
        return clubRepo.findById(id).orElse(null);
    }


    // Delete a club by ID
    @DeleteMapping("/remove/{id}")
    public boolean deleteClub(@PathVariable("id") Integer id) {
        if (clubRepo.existsById(id)) {
            clubRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update an existing club by ID
    @PutMapping("/update/{id}")
    public Club updateClub(@PathVariable("id") Integer id,
                           @RequestBody Map<String, String> body) {

        Optional<Club> clubOpt = clubRepo.findById(id);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();

            club.setName(body.get("name"));
            club.setMarketValue(new BigDecimal(body.get("marketValue")));

            // Fetch the League entity using leagueId provided in the request body
            Integer leagueId = Integer.parseInt(body.get("leagueId"));
            League league = leagueRepo.findById(leagueId).orElseThrow(() -> 
                new RuntimeException("League not found with id " + leagueId)
            );
            club.setLeagueId(league);  // Set the League entity

            club.setAvailableBalance(new BigDecimal(body.get("availableBalance")));
            club.setCoach(body.get("coach"));

            return clubRepo.save(club);
        } else {
            throw new RuntimeException("Club not found with id " + id);
        }
    }

    // Create a new club
    @PostMapping("/add")
    public Club createClub(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            BigDecimal marketValue = new BigDecimal(body.get("marketValue"));

            // Fetch the League entity using leagueId provided in the request body
            Integer leagueId = Integer.parseInt(body.get("leagueId"));
            League league = leagueRepo.findById(leagueId).orElseThrow(() -> 
                new RuntimeException("League not found with id " + leagueId)
            );

            BigDecimal availableBalance = new BigDecimal(body.get("availableBalance"));
            String coach = body.get("coach");

            Club newClub = new Club(name, marketValue, league, availableBalance, coach);
            return clubRepo.save(newClub);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Club", e);
        }
    }
    
    @GetMapping("/get-club-id")
    public Integer getClubId(@RequestParam("clubName") String clubName) {
      
        return clubRepo.findClubIdByName(clubName);

    }
}
