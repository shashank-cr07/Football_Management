package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Transfers;


@Repository
public interface Transfers_Repo extends JpaRepository<Transfers, Integer> {
}