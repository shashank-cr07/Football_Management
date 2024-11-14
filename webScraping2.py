import mysql.connector
import requests
from selenium import webdriver
from bs4 import BeautifulSoup
import datetime
import re
import json
import html
import time
from html import unescape
import undetected_chromedriver as uc
import random

database = mysql.connector.connect(
    host="localhost",
    user="root",
    password="shambo",
    database="football_management"  # Update with your database name
)
cursor = database.cursor(buffered=True)

class Standing:
    def __init__(self,leagueId,clubId,pos,wins,losses,draws,scored,conceded, difference,points):
        self.leagueId = leagueId
        self.clubId = clubId
        self.pos = pos
        self.wins = wins
        self.losses = losses
        self.draws = draws
        self.scored = scored
        self.conceded = conceded
        self.difference = difference
        self.points = points


# Define Schedule class
class Schedule:
    def __init__(self, home_team, away_team, match_date):
        self.home_team = home_team
        self.away_team = away_team
        self.match_date = match_date

# ----------------------------------------------------------Functions---------------------------------------


# Insert match details into MySQL function
# Insert match details into MySQL function
def insert_into_matches(match_event,score):
    # Retrieve the home team ID
    cursor.execute(
        "SELECT Club_ID,League_ID FROM club WHERE Name = %s", (match_event.home_team,)
    )

    home_result = cursor.fetchone()
    if home_result:
        home_id = home_result[0]
    else:
        print(f"Home team '{match_event.home_team}' not found in the database.")
        return
    
    # Retrieve the away team ID
    cursor.execute(
        "SELECT Club_ID,League_ID FROM club WHERE Name = %s", (match_event.away_team,)
    )
    away_result = cursor.fetchone()
    if away_result:
        away_id = away_result[0]
    else:
        print(f"Away team '{match_event.away_team}' not found in the database.")
        return
    league_id = away_result[1]
    
    print(home_id,away_id,league_id)
    # Insert match record
    if (score == None):
            cursor.execute(
        "INSERT INTO matches (Home_club_ID, Away_club_ID,League_ID,Date, Time, Goals_scored, Goals_conceded) VALUES (%s, %s,%s,%s,%s,%s,%s)",
        (home_id, away_id, league_id,match_event.match_date,match_event.match_date[11:],None,None)
            )
    else:
        cursor.execute(
            "INSERT INTO matches (Home_club_ID, Away_club_ID,League_ID,Date, Time, Goals_scored, Goals_conceded) VALUES (%s, %s,%s,%s,%s,%s,%s)",
            (home_id, away_id, league_id,match_event.match_date,match_event.match_date[11:],match_score[0],match_score[2])
        )
    database.commit()
    print("Match inserted successfully.")

# Insert club names into MySQL
def insertIntoClubs(clubName):
    cursor.execute(
        "SELECT standings_name FROM name_conversion WHERE %s = club_matches_name",(clubName,)
    )
    clubName = cursor.fetchone()[0]
    try:
        cursor.execute(
            "INSERT INTO point (Name,League_ID) VALUES (%s,%s)",
            (clubName,1)
        )
        database.commit()

    except mysql.connector.Error as err:
        print(f"Error inserting club {clubName}: {err}")

def createClubStanding(clubName,pos,points,id):     #make the class object of the standing of one single club
    cursor.execute(
        "SELECT FootyStats_naming from name_conversion WHERE club_matches_name = %s",(clubName,)                #get the footystats name of the club
    )

    convertedName = cursor.fetchone()[0]

    cursor.execute(
        "SELECT Club_ID,League_ID from club where Name = %s",(clubName,)
    )
    (leagueID,clubStandingID) = cursor.fetchone()

    tables = funcSoup.find_all("table",class_ = "full-league-table table-sort col-sm-12 mobify-table")
    #print(tables)
    for table in tables:
        rows = table.select("tr[class]")
        #print(rows)
        for row in rows:
            footyName = row.find_all("a", class_ = "bold hover-modal-parent hover-modal-ajax-team")
            #print(footyName)
            for name in footyName:
                #print(name)
                if name.text.strip() == convertedName:
                    wins = row.find("td", class_ = "win").text.strip()
                    draws = row.find("td",class_ = "draw").text.strip()
                    loss = row.find("td",class_ = "loss").text.strip()
                    goalScored = row.find("td",class_ = "gf").text.strip()
                    goalConceded = row.find("td",class_ = "ga").text.strip()
                    goalDiff = row.find("td",class_ = "gd").text.strip()
                    print(leagueID,clubStandingID,pos,wins,loss,draws,goalScored,goalConceded,goalDiff,points)
                    break
                
        break
    #create class object
    
    clubStanding = Standing(leagueID,clubStandingID,pos,wins,loss,draws,goalScored,goalConceded,goalDiff,points)
    return clubStanding

