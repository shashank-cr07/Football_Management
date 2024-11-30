package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bases.Player;
import com.example.demo.bases.Club;
import com.example.demo.Repositories.Player_Repo;
import com.example.demo.Repositories.Club_Repo;

@RestController
public class Player_Controller {

    @Autowired
    private Player_Repo playerRepo;

    @Autowired
    private Club_Repo clubRepo;

    // Get all players
    @GetMapping("/Player")
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }
    
    //TO get all players from a club
    @GetMapping("/Player-by-club")
    public List<Player> getAllPlayers(@RequestParam(value = "club_id", required = true) Integer clubId) {
        if (clubId != null) {
            return playerRepo.findByClubId(clubId);
        }
        else return null;
    }
    // Get a single player by ID
    @GetMapping("/Player-find/{id}")
    public Player getSinglePlayer(@PathVariable("id") Integer id) {
        return playerRepo.findById(id).orElse(null);
    }

    // Delete a player by ID
    @DeleteMapping("/Player-remove/{id}")
    public boolean deletePlayer(@PathVariable("id") Integer id) {
        if (playerRepo.findById(id).isPresent()) {
            playerRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update player details
    @PutMapping("/Player-update/{id}")
    public Player updatePlayer(@PathVariable("id") Integer id, 
                               @RequestBody Map<String, String> body) {
        Optional<Player> currentOpt = playerRepo.findById(id);
        if (currentOpt.isPresent()) {
            Player current = currentOpt.get();

            current.setPlayer_name(body.get("Player_name"));
            current.setPosition(body.get("Position"));
            current.setPlayer_Valuation(Integer.parseInt(body.get("Player_Valuation")));

            Integer clubId = Integer.parseInt(body.get("Current_Club_ID"));
            Club club = clubRepo.findById(clubId).orElseThrow(() -> 
                    new RuntimeException("Club not found with ID " + clubId));
            current.setCurrent_Club(club);

            playerRepo.save(current);
            return current;
        } else {
            throw new RuntimeException("Player not found with ID " + id);
        }
    }

    // Add a new player
    @PostMapping("/Player-add")
    public Player createPlayer(@RequestBody Map<String, String> body) {
        try {
            String playerName = body.get("Player_name");
            String position = body.get("Position");
            Integer valuation = Integer.parseInt(body.get("Player_Valuation"));

            Integer clubId = Integer.parseInt(body.get("Current_Club_ID"));
            Club club = clubRepo.findById(clubId).orElseThrow(() -> 
                    new RuntimeException("Club not found with ID " + clubId));

            Player newPlayer = new Player(playerName, position, valuation, club);
            return playerRepo.save(newPlayer);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating player", e);
        }
    }
    
}
