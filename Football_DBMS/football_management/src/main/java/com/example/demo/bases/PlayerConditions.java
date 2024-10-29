package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "player_conditions", schema = "football_management")
public class PlayerConditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Condition_ID")
    private Integer Condition_ID;

    @ManyToOne
    @JoinColumn(name = "Player_ID")
    private Player player;

    @Column(name = "Injury")
    private String Injury;

    @Column(name = "Recent_form")
    private String Recent_form;

    @Column(name = "Description")
    private String Description;

    @Column(name = "Training_details")
    private String Training_details;

    public PlayerConditions() {}

    
    
	public PlayerConditions(Player player, String injury, String recent_form, String description,
			String training_details) {
		super();
		this.player = player;
		Injury = injury;
		Recent_form = recent_form;
		Description = description;
		Training_details = training_details;
	}



	public Integer getCondition_ID() {
		return Condition_ID;
	}

	

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getInjury() {
		return Injury;
	}

	public void setInjury(String injury) {
		Injury = injury;
	}

	public String getRecent_form() {
		return Recent_form;
	}

	public void setRecent_form(String recent_form) {
		Recent_form = recent_form;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getTraining_details() {
		return Training_details;
	}

	public void setTraining_details(String training_details) {
		Training_details = training_details;
	}

    
}
