package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.MatchEvents;
import com.example.demo.bases.Match;
import com.example.demo.bases.Player;
import com.example.demo.Repositories.MatchEvents_Repo;
import com.example.demo.Repositories.Match_Repo;
import com.example.demo.Repositories.Player_Repo;

@RestController
public class MatchEvents_Controller {

    @Autowired
    private MatchEvents_Repo matchEventsRepo;

    @Autowired
    private Match_Repo matchRepo;

    @Autowired
    private Player_Repo playerRepo;

    // Get all match events
    @GetMapping("/MatchEvents")
    public List<MatchEvents> getAllMatchEvents() {
        return matchEventsRepo.findAll();
    }

    // Get a single match event by ID
    @GetMapping("/MatchEvents-find/{id}")
    public MatchEvents getMatchEventById(@PathVariable("id") Integer id) {
        return matchEventsRepo.findById(id).orElse(null);
    }

    // Delete a match event by ID
    @DeleteMapping("/MatchEvents-remove/{id}")
    public boolean deleteMatchEvent(@PathVariable("id") Integer id) {
        if (matchEventsRepo.findById(id).isPresent()) {
            matchEventsRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update a match event
    @PutMapping("/MatchEvents-update/{id}")
    public MatchEvents updateMatchEvent(
        @PathVariable("id") Integer id, @RequestBody Map<String, String> body) {

        Optional<MatchEvents> currentOpt = matchEventsRepo.findById(id);
        if (currentOpt.isPresent()) {
            MatchEvents current = currentOpt.get();

            Integer matchId = Integer.parseInt(body.get("Match_ID"));
            Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with ID " + matchId));

            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID " + playerId));

            current.setMatch(match);
            current.setPlayer(player);
            current.setMin_occured(Integer.parseInt(body.get("Min_occured")));
            current.setDescription(body.get("Description"));

            return matchEventsRepo.save(current);
        } else {
            throw new RuntimeException("Match event not found with ID " + id);
        }
    }

    // Add a new match event
    @PostMapping("/MatchEvents-add")
    public MatchEvents createMatchEvent(@RequestBody Map<String, String> body) {
        try {
            Integer matchId = Integer.parseInt(body.get("Match_ID"));
            Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with ID " + matchId));

            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID " + playerId));

            Integer minOccured = Integer.parseInt(body.get("Min_occured"));
            String description = body.get("Description");

            MatchEvents newEvent = new MatchEvents(match, player, minOccured, description);
            return matchEventsRepo.save(newEvent);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating match event", e);
        }
    }
}
