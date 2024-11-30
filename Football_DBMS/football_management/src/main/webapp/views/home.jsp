<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .hero {
            background-image: url('views/images/background_home.jpg'); /* Relative path */
            background-size: cover;
            background-position: center 15%; /* Move the image down */
            color: white;
            padding: 100px 0; /* Adjust padding as needed */
            text-align: center;
        }
        
    /* Reduce cell padding to make columns closer together */
    .table-bordered th, .table-bordered td {
        padding: 8px !important; /* Reduce padding to make columns closer */
        font-size: 0.9rem;       /* Optional: Smaller font for compact display */
    }

    /* Make the header slightly bolder and aligned centrally */
    .thead-light th {
        background-color: #f8f9fa; /* Light gray background for headers */
        font-weight: 600;
        text-align: center;
    }
    
    /* Center-align text in result and time columns */
    #recentResultsTableBody td:nth-child(3),
    #recentResultsTableBody td:nth-child(4) {
        text-align: center;
    }
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
                <li class="nav-item" id="userInfo" style="display: none;">
                    <span class="navbar-text" id="clubName"></span>
                    <span class="navbar-text" id="userRole"></span>
                </li>
                <li class="nav-item" id="loginLink">
                    <a class="nav-link" href="#" data-toggle="modal" data-target="#loginModal">Login</a>
                </li>
                <li class="nav-item" id="signupLink">
                    <a class="nav-link" href="signup">Sign Up</a>
                </li>
                <li class="nav-item" id="logoutLink" style="display: none;">
                    <a class="nav-link" href="#" onclick="logout()">Logout</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="hero">
        <h1>Welcome to the Football Management System</h1>
        <p>Manage your teams, leagues, and transfers seamlessly.</p>
        <a href="signup" id="signupLink" class="btn btn-light">Get Started</a>
    </div>

    <div class="container mt-5">
        <h2>Favorite Club:</h2>
<div id="favoriteClub" style="display: none;">
    <div class="d-flex align-items-center">
        <!-- Club Name -->
        <h4 id="favoriteClubName" class="mr-3"></h4>
        <!-- View Points Button -->
        <button id="viewPointsButton" class="btn btn-primary">View Points</button>
    </div>
</div>

            <h5>Recent Results:</h5>
<div class="table-responsive">
    <table class="table table-striped" style="width: 100%; margin-top: 10px;">
        <thead class="thead-light">
            <tr>
                <th style="width: 20%;">Date</th>
                <th style="width: 40%;">Match</th>
                <th style="width: 20%;">Result</th>
                <th style="width: 20%;">Time</th>
            </tr>
        </thead>
        <tbody id="recentResultsTableBody">
            <!-- Recent results will be populated here -->
        </tbody>
    </table>
</div><br><br>
            <h5>Upcoming Matches:</h5>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Opponent</th>
                        <th>Venue</th>
                        <th>Time</th>
                    </tr>
                </thead>
                <tbody id="upcomingMatchesTableBody">
                    <!-- Upcoming matches will be populated here -->
                </tbody>
            </table><br><br>
            <h3>Recent Match Events of your club</h3>
<table id="matchEventsTable" class="table table-striped">
    <thead>
        <tr>
            <th>Player</th>
            <th>Match</th>
            <th>Timeline</th>
        </tr>
    </thead>
    <tbody id="matchEventsTableBody">
        <!-- Rows will be added dynamically -->
    </tbody>
</table><br><br>
<h3>Recent club Transfers</h3>
<table id="recentTransfersTable" class="table table-striped">
    <thead>
        <tr>
            <th>Player</th>
            <th>From Club</th>
            <th>To Club</th>
            <th>Transfer Fees</th>
            <th>Date</th>
        </tr>
    </thead>
    <tbody id="recentTransfersTableBody">
        <!-- Rows will be added dynamically -->
    </tbody>
</table><br><br>

            <a href="player-page" class="btn btn-primary" id="transferPageButton" style="display: none;">Go to Transfers Page</a>
			<br><br>
			<button id="viewHomePlayersStats" class="btn btn-primary mt-3">View Player Stats from Your Club</button>
			
        </div>

    <!-- Login Modal -->
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="loginModalLabel">Login</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="loginForm">
                    <div class="modal-body">
                        <input type="text" class="form-control mb-3" name="username" placeholder="Username" required>
                        <input type="password" class="form-control mb-3" name="password" placeholder="Password" required>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Welcome Modal -->
    <div class="modal fade" id="welcomeModal" tabindex="-1" aria-labelledby="welcomeModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="welcomeModalLabel">Welcome!</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="welcomeMessage"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    $('#viewHomePlayersStats').click(function() {
        window.location.href = '/player-cond?club_id=' + localStorage.getItem('clubId');
    });
    
 // Function to handle the button click and redirect to /points-club
    $('#viewPointsButton').on('click', function() {
        const clubId = localStorage.getItem('clubId');  // Assuming you have a way to get the current club ID
        if (clubId) {
        	$.ajax({
        	    url: 'Club/find?id=' + clubId,  // Use clubId as a query parameter
        	    type: 'GET',
        	    success: function(res) {
        	        if (res && res.leagueId) {
        	            console.log("League ID:", res.leagueId.leagueId);  // Log the leagueId for debugging
        	            // Now you can use the leagueId to make another request to /points-club
        	            window.location.href = '/points-club?league_id=' + res.leagueId.leagueId;  // Redirect to the points page
        	        } else {
        	            console.error("League ID not found in the response.");
        	        }
        	    },
        	    error: function() {
        	        alert('Failed to fetch club data.');
        	    }
        	});

            
        } else {
            alert('Club ID is missing. Please log in again.');
        }
    });

        // Check if user is logged in and populate favorite club info
        // Assume the user role is stored in a JavaScript variable