def insertIntoPoints(clubStanding):

    cursor.execute(
        "INSERT INTO points (points_id, League_id, club_id,position, wins, losses, draws, goals_scored, goals_conceded) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s)",
        (id,clubStanding.clubId,clubStanding.leagueId,clubStanding.pos,clubStanding.wins,clubStanding.losses,clubStanding.draws,clubStanding.scored,clubStanding.conceded)
    )
    database.commit()

def insert_into_match_events(teams,score,row):
    # Fetch the page content for past matches
    #r = requests.get('https://native-stats.org/competition/PD/')
    #soup = BeautifulSoup(r.content, 'html.parser')

    # past_matches_section = soup.find("tbody", id="last_matches")
    # if past_matches_section:
    #     past_matches_rows = past_matches_section.find_all("tr")

    #for row in past_matches_rows:

        # Check if teams and score exist
    if len(teams) == 2 and score:
        home_team = teams[0]
        away_team = teams[1]
        match_score = score.text.strip()

    # Extract the match link from the `phx-click` attribute
        match_link_element = row.find("span", class_="hover:cursor-pointer")
        # print(match_link_element)
        if match_link_element and "phx-click" in match_link_element.attrs:
            phx_click_data = match_link_element.get('phx-click')
            try:
                # Parse JSON-like structure in `phx-click`
                data = json.loads(phx_click_data.replace('&quot;',""))
                match_url = data[0][1]['href']
                #print(match_url)

            except (json.JSONDecodeError, IndexError, KeyError):
                match_url = None
                print("Error in finding the url")
                
            if match_url:
                # Visit the match-specific page to extract event details
                match_page = requests.get(f"https://native-stats.org{match_url}")
                match_soup = BeautifulSoup(match_page.content, 'html.parser')

                tables = match_soup.find_all("table", class_ = "w-full")
                booking = tables[1]
                rows = booking.find_all("tr",class_ = "text-gray-300")
                for row in rows:
                    cells = row.find_all("td",class_ = "py-2 whitespace-nowrap text-sm text-center pr-2 font-medium")
                    spanRequiring = row.find_all("td", class_ = "py-2 whitespace-nowrap text-sm text-center font-medium")
                    count = 0
                    eventOffense = ""
                    eventName = ""
                    for cell in spanRequiring:
                        checkName = cell.find("span")
                        # getting the name of the player
                        # print(len(checkName))
                        if (checkName and count == 0):
                            eventName = checkName.text.strip()
                        
                        elif (checkName and count == 1):
                            eventOffense = checkName.text.strip()
                            if (eventOffense == "📒" ):
                                eventOffense = "Yellow Card"
                            elif (eventOffense == "📕"):
                                eventOffense = "Red Card"
                        count += 1

                        eventTime = cells[0].text.strip()[:-1]
                        # eventPlayer = cells[1].text.strip()            
                    print(eventOffense,eventName,eventTime)
                    cursor.execute(
                        "SELECT Player_ID from player where player_name = %s",(eventName,)
                    )
                    eventPlayerID = cursor.fetchone()[0]

                    cursor.execute(
                        "SELECT Match_ID FROM matches ORDER BY Match_ID DESC LIMIT 1"
                    )
                    matchID = cursor.fetchone()[0]
                    # Insert event into database (customize to match your table schema)
                    cursor.execute(
                        "INSERT INTO match_events (Match_ID, Player_ID, Min_Occured, description) VALUES (%s, %s, %s, %s)",
                        (matchID, eventPlayerID, eventTime, eventOffense)
                    )

                    database.commit()
                    print(f"Event '{eventOffense}' for player '{eventName}' at minute '{eventTime}' added successfully.")
        else:
            print(f"No link found for match: {home_team} vs {away_team}")


def insertIntoPlayer(name,id):
    cursor.execute(
        "SELECT teamLink FROM Name_conversion WHERE club_matches_name = %s",(name,)
    )
    clubNumber = cursor.fetchone()[0]
    url = f"https://native-stats.org/team/{clubNumber}"
    club_page = requests.get(url)
    club_connect = BeautifulSoup(club_page.content,'html.parser')
    # find the players from the page of the club
    #print(club_page.content)
    tables = club_connect.find_all("table", class_ = "table table-xs")
    for table in tables:
        rows = table.find_all("tr", class_ = "hover:bg-blue-300 hover:cursor-pointer")
        for row in rows:
            cells = row.find_all("td", class_ = "whitespace-nowrap px-1 py-.5 text-sm text-gray-200")
            playerName = cells[0].text.strip()
            position = cells[1].text.strip()
            cursor.execute(
                "INSERT INTO player (Player_name, Position, Current_Club_ID, Player_Valuation) VALUES (%s,%s,%s,%s)",
                (playerName, position, id,round(random.randrange(5000000,10000000),2))
            )
            database.commit()

