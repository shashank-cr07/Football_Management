package com.example.demo.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bases.Transfers;


@Repository
public interface Transfers_Repo extends JpaRepository<Transfers, Integer> {
	@Query(value = "CALL GetTransfersByClub(:clubId)", nativeQuery = true)
    List<Transfers> findTransfersByClub(@Param("clubId") int clubId);
}