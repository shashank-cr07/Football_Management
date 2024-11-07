import mysql.connector
import requests
from bs4 import BeautifulSoup

database = mysql.connector.connect(
    host="localhost",
    user="root",
    password="shambo",
    database="football_management"  # Update with your database name
)
cursor = database.cursor()

# Define Schedule class
class Schedule:
    def __init__(self, home_team, away_team, match_date):
        self.home_team = home_team
        self.away_team = away_team
        self.match_date = match_date

# ----------------------------------------------------------Functions---------------------------------------


# Insert match details into MySQL function
def insert_into_match_appearances(match_event):
    cursor.execute(
        "INSERT INTO matches (home_team, away_team, match_date) VALUES (%s, %s, %s)",
        (match_event.home_team, match_event.away_team, match_event.match_date)
    )
    database.commit()

# Insert club names into MySQL
def insertIntoClubs(clubName):
    try:
        cursor.execute(
            "INSERT INTO club (Name,League_ID) VALUES (%s,%s)",
            (clubName,1)
        )
        database.commit()
    except mysql.connector.Error as err:
        print(f"Error inserting club {clubName}: {err}")


#------------------------------------------------------------------ Main-------------------------------------------------------------

# Fetch HTML content from the website
r = requests.get('https://native-stats.org/competition/PD/')
soup = BeautifulSoup(r.content, 'html.parser')

# Extract past matches
print("Past Matches:")
past_matches_section = soup.find("tbody", id="last_matches")
if past_matches_section:
    past_matches_rows = past_matches_section.find_all("tr")
    for row in past_matches_rows:
        past_date = row.find("th").text.strip() if row.find("th") else "Date not available"
        teams = row.find_all("span", class_="hidden text-gray-200 align-middle md:inline-block")
        if len(teams) == 2:
            past_home_team = teams[0].text.strip()
            past_away_team = teams[1].text.strip()
            print(f"{past_home_team} vs {past_away_team} on {past_date}")
            past_match = Schedule(past_home_team, past_away_team, past_date)
            # Uncomment to enable database insertion
            # insert_into_match_appearances(past_match)
else:
    print("No past matches found.")



# Extract upcoming matches
print("\nUpcoming Matches:")
next_matches_section = soup.find("tbody", id="next_matches")
if next_matches_section:
    next_matches_rows = next_matches_section.find_all("tr")
    for row in next_matches_rows:
        next_date = row.find("th").text.strip() if row.find("th") else "Date not available"
        teams = row.find_all("span", class_="hidden text-gray-200 align-middle md:inline-block")
        if len(teams) == 2:
            next_home_team = teams[0].text.strip()
            next_away_team = teams[1].text.strip()
            print(f"{next_home_team} vs {next_away_team} on {next_date}")
            next_match = Schedule(next_home_team, next_away_team, next_date)
            # Uncomment to enable database insertion
            # insert_into_match_appearances(next_match)
else:
    print("No upcoming matches found.")

# Extract standings from the website
tables = soup.find_all("table", class_="table table-xs")  # gets all tables; third is the standings table
if len(tables) > 2:
    standings = tables[2]
    clubs = standings.find_all("span", class_="hidden align-middle md:inline-block sm:inline-block")
    for club in clubs:
        club_name = club.text.strip()
        print(club_name)
        insertIntoClubs(club_name)
else:
    print("No standings table found.")

# Close cursor and database connection after all operations
cursor.close()
database.close()
