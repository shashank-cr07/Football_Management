package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.MatchAppearance;
import com.example.demo.bases.Match;
import com.example.demo.bases.Player;
import com.example.demo.Repositories.MatchAppearance_Repo;
import com.example.demo.Repositories.Match_Repo;
import com.example.demo.Repositories.Player_Repo;

@RestController
public class MatchAppearance_Controller {

    @Autowired
    private MatchAppearance_Repo matchAppearanceRepo;

    @Autowired
    private Match_Repo matchRepo;

    @Autowired
    private Player_Repo playerRepo;

    // Get all match appearances
    @GetMapping("/MatchAppearance")
    public List<MatchAppearance> getAllMatchAppearances() {
        return matchAppearanceRepo.findAll();
    }

    // Get a single match appearance by ID
    @GetMapping("/MatchAppearance-find/{id}")
    public MatchAppearance getMatchAppearanceById(@PathVariable("id") Integer id) {
        return matchAppearanceRepo.findById(id).orElse(null);
    }

    // Delete a match appearance by ID
    @DeleteMapping("/MatchAppearance-remove/{id}")
    public boolean deleteMatchAppearance(@PathVariable("id") Integer id) {
        if (matchAppearanceRepo.findById(id).isPresent()) {
            matchAppearanceRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update a match appearance
    @PutMapping("/MatchAppearance-update/{id}")
    public MatchAppearance updateMatchAppearance(
        @PathVariable("id") Integer id, @RequestBody Map<String, String> body) {

        Optional<MatchAppearance> currentOpt = matchAppearanceRepo.findById(id);
        if (currentOpt.isPresent()) {
            MatchAppearance current = currentOpt.get();

            Integer matchId = Integer.parseInt(body.get("Match_ID"));
            Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with ID " + matchId));

            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID " + playerId));

            current.setDate(body.get("Date"));
            current.setMatch(match);
            current.setPlayer(player);
            current.setPosition(body.get("Position"));
            current.setType(body.get("Type"));
      
            return matchAppearanceRepo.save(current);
        } else {
            throw new RuntimeException("Match appearance not found with ID " + id);
        }
    }

    // Add a new match appearance
    @PostMapping("/MatchAppearance-add")
    public MatchAppearance createMatchAppearance(@RequestBody Map<String, String> body) {
        try {
            String date = body.get("Date");

            Integer matchId = Integer.parseInt(body.get("Match_ID"));
            Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with ID " + matchId));

            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID " + playerId));

            String position = body.get("Position");
            String type = body.get("Type");
            String player_club = body.get("player_club");
            MatchAppearance newAppearance = new MatchAppearance(date, match, player, position, type,player_club);
            return matchAppearanceRepo.save(newAppearance);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating match appearance", e);
        }
    }
    @GetMapping("/match-appear")
    @ResponseBody
    public List<MatchAppearance> getMatchAppearances(@RequestParam("match_id") Integer matchId) {
        return matchAppearanceRepo.findMatchfromId(matchId);
    }


}
