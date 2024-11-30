package com.example.demo.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Match;


@Repository
public interface Match_Repo extends JpaRepository<Match, Integer> {
	 @Query(value = "CALL GetLatestMatchesByClub(:clubId)", nativeQuery = true)
	    List<Match> findLatestMatchesByClub(@Param("clubId") int clubId);
	 
	 @Query(value="call GetMatchEvents(:matchId)",nativeQuery=true)
	 List<Object[]> getAllMatchEvents(@Param("matchId")int matchId);
	 
	 @Query(value = "CALL getFutureClubMatches(:clubId)", nativeQuery = true)
	    List<Match> getNextMatchesByClub(@Param("clubId") int clubId);
	 @Query(value = "SELECT * FROM matches m WHERE m.goals_scored IS NOT NULL ", nativeQuery = true)
	    List<Match> findLatestPresentMatchesByClub();
}