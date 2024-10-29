package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.MatchEvents;


@Repository
public interface MatchEvents_Repo extends JpaRepository<MatchEvents, Integer> {
}