def getPlayerValuation(name,end_url):
    player_page = requests.get(f"https://native-stats.org{end_url}")
    player_soup = BeautifulSoup(player_page.content, 'html.parser')

    toptable = player_soup.find_all("div",class_ = "mt-6 border-t border-gray-100")
    for row in toptable:
        element = row.find_all("dd",class_ = "mt-1 text-sm leading-6 text-gray-300 sm:col-span-2 sm:mt-0")
        # print(element)
        value = element[5]
        value = value.text.strip()[:-7]
        print(value)
        if (value == "n/a"):
            return None
        else:
            return float(value)*1000000

def insertIntoMatchAppearances(date,teams,score,row):
    if len(teams) == 2 and score:
        home_team = teams[0]
        away_team = teams[1]
        match_score = score.text.strip()

    # Extract the match link from the `phx-click` attribute
        match_link_element = row.find("span", class_="hover:cursor-pointer")
        # print(match_link_element)
        if match_link_element and "phx-click" in match_link_element.attrs:
            phx_click_data = match_link_element.get('phx-click')
            try:
                # Parse JSON-like structure in `phx-click`
                data = json.loads(phx_click_data.replace('&quot;',""))
                match_url = data[0][1]['href']
                #print(match_url)

            except (json.JSONDecodeError, IndexError, KeyError):
                match_url = None
                print("Error in finding the url")

            if match_url:  #home club url
                # Visit the match-specific page to extract event details
                match_page = requests.get(f"https://native-stats.org{match_url}")
                match_soup = BeautifulSoup(match_page.content, 'html.parser')

                tables = match_soup.find_all("div", class_ = "space-y-1")
                #print(tables[2])
                home_lineup = tables[2]     #finding all the home team players
                #print(home_lineup)
                home_bench = tables[3]
                away_lineup = tables[4]
                away_bench = tables[5]  
                home_rows = home_lineup.find_all("div",class_ = "text-sm text-gray-300 hover:text-white cursor-pointer")                
                away_rows = away_lineup.find_all("div",class_ = "text-sm text-gray-300 hover:text-white cursor-pointer")
                #print("Home lineup is:",rows)
                for row in home_rows:
                    #print(row)
                    if ("phx-click" in row.attrs):
                        home_phx_link = row.get('phx-click')
                        try:
                            homeData = json.loads(home_phx_link.replace('&quot;',""))
                            player_url = homeData[0][1]['href']
                        except (json.JSONDecodeError, IndexError, KeyError):
                            player_url = None
                            print("Error in finding the player url")
                    
                    cursor.execute(
                        "SELECT Match_ID FROM matches ORDER BY Match_ID DESC LIMIT 1"
                    )
                    matchID = cursor.fetchone()[0]
                    playerName = row.text.strip()
                    cleaned_player_name = re.sub(r"\s\(\d+\)", "", playerName)
                    value = getPlayerValuation(cleaned_player_name,player_url)    
                    
                    print(cleaned_player_name)
                    cursor.execute(
                        "SELECT Player_ID,position from player WHERE Player_Name = %s",(cleaned_player_name,)
                    )    
                    (id,pos) = cursor.fetchone()
                    print("Player ID is:",id,"Player position is:",pos)
                    
                    cursor.execute(
                        "UPDATE player SET Player_Valuation = %s where player_ID = %s",(value,id)
                    )
                    cursor.execute(
                        "INSERT INTO match_appearance (Date, Match_ID, Player_ID, Position) VALUES (%s,%s,%s,%s)",
                        (date,matchID,id,pos)
                    )
                    database.commit()
                
                for row in away_rows:
                    if ("phx-click" in row.attrs):
                        away_phx_link = row.get('phx-click')
                        try:
                            awayData = json.loads(away_phx_link.replace('&quot;',""))
                            player_url = awayData[0][1]['href']
                        except (json.JSONDecodeError, IndexError, KeyError):
                            player_url = None
                            print("Error in finding the player url")
                    
                    cursor.execute(
                        "SELECT Match_ID FROM matches ORDER BY Match_ID DESC LIMIT 1"
                    )
                    matchID = cursor.fetchone()[0]
                    playerName = row.text.strip()
                    cleaned_player_name = re.sub(r"\s\(\d+\)", "", playerName)
                    print(cleaned_player_name)
                    value = getPlayerValuation(cleaned_player_name,player_url)
                    cursor.execute(
                        "SELECT Player_ID,position from player WHERE Player_Name = %s",(cleaned_player_name,)
                    )    
                    (id,pos) = cursor.fetchone()
                    print("Player ID is:",id,"Player position is:",pos)
                    cursor.execute(
                        "UPDATE player SET Player_Valuation = %s where player_ID = %s",(value,id)
                    )

                    cursor.execute(
                        "INSERT INTO match_appearance (Date, Match_ID, Player_ID, Position) VALUES ( %s,%s,%s,%s)",
                        (date,matchID,id,pos)
                    ) 
                    database.commit()



