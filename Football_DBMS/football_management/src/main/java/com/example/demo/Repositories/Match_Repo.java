package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Match;


@Repository
public interface Match_Repo extends JpaRepository<Match, Integer> {
}