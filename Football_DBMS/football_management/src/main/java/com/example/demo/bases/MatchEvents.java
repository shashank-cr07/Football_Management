package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "match_events", schema = "football_management")
public class MatchEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Event_ID")
    private Integer Event_ID;

    @ManyToOne
    @JoinColumn(name = "Match_ID")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "Player_ID")
    private Player player;

    @Column(name = "Min_occured")
    private Integer Min_occured;

    @Column(name = "Description")
    private String Description;

    public MatchEvents() {}

    
    
	public MatchEvents(Match match, Player player, Integer min_occured, String description) {
		super();
		this.match = match;
		this.player = player;
		Min_occured = min_occured;
		Description = description;
	}



	public Integer getEvent_ID() {
		return Event_ID;
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

	public Integer getMin_occured() {
		return Min_occured;
	}

	public void setMin_occured(Integer min_occured) {
		Min_occured = min_occured;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

    
}
