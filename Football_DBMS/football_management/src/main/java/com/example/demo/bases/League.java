package com.example.demo.bases;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "league", schema = "football_management")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id")
    private Integer leagueId;

    @Column(name = "league_name", nullable = false)
    private String leagueName;

    @Column(name = "winning_price", nullable = false)
    private Double winningPrice;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "winning_club_id", referencedColumnName = "club_id")
    private Club winningClubId;

    @Column(name = "description")
    private String description;

    public League() {}

    public League(String leagueName, Double winningPrice, Integer year, Date startDate, Date endDate,
                  Club winningClubId, String description) {
        super();
        this.leagueName = leagueName;
        this.winningPrice = winningPrice;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.winningClubId = winningClubId;
        this.description = description;
    }

    // Getters and setters
    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public Double getWinningPrice() {
        return winningPrice;
    }

    public void setWinningPrice(Double winningPrice) {
        this.winningPrice = winningPrice;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Club getWinningClubId() {
        return winningClubId;
    }

    public void setWinningClubId(Club winningClubId) {
        this.winningClubId = winningClubId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
