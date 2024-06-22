package com.journalmicroservice.JournalMicroService.services;

import com.journalmicroservice.JournalMicroService.entities.JournalEntry;
import com.journalmicroservice.JournalMicroService.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;

    public JournalEntry createEntry(JournalEntry entry) {
        return journalRepository.save(entry);
    }
}
