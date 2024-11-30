package com.example.demo.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Points;


@Repository
public interface Points_Repo extends JpaRepository<Points, Integer> {
	@Query(value = "CALL GetPointsByLeague(:leagueId)", nativeQuery = true)
    List<Points> findPointsByLeague(@Param("leagueId") int leagueId);
}