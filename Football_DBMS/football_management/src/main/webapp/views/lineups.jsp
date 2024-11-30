<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match Lineups</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css">
</head>
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
            <h3 class="text-left">Lineups for Match ID: ${matchID}</h3>
        </div>
        <div class="col-md-4 text-right">
            <label for="matchDropdown" class="form-label">View Other Matches:</label>
            <select id="matchDropdown" class="form-control">
                <option value="">Select a Match</option>
                <!-- Options will be populated here -->
            </select>
        </div>
    </div>
	<br><br>
    <!-- Display the tables for home and away clubs -->
    <div class="row">
        <div class="col-md-6">
         <h4>Home Club: <span id="homeClubName"></span></h4>
           
            <table class="table table-striped" id="homeClubTable">
                <thead>
                    <tr>
                        <th>Position</th>
                        <th>Player Name</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody id="homeLineupTableBody">
                    <!-- Table rows for home club will be populated by JavaScript -->
                </tbody>
            </table>
             <h4 class="text-center" id="homeClubHeader"></h4> <!-- Header for Home Club -->
        </div>
        <div class="col-md-6">
                    <h4>Away Club: <span id="awayClubName"></span></h4>
        
            <table class="table table-striped" id="awayClubTable">
                <thead>
                    <tr>
                        <th>Position</th>
                        <th>Player Name</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody id="awayLineupTableBody">
                    <!-- Table rows for away club will be populated by JavaScript -->
                </tbody>
            </table>
                        <h4 class="text-center" id="awayClubHeader"></h4> <!-- Header for Away Club -->
            
        </div>
    </div>
</div>

<!-- Include Bootstrap JS for styling -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    $(document).ready(function() {
        const matchId = ${matchID}; // Ensure matchId is passed from the backend to the JSP
        console.log(matchId);
        if (matchId) {
            $.ajax({
                url: '/match-appear?match_id=' + matchId,
                type: 'GET',
                success: function(data) {
                    populateLineups(data);
                },
                error: function() {
                    alert('Failed to fetch match lineups.');
                }
            });
        } else {
            alert('Match ID is missing. Please log in again.');
        }

        // Fetch and populate the dropdown for other matches
        $.ajax({
            type: 'GET',
            url: '/Match/not-null-matches',  // Endpoint to fetch all matches
            success: function(matches) {
                matches.forEach(function(match) {
                    // Set the option value to match ID and display homeClub vs awayClub as text
                    $('#matchDropdown').append(new Option(match.homeClub.name + " vs " + match.awayClub.name +" ("+ match.date + ")", match.matchId));
                });
            },
            error: function() {
                alert('Failed to load matches. Please try again later.');
            }
        });

        // Event listener for dropdown change
        $('#matchDropdown').change(function() {
            const selectedMatchId = $(this).val();  // Get the match ID from the dropdown value
            if (selectedMatchId) {
                // Redirect or refresh the page based on the selected match
                window.location.href = '/lineups?match_id=' + selectedMatchId;
            }
        });
    });

    // Populate the tables with lineup data
    function populateLineups(data) {
        if (!Array.isArray(data)) {
            console.error("Invalid data format. Expected an array.");
            return;
        }

        const homeClubName = data[0].match.homeClub.name;  // Assuming the first object contains home club info
        const awayClubName = data[0].match.awayClub.name;  // Assuming the first object contains away club info
		
        $('h3.text-left').text('Lineups for ' + homeClubName + ' vs ' + awayClubName +" on "+ data[0].match.date);
        
        $('#homeClubHeader').text('Coach: ' + data[0].match.homeClub.coach);
        $('#awayClubHeader').text('Coach: ' + data[0].match.awayClub.coach);
        // Set home and away club names
        $('#homeClubName').text(homeClubName);
        $('#awayClubName').text(awayClubName);

        // Clear any existing rows
        $('#homeLineupTableBody').empty();
        $('#awayLineupTableBody').empty();

        // Populate the home club lineup
        data.forEach(function(lineup) {
            const tableRow = $('<tr></tr>');

            const positionCell = $('<td></td>').text(lineup.position);
            const playerNameCell = $('<td></td>').text(lineup.player.player_name);
            const playerTypeCell = $('<td></td>').text(lineup.type);

            // Check if the player is from the home club
            if (lineup.player_club === homeClubName) {
                tableRow.append(positionCell, playerNameCell,playerTypeCell);
                $('#homeLineupTableBody').append(tableRow);
            }
            // Check if the player is from the away club
            else if (lineup.player_club === awayClubName) {
                tableRow.append(positionCell, playerNameCell,playerTypeCell);
                $('#awayLineupTableBody').append(tableRow);
            }
        });
    }
</script>
</body>
</html>
