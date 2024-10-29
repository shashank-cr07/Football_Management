package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.MatchAppearance;


@Repository
public interface MatchAppearance_Repo extends JpaRepository<MatchAppearance, Integer> {
}