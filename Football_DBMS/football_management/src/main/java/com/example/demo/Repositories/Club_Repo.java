package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Club;


@Repository
public interface Club_Repo extends JpaRepository<Club, Integer> {
}