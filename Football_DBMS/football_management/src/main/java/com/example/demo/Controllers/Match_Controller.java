package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.bases.Match;
import com.example.demo.bases.Club;
import com.example.demo.bases.League;
import com.example.demo.Repositories.Match_Repo;
import com.example.demo.Repositories.Club_Repo;
import com.example.demo.Repositories.League_Repo;

import java.util.*;

@RestController
@RequestMapping("/Match")
public class Match_Controller {

    @Autowired
    private Match_Repo matchRepo;

    @Autowired
    private Club_Repo clubRepo;

    @Autowired
    private League_Repo leagueRepo;

    @GetMapping
    public List<Match> getAllMatches() {
        return matchRepo.findAll();
    }
    @GetMapping("/not-null-matches")
    public List<Match> getAllPresentMatches() {
        return matchRepo.findLatestPresentMatchesByClub();
    }
    @GetMapping("/find/{id}")
    public Match getMatchById(@PathVariable("id") Integer id) {
        return matchRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/remove/{id}")
    public boolean deleteMatch(@PathVariable("id") Integer id) {
        if (matchRepo.existsById(id)) {
            matchRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @PutMapping("/update/{id}")
    public Match updateMatch(@PathVariable("id") Integer id,
                             @RequestBody Map<String, String> body) {

        Optional<Match> matchOpt = matchRepo.findById(id);
        if (matchOpt.isPresent()) {
            Match match = matchOpt.get();

            Integer homeClubId = Integer.parseInt(body.get("homeClub"));
            Integer awayClubId = Integer.parseInt(body.get("awayClub"));
            Integer leagueId = Integer.parseInt(body.get("league_id"));

            Club homeClub = clubRepo.findById(homeClubId)
                    .orElseThrow(() -> new RuntimeException("Home club not found with id " + homeClubId));
            Club awayClub = clubRepo.findById(awayClubId)
                    .orElseThrow(() -> new RuntimeException("Away club not found with id " + awayClubId));
            League league = leagueRepo.findById(leagueId)
                    .orElseThrow(() -> new RuntimeException("League not found with id " + leagueId));

            match.setHomeClub(homeClub);
            match.setAwayClub(awayClub);
            match.setLeague(league);

            match.setGoalsScored(Integer.parseInt(body.get("goals_scored")));
            match.setGoalsConceded(Integer.parseInt(body.get("goals_conceded")));
            match.setDate(body.get("date"));
            match.setTime(body.get("time"));

            return matchRepo.save(match);
        } else {
            throw new RuntimeException("Match not found with id " + id);
        }
    }

    @PostMapping("/add")
    public Match createMatch(@RequestBody Map<String, String> body) {
        try {
            Integer homeClubId = Integer.parseInt(body.get("homeClub"));
            Integer awayClubId = Integer.parseInt(body.get("awayClub"));
            Integer leagueId = Integer.parseInt(body.get("league_id"));

            Club homeClub = clubRepo.findById(homeClubId)
                    .orElseThrow(() -> new RuntimeException("Home club not found with id " + homeClubId));
            Club awayClub = clubRepo.findById(awayClubId)
                    .orElseThrow(() -> new RuntimeException("Away club not found with id " + awayClubId));
            League league = leagueRepo.findById(leagueId)
                    .orElseThrow(() -> new RuntimeException("League not found with id " + leagueId));

            Integer goalsScored = Integer.parseInt(body.get("goals_scored"));
            Integer goalsConceded = Integer.parseInt(body.get("goals_conceded"));
            String date = body.get("date");
            String time = body.get("time");


            Match newMatch = new Match(homeClub, awayClub, goalsScored, goalsConceded, date, league,time);
            return matchRepo.save(newMatch);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format", e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Match", e);
        }
    }
    @GetMapping("/latest")
    public List<Match> getLatestMatchesByClub(@RequestParam(value = "club_id", required = false) Integer clubId) {
        return matchRepo.findLatestMatchesByClub(clubId);
    }
    @GetMapping("/next-matches")
    public List<Match> getNextMatchesByClubId(@RequestParam(value = "club_id", required = false) Integer clubId) {
        return matchRepo.getNextMatchesByClub(clubId);
    }
    @GetMapping("/latest-events")
    public List<Map<String, Object>> getLatestEvents(@RequestParam(value = "match_id", required = false) Integer matchId) {
        if (matchId == null) {
            return new ArrayList<>(); // Return an empty list if matchId is null
        }

        // Call the repository method to get match events
        List<Object[]> results = matchRepo.getAllMatchEvents(matchId);
        List<Map<String, Object>> matchEvents = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> eventMap = new HashMap<>();
            eventMap.put("eventId", result[0]);          // Event ID
            eventMap.put("playerName", result[1]);       // Player Name
            eventMap.put("player_club", result[2]);
            eventMap.put("homeClub", result[3]);         // Home Club
            eventMap.put("awayClub", result[4]);         // Away Club
            eventMap.put("minOccured", result[5]);       // Minute Occurred
            eventMap.put("description", result[6]);      // Description

            matchEvents.add(eventMap);
        }

        return matchEvents; // Return the list of events
    }
}
