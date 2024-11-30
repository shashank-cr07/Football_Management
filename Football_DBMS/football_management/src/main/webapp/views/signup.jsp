<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
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
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">My Football App</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="Home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" data-toggle="modal" data-target="#loginModal">Login</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-5">
        <h1 class="text-center">Sign Up</h1>
        <form id="signupForm">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
            </div>
            <div class="form-group">
                <label for="league">Favourite League</label>
                <select class="form-control" id="league" name="league" required>
                    <option value="">Select a League</option>
                </select>
            </div>
            <div class="form-group">
                <label for="club">Favourite Club</label>
                <select class="form-control" id="club" name="club" required>
                    <option value="">Select a League first</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Sign Up</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function() {
            // Fetch leagues and populate the dropdown
            $.ajax({
                type: 'GET',
                url: '/league',
                success: function(leagues) {
                    leagues.forEach(function(league) {
                        $('#league').append(new Option(league.leagueName, league.leagueId));
                    });
                },
                error: function() {
                    alert('Failed to load leagues. Please try again later.');
                }
            });

         // Fetch clubs based on the selected league
           $('#league').on('change', function() {
    const selectedLeagueId = $(this).val();

    // Clear the club dropdown
    $('#club').empty().append(new Option("Select a Club", ""));

    // Only fetch clubs if a league is selected
    if (selectedLeagueId) {
        $.ajax({
            type: 'GET',
            url: '/Club?league_id=' + selectedLeagueId, // Ensure correct endpoint
            success: function(clubs) {
                if (clubs.length > 0) {
                    clubs.forEach(function(club) {
                        $('#club').append(new Option(club.name, club.clubId));
                        console.log(club.name+" "+club.clubId);
                    });
                } else {
                    $('#club').append(new Option("No clubs found", ""));
                }
            },
            error: function() {
                alert('Failed to load clubs. Please try again later.');
            }
        });
    }
});



            // Handle form submission
            $('#signupForm').on('submit', function(event) {
                event.preventDefault();

                // Prepare form data with role set to 'viewer'
                const formData = {
                    username: $('#username').val(),
                    password: $('#password').val(),
                    role: 'viewer', // Fixed role value
                    clubId: $('#club').val()
                };
				console.log(formData);
                // Submit the data as a JSON object to the /login endpoint
                $.ajax({
                    type: 'POST',
                    url: '/login',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    success: function(response) {
                        alert('Sign up successful! You can now log in.');
                        window.location.href = 'Home'; // Redirect to home or login page
                    },
                    error: function(xhr) {
                        if (xhr.status === 409) { // Assuming 409 Conflict if username already exists
                            alert('Username already exists. Please choose a different username.');
                        } else {
                            alert('An error occurred. Please try again later.');
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>
