package com.example.demo.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.Transfers;
import com.example.demo.bases.Player;
import com.example.demo.bases.Club;
import com.example.demo.Repositories.Transfers_Repo;
import com.example.demo.Repositories.Player_Repo;
import com.example.demo.Repositories.Club_Repo;

@RestController
public class Transfers_Controller {

    @Autowired
    private Transfers_Repo transfersRepo;

    @Autowired
    private Player_Repo playerRepo;

    @Autowired
    private Club_Repo clubRepo;

    // Get all transfers
    @GetMapping("/Transfers")
    public List<Transfers> getAllTransfers() {
        return transfersRepo.findAll();
    }

    // Get a single transfer by ID
    @GetMapping("/Transfers-find/{id}")
    public Transfers getSingleTransfer(@PathVariable("id") Integer id) {
        return transfersRepo.findById(id).orElse(null);
    }

    // Delete a transfer by ID
    @DeleteMapping("/Transfers-remove/{id}")
    public boolean deleteTransfer(@PathVariable("id") Integer id) {
        if (transfersRepo.findById(id).isPresent()) {
            transfersRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update transfer details
    @PutMapping("/Transfers-update/{id}")
    public Transfers updateTransfer(@PathVariable("id") Integer id, 
                                    @RequestBody Map<String, String> body) {

        Optional<Transfers> currentOpt = transfersRepo.findById(id);
        if (currentOpt.isPresent()) {
            Transfers current = currentOpt.get();

            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Integer fromClubId = Integer.parseInt(body.get("From_club_id"));
            Integer toClubId = Integer.parseInt(body.get("To_club_id"));

            Player player = playerRepo.findById(playerId).orElseThrow(() -> 
                new RuntimeException("Player not found with ID " + playerId));
            Club fromClub = clubRepo.findById(fromClubId).orElseThrow(() -> 
                new RuntimeException("From club not found with ID " + fromClubId));
            Club toClub = clubRepo.findById(toClubId).orElseThrow(() -> 
                new RuntimeException("To club not found with ID " + toClubId));

            current.setPlayer(player);
            current.setFromClub(fromClub);
            current.setToClub(toClub);
            current.setTransfer_fees(Integer.parseInt(body.get("Transfer_fees")));
            current.setClauses(body.get("Clauses"));
            current.setDate(body.get("Date"));

            return transfersRepo.save(current);
        } else {
            throw new RuntimeException("Transfer not found with ID " + id);
        }
    }

    // Add a new transfer
    @PostMapping("/Transfers-add")
    public Transfers createTransfer(@RequestBody Map<String, String> body) {
        try {
            Integer playerId = Integer.parseInt(body.get("Player_ID"));
            Integer fromClubId = Integer.parseInt(body.get("From_club_id"));
            Integer toClubId = Integer.parseInt(body.get("To_club_id"));

            Player player = playerRepo.findById(playerId).orElseThrow(() -> 
                new RuntimeException("Player not found with ID " + playerId));
            Club fromClub = clubRepo.findById(fromClubId).orElseThrow(() -> 
                new RuntimeException("From club not found with ID " + fromClubId));
            Club toClub = clubRepo.findById(toClubId).orElseThrow(() -> 
                new RuntimeException("To club not found with ID " + toClubId));

            Integer transferFees = Integer.parseInt(body.get("Transfer_fees"));
            String clauses = body.get("Clauses");
            String date = body.get("Date");

            Transfers newTransfer = new Transfers(player, fromClub, toClub, transferFees, clauses, date);
            return transfersRepo.save(newTransfer);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating transfer", e);
        }
    }
}
