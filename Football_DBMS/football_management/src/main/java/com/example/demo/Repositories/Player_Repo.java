package com.example.demo.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Player;


@Repository
public interface Player_Repo extends JpaRepository<Player, Integer> {
	
	@Query("SELECT p FROM Player p WHERE p.Current_Club.clubId = :clubId")
    List<Player> findByClubId(@Param("clubId") Integer clubId);
}