package com.example.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.League;
import com.example.demo.bases.Club;
import com.example.demo.Repositories.League_Repo;
import com.example.demo.Repositories.Club_Repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/league")
public class League_Controller {

    @Autowired
    private League_Repo leagueRepo;

    @Autowired
    private Club_Repo clubRepo;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    public List<League> getAllLeagues() {
        return leagueRepo.findAll();
    }

    @GetMapping("/find/{id}")
    public League getLeagueById(@PathVariable("id") Integer id) {
        return leagueRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/remove/{id}")
    public boolean deleteLeague(@PathVariable("id") Integer id) {
        if (leagueRepo.existsById(id)) {
            leagueRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @PutMapping("/update/{id}")
    public League updateLeague(@PathVariable("id") Integer id,
                               @RequestBody Map<String, String> body) {

        Optional<League> leagueOpt = leagueRepo.findById(id);
        if (leagueOpt.isPresent()) {
            League league = leagueOpt.get();

            league.setLeagueName(body.get("leagueName"));
            league.setWinningPrice(Double.parseDouble(body.get("winningPrice")));
            league.setYear(Integer.parseInt(body.get("year")));

            try {
                Date startDate = dateFormat.parse(body.get("startDate"));
                Date endDate = dateFormat.parse(body.get("endDate"));
                league.setStartDate(startDate);
                league.setEndDate(endDate);
            } catch (ParseException e) {
                throw new RuntimeException("Invalid date format", e);
            }

            Integer winningClubId = Integer.parseInt(body.get("winningClubId"));
            Club winningClub = clubRepo.findById(winningClubId)
                    .orElseThrow(() -> new RuntimeException("Winning club not found with id " + winningClubId));
            league.setWinningClubId(winningClub);

            league.setDescription(body.get("description"));

            return leagueRepo.save(league);
        } else {
            throw new RuntimeException("League not found with id " + id);
        }
    }

    @PostMapping("/add")
    public League createLeague(@RequestBody Map<String, String> body) {
        try {
            String leagueName = body.get("leagueName");
            Double winningPrice = Double.parseDouble(body.get("winningPrice"));
            Integer year = Integer.parseInt(body.get("year"));

            Date startDate = dateFormat.parse(body.get("startDate"));
            Date endDate = dateFormat.parse(body.get("endDate"));

            Integer winningClubId = Integer.parseInt(body.get("winningClubId"));
            Club winningClub = clubRepo.findById(winningClubId)
                    .orElseThrow(() -> new RuntimeException("Winning club not found with id " + winningClubId));

            String description = body.get("description");

            League newLeague = new League(leagueName, winningPrice, year, startDate, endDate, winningClub, description);
            return leagueRepo.save(newLeague);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating League", e);
        }
    }
}
