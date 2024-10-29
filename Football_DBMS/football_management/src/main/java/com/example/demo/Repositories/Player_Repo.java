package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Player;


@Repository
public interface Player_Repo extends JpaRepository<Player, Integer> {
}