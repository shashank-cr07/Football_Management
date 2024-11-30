package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "points", schema = "football_management")
public class Points {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "points_id")
    private Integer pointsId;
    
    @ManyToOne
    @JoinColumn(name = "league_id", referencedColumnName = "league_id")
    private League leagueId;

    @ManyToOne
    @JoinColumn(name = "club_id", referencedColumnName = "club_id")
    private Club clubId;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "wins", nullable = false, columnDefinition = "int default 0")
    private Integer wins = 0;

    @Column(name = "losses", nullable = false, columnDefinition = "int default 0")
    private Integer losses = 0;

    @Column(name = "draws", nullable = false, columnDefinition = "int default 0")
    private Integer draws = 0;

    @Column(name = "goals_scored", nullable = false, columnDefinition = "int default 0")
    private Integer goalsScored = 0;

    @Column(name = "goals_conceded", nullable = false, columnDefinition = "int default 0")
    private Integer goalsConceded = 0;

    @Column(name = "goal_difference")
    private Integer goalDifference;

    @Column(name = "points")
    private Integer points;

    
    public Points() {}
    
	public Points(League leagueId, Club clubId, Integer position, Integer wins, Integer losses, Integer draws,
			Integer goalsScored, Integer goalsConceded, Integer goalDifference, Integer points) {
		super();
		this.leagueId = leagueId;
		this.clubId = clubId;
		this.position = position;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.goalsScored = goalsScored;
		this.goalsConceded = goalsConceded;
		this.goalDifference = goalDifference;
		this.points = points;
	}

	public Integer getPointsId() {
		return pointsId;
	}

	public void setPointsId(Integer pointsId) {
		this.pointsId = pointsId;
	}

	public League getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(League leagueId) {
		this.leagueId = leagueId;
	}

	public Club getClubId() {
		return clubId;
	}

	public void setClubId(Club clubId) {
		this.clubId = clubId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public Integer getDraws() {
		return draws;
	}

	public void setDraws(Integer draws) {
		this.draws = draws;
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

	public Integer getGoalDifference() {
		return goalDifference;
	}

	public void setGoalDifference(Integer goalDifference) {
		this.goalDifference = goalDifference;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
    
    
    
    // Getters and setters
}
