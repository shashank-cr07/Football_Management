package com.example.demo.bases;

import jakarta.persistence.*;

@Entity
@Table(name = "matches", schema = "football_management")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Match_ID")
    private Integer matchId;

    @ManyToOne
    @JoinColumn(name = "Home_club_ID", nullable = false)
    private Club homeClub;

    @ManyToOne
    @JoinColumn(name = "Away_club_ID", nullable = false)
    private Club awayClub;

    @Column(name = "Goals_scored")
    private Integer goalsScored;

    @Column(name = "Goals_conceded")
    private Integer goalsConceded;

    @Column(name = "Date", nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn(name = "league_id", referencedColumnName = "league_id", nullable = false)
    private League league;

    @Column(name = "time")
    private String time;
    public Match() {}

    public Match(Club homeClub, Club awayClub, Integer goalsScored, Integer goalsConceded, String date, League league,String time) {
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.date = date;
        this.league = league;
        this.time=time;
    }
    

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Club getHomeClub() {
		return homeClub;
	}

	public void setHomeClub(Club homeClub) {
		this.homeClub = homeClub;
	}

	public Club getAwayClub() {
		return awayClub;
	}

	public void setAwayClub(Club awayClub) {
		this.awayClub = awayClub;
	}

	public Integer getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(Integer goalsScored) {
		this.goalsScored = goalsScored;
	}

	public Integer getGoalsConceded() {
		return goalsConceded;
	}

	public void setGoalsConceded(Integer goalsConceded) {
		this.goalsConceded = goalsConceded;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

   
}
