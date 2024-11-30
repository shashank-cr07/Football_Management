<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>League Points</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css">
    <style>
    	body {
            background-image: url('views/images/jj.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            height: 100vh; /* Ensure full viewport height */
            margin: 0;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">My Football App</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="Home">Home</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container mt-4">
    <!-- Title and Dropdown Container -->
    <div class="row align-items-center">
        <div class="col-md-8">
            <h3 class="text-left">Points table for the league: ${leagueName}</h3>
        </div>
        <div class="col-md-4 text-right">
            <label for="leagueDropdown" class="form-label">View Other Leagues:</label>
            <select id="leagueDropdown" class="form-control">
                <option value="">Select a League</option>
                <!-- Options will be populated here -->
            </select>
        </div>
    </div>

    <!-- Display the table of points -->
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Position</th>
                <th>Club</th>
                <th>Wins</th>
                <th>Losses</th>
                <th>Draws</th>
                <th>Goals Scored</th>
                <th>Goals Conceded</th>
                <th>Goal Difference</th>
                <th>Points</th>
            </tr>
        </thead>
        <tbody id="pointsTableBody">
            <!-- Table rows will be populated by JavaScript -->
        </tbody>
    </table>
</div>

    <!-- Include Bootstrap JS for styling -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function() {
            const leagueId = ${leagueId}; // Ensure leagueId is passed from the backend to the JSP
            console.log(leagueId);
            if (leagueId) {
                $.ajax({
                    url: '/points/points-league?league_id=' + leagueId,
                    type: 'GET',
                    success: function(data) {
                        populateResults(data);
                    },
                    error: function() {
                        alert('Failed to fetch league points.');
                    }
                });
            } else {
                alert('League ID is missing. Please log in again.');
            }

            // Fetch and populate the dropdown for other leagues
            $.ajax({
                type: 'GET',
                url: '/league',  // Endpoint to fetch all leagues
                success: function(leagues) {
                    leagues.forEach(function(league) {
                        $('#leagueDropdown').append(new Option(league.leagueName, league.leagueId));
                    });
                },
                error: function() {
                    alert('Failed to load leagues. Please try again later.');
                }
            });

            // Event listener for dropdown change
            $('#leagueDropdown').change(function() {
                const selectedLeagueId = $(this).val();
                if (selectedLeagueId) {
                    // Redirect or refresh the page based on the selected league
                    window.location.href = '/points-club?league_id=' + selectedLeagueId;
                }
            });
        });

        // Populate the table with points data
        function populateResults(data) {
            if (!Array.isArray(data)) {
                console.error("Invalid data format. Expected an array.");
                return;
            }

            const targetTableBody = $('#pointsTableBody');
            targetTableBody.empty();  // Clear any existing rows

            data.forEach(function(result) {
                const tableRow = $('<tr></tr>');

                const positionCell = $('<td></td>').text(result.position);
                const clubCell = $('<td></td>').text(result.clubId.name);
                const winsCell = $('<td></td>').text(result.wins);
                const lossesCell = $('<td></td>').text(result.losses);
                const drawsCell = $('<td></td>').text(result.draws);
                const goalsScoredCell = $('<td></td>').text(result.goalsScored);
                const goalsConcededCell = $('<td></td>').text(result.goalsConceded);
                const goalDifferenceCell = $('<td></td>').text(result.goalDifference);
                const pointsCell = $('<td></td>').text(result.points);

                tableRow.append(positionCell, clubCell, winsCell, lossesCell, drawsCell, goalsScoredCell, goalsConcededCell, goalDifferenceCell, pointsCell);

                targetTableBody.append(tableRow);
            });
        }
    </script>
</body>
</html>
