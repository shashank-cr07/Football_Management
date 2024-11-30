package com.example.demo.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Club;
import com.example.demo.bases.League;


@Repository
public interface Club_Repo extends JpaRepository<Club, Integer> {
	List<Club> findByLeagueId(League leagueId);
	
	@Query("SELECT c.clubId FROM Club c WHERE c.name = :clubName")
	Integer findClubIdByName(@Param("clubName") String clubName);
	
	@Query("SELECT l.name FROM Club l WHERE l.clubId = :id")
    String findClubNameById(@Param("id") int id);

}