package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "matches", schema = "football_management")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Match_ID")
    private Integer Match_ID;

    @ManyToOne
    @JoinColumn(name = "Home_club_ID")
    private Club homeClub;

    @ManyToOne
    @JoinColumn(name = "Away_club_ID")
    private Club awayClub;

    @Column(name = "Goals_scored")
    private Integer Goals_scored;

    @Column(name = "Goals_conceded")
    private Integer Goals_conceded;

    @Column(name = "Date")
    private String Date;

    public Match() {}

    
    
	public Match(Club homeClub, Club awayClub, Integer goals_scored, Integer goals_conceded, String date) {
		super();
		this.homeClub = homeClub;
		this.awayClub = awayClub;
		Goals_scored = goals_scored;
		Goals_conceded = goals_conceded;
		Date = date;
	}



	public Integer getMatch_ID() {
		return Match_ID;
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

	public Integer getGoals_scored() {
		return Goals_scored;
	}

	public void setGoals_scored(Integer goals_scored) {
		Goals_scored = goals_scored;
	}

	public Integer getGoals_conceded() {
		return Goals_conceded;
	}

	public void setGoals_conceded(Integer goals_conceded) {
		Goals_conceded = goals_conceded;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

    
}