def convertStandings(name):                     #converts the name from the standing table into the universal format fo the matches
    cursor.execute(
        "SELECT club_matches_name FROM name_conversion WHERE Standings_name = %s",(name,)
        ) 
    club_name = cursor.fetchone()
    return club_name[0]
    

#def insertIntoPoints(clubStanding):
#------------------------------------------------------------------ Main-------------------------------------------------------------

# Fetch HTML content from the website
r = requests.get('https://native-stats.org/competition/PD/')
soup = BeautifulSoup(r.content, 'html.parser')
try:
    driver = uc.Chrome()  # or webdriver.Firefox(), depending on the browser you're using

    # Open the website
    driver.get("https://footystats.org/spain/la-liga")


    time.sleep(3)

    # Get the page source after JavaScript has rendered
    page_html = driver.page_source

    # Parse with BeautifulSoup
    funcSoup = BeautifulSoup(page_html, 'html.parser')

finally:
    if driver is not None:
        driver.quit()
    else:
        pass

id = 0
tables = soup.find_all("table", class_="table table-xs")  # gets all tables; third is the standings table
if len(tables) > 2:
    standings = tables[2]
    table_body = standings.find_all("tr")
    for rows in table_body:    
        cell = rows.find_all("td")
        # print(cell) 
        if (cell):
            pos = cell[0].text.strip()[:-1]
            
            name = cell[1].find_all("span",class_ = "hidden align-middle md:inline-block sm:inline-block")
            
            club_name = convertStandings(name[0].text.strip())
            
            points = cell[3].text.strip()
            print(club_name,pos,points)

            cursor.execute(
                "SELECT Club_ID FROM club WHERE Name = %s",
                (club_name,)
            )

            club_id = cursor.fetchone()[0]
            id = id + 1
            clubStanding = createClubStanding(club_name,pos,points,id)
            insertIntoPoints(clubStanding)
            insertIntoPlayer(club_name,club_id)


else:
    print("No standings table found.")


# Extract past matches
print("Past Matches:")
past_matches_section = soup.find("tbody", id="last_matches")
if past_matches_section:
    past_matches_rows = past_matches_section.find_all("tr")
    for row in past_matches_rows:
        past_date = row.find("th").text.strip() if row.find("th") else "Date not available"
        teams = row.find_all("span", class_="hidden text-gray-200 align-middle md:inline-block")
        score = row.find("div",class_ = "inline-flex items-baseline rounded-full px-2 py-0.5 text-xs font-normal bg-purple-900 text-white md:mt-2 lg:mt-0")
        if (score):
            match_score = score.text.strip()
        
        else:
            match_score = "0:0"    #default if value unavailable
        
        if len(teams) == 2:
            past_home_team = teams[0].text.strip()
            past_away_team = teams[1].text.strip()

            cleaned_home_name = re.sub(r'\s*\(.*?\)','',past_home_team)
            cleaned_away_name = re.sub(r'\s*\(.*?\)','',past_away_team)

            pattern = r"(\d{4})/(\d{2})/(\d{2}), (\d{2})h(\d{2})"
        match = re.match(pattern,past_date)

        if match:
            # Extract the components
            year, month, day, hour, minute = match.groups()
    
    # Format into MySQL datetime format
            formatted_date = f"{year}-{month}-{day} {hour}:{minute}:00"

            print(f"{past_home_team} vs {past_away_team} on {past_date}")
            past_match = Schedule(cleaned_home_name, cleaned_away_name,formatted_date) 
            # Uncomment to enable database insertion
            # insertIntoClubs(past_home_team)
            # insertIntoClubs(past_away_team)
            insert_into_matches(past_match,match_score)
            print("Match score is:",match_score)
            insert_into_match_events([past_home_team,past_away_team],score,row)
            insertIntoMatchAppearances(formatted_date,[past_home_team,past_away_team],score,row)
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
        pattern = r"(\d{4})/(\d{2})/(\d{2}), (\d{2})h(\d{2})"
        match = re.match(pattern,next_date)

        if match:
            # Extract the components
            year, month, day, hour, minute = match.groups()
    
    # Format into MySQL datetime format
            formatted_date = f"{year}-{month}-{day} {hour}:{minute}:00"
            print(f"{next_home_team} vs {next_away_team} on {formatted_date}")
            next_match = Schedule(next_home_team, next_away_team, formatted_date)
            # Uncomment to enable database insertion
            insert_into_matches(next_match,None)
else:
    print("No upcoming matches found.")

# Extract standings from the website
# Close cursor and database connection after all operations

cursor.close()
database.close()
