package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "match_appearance", schema = "football_management")
public class MatchAppearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "App_ID")
    private Integer App_ID;

    @Column(name = "Date")
    private String Date;

    @ManyToOne
    @JoinColumn(name = "Match_ID")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "Player_ID")
    private Player player;

    @Column(name = "Position")
    private String Position;

    @Column(name = "Type")
    private String Type;

    public MatchAppearance() {}

    
	public MatchAppearance(String date, Match match, Player player, String position, String type) {
		super();
		Date = date;
		this.match = match;
		this.player = player;
		Position = position;
		Type = type;
	}


	public Integer getApp_ID() {
		return App_ID;
	}

	

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

    
}