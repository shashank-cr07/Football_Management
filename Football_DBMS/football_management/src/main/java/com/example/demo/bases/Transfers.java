package com.example.demo.bases;
import jakarta.persistence.*;

@Entity
@Table(name = "transfers", schema = "football_management")
public class Transfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Transfer_ID")
    private Integer Transfer_ID;

    @ManyToOne
    @JoinColumn(name = "Player_ID")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "From_club_id")
    private Club fromClub;

    @ManyToOne
    @JoinColumn(name = "To_club_id")
    private Club toClub;

    @Column(name = "Transfer_fees")
    private Integer Transfer_fees;

    @Column(name = "Clauses")
    private String Clauses;

    @Column(name = "Date")
    private String Date;

    public Transfers() {}

    
	public Transfers(Player player, Club fromClub, Club toClub, Integer transfer_fees, String clauses, String date) {
		super();
		this.player = player;
		this.fromClub = fromClub;
		this.toClub = toClub;
		Transfer_fees = transfer_fees;
		Clauses = clauses;
		Date = date;
	}


	public Integer getTransfer_ID() {
		return Transfer_ID;
	}

	

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Club getFromClub() {
		return fromClub;
	}

	public void setFromClub(Club fromClub) {
		this.fromClub = fromClub;
	}

	public Club getToClub() {
		return toClub;
	}

	public void setToClub(Club toClub) {
		this.toClub = toClub;
	}

	public Integer getTransfer_fees() {
		return Transfer_fees;
	}

	public void setTransfer_fees(Integer transfer_fees) {
		Transfer_fees = transfer_fees;
	}

	public String getClauses() {
		return Clauses;
	}

	public void setClauses(String clauses) {
		Clauses = clauses;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

    
}
