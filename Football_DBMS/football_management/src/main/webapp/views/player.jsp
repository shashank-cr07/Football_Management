<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfer Player</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
    <!-- Home link on the top right -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">My Football App</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <!-- Home Button -->
            <li class="nav-item">
                <a class="nav-link" href="Home">Home</a>
            </li>
            <!-- Login Button (if applicable) -->
            
        </ul>
    </div>
</nav>


    <div class="container mt-5">
        <h1 class="text-center">Transfer Player</h1>

        <!-- Choose Player Section -->
        <h4>Choose Player</h4>
        <form id="transferForm">
            <div class="form-group">
                <label for="playerLeague">League</label>
                <select class="form-control" id="playerLeague">
                    <option value="">Select a League</option>
                </select>
            </div>
            <div class="form-group">
                <label for="playerClub">Club</label>
                <select class="form-control" id="playerClub">
                    <option value="">Select a League first</option>
                </select>
            </div>
            <div class="form-group">
                <label for="player">Player</label>
                <select class="form-control" id="player">
                    <option value="">Select a Club first</option>
                </select>
            </div>

            <!-- Choose Transfer Club Section -->
            <h4>Choose Transfer Club</h4>
            <div class="form-group">
                <label for="transferLeague">League</label>
                <select class="form-control" id="transferLeague">
                    <option value="">Select a League</option>
                </select>
            </div>
            <div class="form-group">
                <label for="transferClub">Club</label>
                <select class="form-control" id="transferClub">
                    <option value="">Select a League first</option>
                </select>
            </div>

            <!-- Transfer Details -->
            <h4>Transfer Details</h4>
            <div class="form-group">
                <label for="fees">Transfer Fees</label>
                <input type="number" class="form-control" id="fees" name="fees" placeholder="Enter transfer fees" required>
            </div>
            <div class="form-group">
                <label for="clause">Transfer Clauses</label>
                <textarea class="form-control" id="clause" name="clause" placeholder="Enter transfer clauses"></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Submit Transfer</button>
        </form>
    </div>

    <!-- Success Modal -->
    <div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="successModalLabel">Transfer Successful</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="modalBody">
                    <!-- Success message content will be added here by JavaScript -->
                </div>
                <div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="location.reload()">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function() {
            // Populate leagues for both player and transfer club sections
            function loadLeagues(selectId) {
                $.ajax({
                    type: 'GET',
                    url: '/league',
                    success: function(leagues) {
                        leagues.forEach(function(league) {
                            $(selectId).append(new Option(league.leagueName, league.leagueId));
                        });
                    },
                    error: function() {
                        alert('Failed to load leagues. Please try again later.');
                    }
                });
            }

            loadLeagues('#playerLeague');
            loadLeagues('#transferLeague');

            function loadClubs(leagueId, selectId) {
                $.ajax({
                    type: 'GET',
                    url: '/Club?league_id=' + leagueId,
                    success: function(clubs) {
                        $(selectId).empty().append(new Option("Select a Club", ""));
                        clubs.forEach(function(club) {
                            $(selectId).append(new Option(club.name, club.clubId));
                        });
                    },
                    error: function() {
                        alert('Failed to load clubs. Please try again later.');
                    }
                });
            }

            function loadPlayers(clubId) {
                $.ajax({
                    type: 'GET',
                    url: '/Player-by-club?club_id=' + clubId,
                    success: function(players) {
                        $('#player').empty().append(new Option("Select a Player", ""));
                        players.forEach(function(player) {
                            $('#player').append(new Option(player.player_name, player.player_ID));
                        });
                    },
                    error: function() {
                        alert('Failed to load players. Please try again later.');
                    }
                });
            }

            $('#playerLeague').on('change', function() {
                loadClubs($(this).val(), '#playerClub');
            });

            $('#playerClub').on('change', function() {
                loadPlayers($(this).val());
            });

            $('#transferLeague').on('change', function() {
                loadClubs($(this).val(), '#transferClub');
            });

            $('#transferForm').on('submit', function(event) {
                event.preventDefault();

                const today = new Date();
                const formattedDate = today.getFullYear() + '-' + 
                                      String(today.getMonth() + 1).padStart(2, '0') + '-' + 
                                      String(today.getDate()).padStart(2, '0');

                const playerText = $('#player option:selected').text();
                const fromClubText = $('#playerClub option:selected').text();
                const toClubText = $('#transferClub option:selected').text();
                const fees = $('#fees').val();
				
                if ($('#playerClub').val() === $('#transferClub').val()) {
                    alert('Player can\'t be transferred to the same club');
                    return false; 
                }

                
                const transferData = {
                    Player_ID: $('#player').val(),
                    From_club_id: $('#playerClub').val(),
                    To_club_id: $('#transferClub').val(),
                    Transfer_fees: fees,
                    Clauses: $('#clause').val(),
                    Date: formattedDate
                };
				console.log(JSON.stringify(transferData));
                $.ajax({
                    type: 'POST',
                    url: '/Transfers-add',
                    contentType: 'application/json',
                    data: JSON.stringify(transferData),
                    success: function() {
                    	$('#modalBody').text(playerText + ' from ' + fromClubText + ' transferred to ' + toClubText + ' for ' + fees + '€ successfully.');
                        $('#successModal').modal('show');
                    },
                    error: function() {
                        alert('An error occurred during the transfer. Please try again later.');
                    }
                });
            });
        });
    </script>
</body>
</html>
