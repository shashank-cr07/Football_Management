package com.example.demo.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.PlayerConditions;


@Repository
public interface PlayerConditions_Repo extends JpaRepository<PlayerConditions, Integer> {
    @Query(value = "SELECT * FROM player_conditions pc WHERE pc.Player_ID = :playerId", nativeQuery = true)
    List<PlayerConditions> findByPlayerId(@Param("playerId") Integer playerId);
}