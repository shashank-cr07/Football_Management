package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.League;


@Repository
public interface League_Repo extends JpaRepository<League, Integer> {
	@Query("SELECT l.leagueName FROM League l WHERE l.leagueId = :id")
    String findLeagueNameById(@Param("id") int id);
}