var userRole = localStorage.getItem('userRole'); // You can replace this with the actual user role fetched from your backend or session

// Check if the user is an admin and show the button
if (userRole === "admin") {
    document.getElementById("transferPageButton").style.display = "inline-block"; // Show the button
}

$(document).ready(function() {
    const loggedIn = localStorage.getItem('loggedIn');
    const favoriteClub = localStorage.getItem('favoriteClub');
    const username = localStorage.getItem('username');
    const clubName = localStorage.getItem('clubName');
    const userRole = localStorage.getItem('userRole');
    const clubId = localStorage.getItem('clubId'); // Get clubId from local storage

    if (loggedIn === 'true') {
        $('#userInfo').show();
        $('#clubName').text('Name: ' + (username ? username : 'No Name') + ' | ');
        $('#userRole').text('Role: ' + (userRole ? userRole : 'Unknown Role'));
        $('#loginLink').hide();
        $('#signupLink').hide();
        $('#logoutLink').show();
        

        // Populate favorite club info if available
        if (clubId) {
            $('#favoriteClub').show();
            $('#favoriteClubName').text(clubName);

            // Fetch recent results on page load
            fetchRecentResults(clubId);
            fetchNextResults(clubId);
            fetchRecentTransfers(clubId);
            
        } else {
            alert('Favorite club information is missing. Please re-login.');
        }
    } else {
        alert('Please log in or sign up to continue.');
        $('#loginModal').modal('show');
    }
});

function fetchNextResults(clubId) {
    if (clubId) {
        $.ajax({
            url: '/Match/next-matches?club_id=' + clubId,
            type: 'GET',
            success: function(data) {
                populateNextResults(data,clubId);
            },
            error: function() {
                alert('Failed to fetch recent results.');
            }
        });
    } else {
        alert('Club ID is missing. Please log in again.');
    }
}

function populateNextResults(data, clubId) {
    // Clear any existing rows
    $('#upcomingMatchesTableBody').empty();

    // Iterate over the match data and append rows to the table
    data.forEach(function(match) {
    	console.log(match);
        // Determine the opponent and venue based on the club ID
        let opponent, venue;
        if (match.homeClub.clubId == clubId) {
            opponent = match.awayClub.name;
            venue = match.homeClub.name +"(Home)"; // Venue is home club's name if clubId matches home club
        } else if (match.awayClub.clubId == clubId) {
            opponent = match.homeClub.name;
            venue = match.homeClub.name+"(Away)"; // Venue is away club's name if clubId matches away club
        } else {
        	console.log("JO");
            return; // Skip this match if the club ID is neither in home nor away
        }

        // Create a table row
        const tableRow = $('<tr></tr>');
        
        // Create table cells and set their text content
        const dateCell = $('<td></td>').text(match.date);
        const opponentCell = $('<td></td>').text(opponent);
        const venueCell = $('<td></td>').text(venue);
        const timeCell = $('<td></td>').text(match.time);
        
        // Append the cells to the row
        tableRow.append(dateCell, opponentCell, venueCell, timeCell);
        
        // Append the row to the table body
        $('#upcomingMatchesTableBody').append(tableRow);
    });
}


// Fetch recent results from the server using club ID
function fetchRecentResults(clubId) {
    if (clubId) {
        $.ajax({
            url: '/Match/latest?club_id=' + clubId,
            type: 'GET',
            success: function(data) {
                populateResults(data);
            },
            error: function() {
                alert('Failed to fetch recent results.');
            }
        });
    } else {
        alert('Club ID is missing. Please log in again.');
    }
}

// Populate results in the table
function populateResults(data) {
    if (!Array.isArray(data)) {
        console.error("Invalid data format. Expected an array.");
        return;
    }

    const targetTableBody = $('#recentResultsTableBody');
    targetTableBody.empty();

    data.forEach(function(result) {
        fetchMatchEvents(result.matchId);

        const tableRow = $('<tr></tr>');
        const dateCell = $('<td></td>').text(result.date);
        const opponentCell = $('<td></td>').text(result.homeClub.name + " vs " + result.awayClub.name);

        // Create the "View Match Lineup" button
        const lineupButton = $('<button></button>')
        .text("View Match Lineup")
        .addClass("btn btn-secondary btn-sm ml-2") // Gray color, small size, and margin for spacing
        .on('click', function() {
            window.location.href = '/lineups?match_id=' + result.matchId;
        });

        // Append the button to the opponentCell
        opponentCell.append("&emsp;&emsp;&emsp;",lineupButton);

        const scoreCell = $('<td></td>').text(result.goalsScored + " - " + result.goalsConceded);
        const timeCell = $('<td></td>').text(result.time);

        // Append cells to the row
        tableRow.append(dateCell, opponentCell, scoreCell, timeCell);
        targetTableBody.append(tableRow);
    });

}

