package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.MatchAppearance;

@Repository
public interface MatchAppearance_Repo extends JpaRepository<MatchAppearance, Integer> {
    @Query(value = "SELECT * FROM match_appearance ma WHERE ma.match_id = :match_id", nativeQuery = true)
    List<MatchAppearance> findMatchfromId(@Param("match_id") Integer match_id);
}
