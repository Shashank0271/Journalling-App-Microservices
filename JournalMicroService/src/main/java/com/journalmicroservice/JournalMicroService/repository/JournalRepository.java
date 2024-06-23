package com.journalmicroservice.JournalMicroService.repository;

import com.journalmicroservice.JournalMicroService.entities.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<JournalEntry, Integer> {
}
