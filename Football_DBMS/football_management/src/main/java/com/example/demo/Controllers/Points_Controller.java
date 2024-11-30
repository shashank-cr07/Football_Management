package com.example.demo.Controllers;

import com.example.demo.bases.Points;
import com.example.demo.Repositories.Points_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/points")
public class Points_Controller {

    @Autowired
    private Points_Repo Points_Repo;

    // Get all points records
    @GetMapping
    public List<Points> getAllPoints() {
        return Points_Repo.findAll();
    }

    // Get a single points record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Points> getPointsById(@PathVariable Integer id) {
        Optional<Points> points = Points_Repo.findById(id);
        return points.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new points record
    @PostMapping
    public Points createPoints(@RequestBody Points points) {
        return Points_Repo.save(points);
    }

    // Update an existing points record by ID
    @PutMapping("/{id}")
    public ResponseEntity<Points> updatePoints(@PathVariable Integer id, @RequestBody Points pointsDetails) {
        Optional<Points> optionalPoints = Points_Repo.findById(id);

        if (optionalPoints.isPresent()) {
            Points points = optionalPoints.get();
            points.setLeagueId(pointsDetails.getLeagueId());
            points.setClubId(pointsDetails.getClubId());
            points.setPosition(pointsDetails.getPosition());
            points.setWins(pointsDetails.getWins());
            points.setLosses(pointsDetails.getLosses());
            points.setDraws(pointsDetails.getDraws());
            points.setGoalsScored(pointsDetails.getGoalsScored());
            points.setGoalsConceded(pointsDetails.getGoalsConceded());
            points.setGoalDifference(pointsDetails.getGoalDifference());
            points.setPoints(pointsDetails.getPoints());

            Points updatedPoints = Points_Repo.save(points);
            return ResponseEntity.ok(updatedPoints);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a points record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoints(@PathVariable Integer id) {
        Optional<Points> points = Points_Repo.findById(id);

        if (points.isPresent()) {
            Points_Repo.delete(points.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/points-league")
    public List<Points> getPointsByLeague(@RequestParam("league_id") int leagueId) {
        return Points_Repo.findPointsByLeague(leagueId);
    }
}
