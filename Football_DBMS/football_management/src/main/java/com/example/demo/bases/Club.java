package com.example.demo.bases;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "club", schema = "football_management")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Club_ID")
    private Integer clubId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Market_value")
    private BigDecimal marketValue;

    @ManyToOne
    @JoinColumn(name = "League_ID", referencedColumnName = "league_id", nullable = false)
    private League leagueId;

    @Column(name = "Available_Balance")
    private BigDecimal availableBalance;

    @Column(name = "Coach")
    private String coach;

    public Club() {}
	public Club(String name, BigDecimal marketValue, League leagueId, BigDecimal availableBalance, String coach) {
		super();
		this.name = name;
		this.marketValue = marketValue;
		this.leagueId = leagueId;
		this.availableBalance = availableBalance;
		this.coach = coach;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	public League getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(League leagueId) {
		this.leagueId = leagueId;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

    
}

