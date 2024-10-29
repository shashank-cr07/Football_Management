package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "player", schema = "football_management")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_ID")
    private Integer player_ID;
    
    @Column(name = "Player_name")
    private String Player_name;

    @Column(name = "Position")
    private String Position;

    @Column(name = "Player_Valuation")
    private Integer Player_Valuation;

    @ManyToOne
    @JoinColumn(name = "Current_Club_ID")
    private Club Current_Club;

    public Player() {}

    
    
	public Player(String player_name, String position, Integer player_Valuation, Club current_Club) {
		super();
		Player_name = player_name;
		Position = position;
		Player_Valuation = player_Valuation;
		Current_Club = current_Club;
	}



	public Integer getPlayer_ID() {
		return player_ID;
	}

	

	public String getPlayer_name() {
		return Player_name;
	}

	public void setPlayer_name(String player_name) {
		Player_name = player_name;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public Integer getPlayer_Valuation() {
		return Player_Valuation;
	}

	public void setPlayer_Valuation(Integer player_Valuation) {
		Player_Valuation = player_Valuation;
	}

	public Club getCurrent_Club() {
		return Current_Club;
	}

	public void setCurrent_Club(Club current_Club) {
		Current_Club = current_Club;
	}
    
    
}
