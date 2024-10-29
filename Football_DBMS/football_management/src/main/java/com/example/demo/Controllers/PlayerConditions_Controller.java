package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.PlayerConditions;
import com.example.demo.bases.Player;
import com.example.demo.Repositories.PlayerConditions_Repo;
import com.example.demo.Repositories.Player_Repo;

@RestController
public class PlayerConditions_Controller {

    @Autowired
    private PlayerConditions_Repo playerConditionsRepo;

    @Autowired
    private Player_Repo playerRepo;

    // Get all player conditions
    @GetMapping("/PlayerConditions")
    public List<PlayerConditions> getAllPlayerConditions() {
        return playerConditionsRepo.findAll();
    }

    // Get a single player condition by ID
    @GetMapping("/PlayerConditions-find/{id}")
    public PlayerConditions getPlayerConditionById(@PathVariable("id") Integer id) {
        return playerConditionsRepo.findById(id).orElse(null);
    }

    // Delete a player condition by ID
    @DeleteMapping("/PlayerConditions-remove/{id}")
    public boolean deletePlayerCondition(@PathVariable("id") Integer id) {
        if (playerConditionsRepo.findById(id).isPresent()) {
            playerConditionsRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update a player condition
    @PutMapping("/PlayerConditions-update/{id}")
    public PlayerConditions updatePlayerCondition(
        @PathVariable("id") Integer id, @RequestBody Map<String, String> body) {

        Optional<PlayerConditions> currentOpt = playerConditionsRepo.findById(id);
        if (currentOpt.isPresent()) {
            PlayerConditions current = currentOpt.get();

            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID " + playerId));

            current.setPlayer(player);
            current.setInjury(body.get("Injury"));
            current.setRecent_form(body.get("Recent_form"));
            current.setDescription(body.get("Description"));
            current.setTraining_details(body.get("Training_details"));

            return playerConditionsRepo.save(current);
        } else {
            throw new RuntimeException("Player condition not found with ID " + id);
        }
    }

    // Add a new player condition
    @PostMapping("/PlayerConditions-add")
    public PlayerConditions createPlayerCondition(@RequestBody Map<String, String> body) {
        try {
            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID " + playerId));

            String injury = body.get("Injury");
            String recentForm = body.get("Recent_form");
            String description = body.get("Description");
            String trainingDetails = body.get("Training_details");

            PlayerConditions newCondition = new PlayerConditions(player, injury, recentForm, description, trainingDetails);
            return playerConditionsRepo.save(newCondition);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating player condition", e);
        }
    }
}
