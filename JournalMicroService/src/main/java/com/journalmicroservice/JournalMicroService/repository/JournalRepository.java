package com.journalmicroservice.JournalMicroService.repository;

import com.journalmicroservice.JournalMicroService.entities.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntry, Integer> {
    List<JournalEntry> findByUserId(int userId);
}
