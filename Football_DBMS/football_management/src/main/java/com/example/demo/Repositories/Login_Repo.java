package com.example.demo.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Login;


@Repository
public interface Login_Repo extends JpaRepository<Login, Integer> {
	Optional<Login> findByUsernameAndPassword(String username, String password);
}