function fetchMatchEvents(matchId) {
    $.ajax({
        url: '/Match/latest-events', // Assuming this endpoint returns the event data
        data: { match_id: matchId },
        method: 'GET',
        success: function(data) {
            populateMatchEvents(data);
        },
        error: function() {
            alert('Failed to fetch match events.');
        }
    });
}






// This function will populate the match events into the table
function populateMatchEvents(data) {
    // Clear any existing rows
    $('#matchEventsTableBody').empty();

    // Iterate over the event data and append rows to the table
    data.forEach(function(event) {
        // Create a table row
        const tableRow = $('<tr></tr>');
        
        // Create table cells and set their text content
        const playerCell = $('<td></td>').text(event.playerName +" ("+ event.player_club +")");
        const matchCell = $('<td></td>').text(event.homeClub+" vs "+ event.awayClub);
        const eventCell = $('<td></td>').text(event.description+" at "+event.minOccured +" min");
        
        // Append the cells to the row
        tableRow.append(playerCell,matchCell,eventCell);
        
        // Append the row to the table body
        $('#matchEventsTableBody').append(tableRow);
    });

}

function fetchRecentTransfers(clubId) {
    if (clubId) {
        $.ajax({
            url: '/transfers-club?club_id=' + clubId,
            type: 'GET',
            success: function(data) {
                populateTransfers(data);  // Assuming you have a function to populate the transfers table
            },
            error: function() {
                alert('Failed to fetch recent transfers.');
            }
        });
    } else {
        alert('Club ID is missing. Please log in again.');
    }
}
function populateTransfers(data) {
    if (!Array.isArray(data)) {
        console.error("Invalid data format. Expected an array.");
        return;
    }

    const targetTableBody = $('#recentTransfersTableBody');
    targetTableBody.empty();

    data.forEach(function(transfer) {
        const tableRow = $('<tr></tr>');
        
        // Assuming transfer has these fields: player_name, from_club_name, to_club_name, transfer_fees, and date
        const playerCell = $('<td></td>').text(transfer.player.player_name);
        const fromClubCell = $('<td></td>').text(transfer.fromClub.name);
        const toClubCell = $('<td></td>').text(transfer.toClub.name);
        const feesCell = $('<td></td>').text(transfer.transfer_fees+"€");
        const dateCell = $('<td></td>').text(transfer.date);
        
        tableRow.append(playerCell, fromClubCell, toClubCell, feesCell, dateCell);
        targetTableBody.append(tableRow);
    });
}


        // Handle login form submission
        $('#loginForm').on('submit', function(event) {
            event.preventDefault(); // Prevent form from submitting normally
            const formData = $(this).serialize();
            console.log("Form data being sent:", formData);
            
            $.ajax({
                type: 'POST',
                url: '/login/authenticate',
                data: $(this).serialize(),
                success: function(response) {
                	console.log(data);
                    const username = response[0] ? response[0] : 'Unknown User';
                    const clubName = response[1] ? response[1] : 'No Club Assigned';
                    const userRole = response[2] ? response[2] : 'Unknown Role';
                    const clubId = response[3] ? response[3] : null; // Assuming clubId is returned

                    // Store user info in localStorage
                    localStorage.setItem('loggedIn', 'true');
                    localStorage.setItem('username', username);
                    localStorage.setItem('clubName', clubName);
                    localStorage.setItem('userRole', userRole);
                    localStorage.setItem('clubId', clubId);

                    $('#loginModal').modal('hide');
                    $('#welcomeMessage').text('Hello, ' + username + '!');
                    $('#welcomeModal').modal('show');

                    // Update UI with favorite club
                    $('#clubName').text('Club: ' + clubName + ' | ');
                    $('#userRole').text('Role: ' + userRole);
                    $('#userInfo').show();
                    $('#loginLink').hide();
                    $('#signupLink').hide();
                    $('#logoutLink').show();
                    $('#favoriteClub').show();
                    $('#favoriteClubName').text(clubName);
                    
                    // Fetch recent results after login
                    fetchRecentResults(clubId);
                    fetchNextResults(clubId);
                    fetchRecentTransfers(clubId);
                    if (userRole === "admin") {
                        document.getElementById("transferPageButton").style.display = "inline-block"; // Show the button
                    }
                },
                error: function() {
                    alert('Login failed. Please check your credentials.');
                }
            });
        });

        // Logout function
        function logout() {
            localStorage.clear(); // Clear localStorage
            location.reload(); // Reload the page
        }
    </script>
</body>
</html>
