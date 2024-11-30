package com.example.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import com.example.demo.Repositories.Club_Repo;
import com.example.demo.Repositories.League_Repo;


@Controller
public class Main_Controller {
	
	@Autowired
	League_Repo league_repo;
	
	@Autowired
	Club_Repo club_repo;
	
	@GetMapping("/Home")
    public String showHomePage() {
        return "home"; 
    }
	@GetMapping("/signup")
    public String showSignUpPage() {
        return "signup"; 
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Check for 404 error and redirect to custom page
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == 404) {
                return "error"; // Name of your error.jsp file
            }
        }
        return "error"; // Default error page
    }
    @GetMapping("/player-page")
    public String showPlayerUpPage() {
        return "player"; 
    }
    @GetMapping("/points-club")
    public String showPointsByLeaguePage(HttpServletRequest request) {
        // Fetch the league_id from the request parameters
        String leagueId = request.getParameter("league_id");
        String leagueName = league_repo.findLeagueNameById(Integer.parseInt(leagueId));
        // If league_id is provided, pass it as a request attribute to the view (or use it directly in the frontend).
        if (leagueId != null) {
            request.setAttribute("leagueId", leagueId);
            request.setAttribute("leagueName", leagueName);
        }

        // Return the view name for the points-club page
        return "points-club"; // points-club.html (or points-club.jsp, depending on your template engine)
    }
    @GetMapping("/lineups")
    public String showLinups(HttpServletRequest request) {
        // Fetch the league_id from the request parameters
        String leagueId = request.getParameter("match_id");
        // If league_id is provided, pass it as a request attribute to the view (or use it directly in the frontend).
        if (leagueId != null) {
            request.setAttribute("matchID", leagueId);
        }

        // Return the view name for the points-club page
        return "lineups"; // points-club.html (or points-club.jsp, depending on your template engine)
    }
    @GetMapping("/player-cond")
    public String showPlayerco(HttpServletRequest request) {
        // Fetch the club_id from the request parameters
        String clubId = request.getParameter("club_id");
        String clubName= club_repo.findClubNameById(Integer.parseInt(clubId));

        // If club_id is provided, pass it as a request attribute to the view
        if (clubId != null) {
            request.setAttribute("clubID", clubId);
            request.setAttribute("clubName", clubName);
        }

        // Return the view name for the player conditions page
        return "play-cond"; // Make sure this corresponds to play-cond.jsp (or play-cond.html if using Thymeleaf)
    }

}
