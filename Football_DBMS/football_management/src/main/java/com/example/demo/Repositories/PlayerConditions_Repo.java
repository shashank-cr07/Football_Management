package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.PlayerConditions;


@Repository
public interface PlayerConditions_Repo extends JpaRepository<PlayerConditions, Integer> {
}