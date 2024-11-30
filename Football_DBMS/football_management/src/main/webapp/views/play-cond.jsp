<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Player Conditions</title>
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
    <!-- Title and Club Info -->
    <h3 class="text-left">Player Conditions for Club:  ${clubName}</h3>

    <!-- Table displaying players -->
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Player Name</th>
                <th>Position</th>
                <th>Player Valuation</th>
                <th>Conditions</th>
            </tr>
        </thead>
        <tbody id="playerTableBody">
            <!-- Table rows will be populated by JavaScript -->
        </tbody>
    </table>
</div>

<!-- Modal for displaying player conditions -->
<div class="modal fade" id="conditionModal" tabindex="-1" role="dialog" aria-labelledby="conditionModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="conditionModalLabel">Player Conditions</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="modalBody">
                <!-- Modal content will be populated by JavaScript -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- Include Bootstrap JS for styling -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    $(document).ready(function() {
        const clubID = ${clubID}; // Ensure clubID is passed from the backend to the JSP

        if (clubID) {
            $.ajax({
                url: '/Player-by-club?club_id=' + clubID,
                type: 'GET',
                success: function(data) {
                    populatePlayerTable(data);
                },
                error: function() {
                    alert('Failed to fetch players for this club.');
                }
            });
        } else {
            alert('Club ID is missing.');
        }
    });

    // Populate the table with player data
    function populatePlayerTable(data) {
        if (!Array.isArray(data)) {
            console.error("Invalid data format. Expected an array.");
            return;
        }

        const targetTableBody = $('#playerTableBody');
        targetTableBody.empty();  // Clear any existing rows

        data.forEach(function(player) {
            const tableRow = $('<tr></tr>');

            const playerNameCell = $('<td></td>').text(player.player_name);
            const positionCell = $('<td></td>').text(player.position);
            const playerValuationCell = $('<td></td>').text(player.player_Valuation);
            const conditionCell = $('<td></td>').html('<button class="btn btn-info" data-toggle="modal" data-target="#conditionModal" onclick="showPlayerConditions(' + player.player_ID + ')">View Conditions</button>');

            tableRow.append(playerNameCell, positionCell, playerValuationCell, conditionCell);
            targetTableBody.append(tableRow);
        });
    }

    // Fetch and display player conditions in the modal
function showPlayerConditions(playerId) {
    console.log(playerId);
    $.ajax({
        url: '/PlayerConditions-by-player?player_id=' + playerId,
        type: 'GET',
        success: function(data) {
            let modalContent = '';

            if (data && data.length > 0) {
                const condition = data[0]; 
                const playerConditions = condition;

                modalContent += '<p><strong>Player:</strong> ' + (playerConditions.player.player_name || 'N/A') + '</p>';
                modalContent += '<p><strong>Injury:</strong> ' + (playerConditions.injury || 'N/A') + '</p>';
                modalContent += '<p><strong>Recent Form:</strong> ' + (playerConditions.recent_form || 'N/A') + '</p>';
                modalContent += '<p><strong>Description:</strong> ' + (playerConditions.description || 'N/A') + '</p>';
                modalContent += '<p><strong>Training Details:</strong> ' + (playerConditions.training_details || 'N/A') + '</p>';
            } else {
                modalContent = '<p>No conditions present for this player.</p>';
            }

            $('#modalBody').html(modalContent);
        },
        error: function() {
            $('#modalBody').html('<p>Error fetching player conditions. Please try again.</p>');
        }
    });
}

</script>
</body>
</html>
