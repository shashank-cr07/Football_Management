package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "club", schema = "football_management")
public class Club {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Club_ID")
	private Integer Club_ID;
	@Column(name = "Name")
	private String Club_Name;
	
	@Column(name = "Market_value")
	private Integer Market_value;
	@Column(name = "League")
	private String League;
	@Column(name = "Available_Balance")
	private Integer Available_Balance;
	@Column(name = "Coach")
	private String Coach;
	public Club() {}
	
	
	public Club(String club_Name, Integer market_value, String league, Integer available_Balance,
			String coach) {
		super();
		Club_Name = club_Name;
		Market_value = market_value;
		League = league;
		Available_Balance = available_Balance;
		Coach = coach;
	}


	public Integer getClub_ID() {
		return Club_ID;
	}
	
	public String getClub_Name() {
		return Club_Name;
	}
	public void setClub_Name(String club_Name) {
		Club_Name = club_Name;
	}
	public Integer getMarket_value() {
		return Market_value;
	}
	public void setMarket_value(Integer market_value) {
		Market_value = market_value;
	}
	public String getLeague() {
		return League;
	}
	public void setLeague(String league) {
		League = league;
	}
	public Integer getAvailable_Balance() {
		return Available_Balance;
	}
	public void setAvailable_Balance(Integer available_Balance) {
		Available_Balance = available_Balance;
	}
	public String getCoach() {
		return Coach;
	}
	public void setCoach(String coach) {
		Coach = coach;
	}
	
	
}

