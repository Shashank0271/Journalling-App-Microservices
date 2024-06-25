package com.journalmicroservice.JournalMicroService.services;

import com.journalmicroservice.JournalMicroService.Exceptions.EntryNotFoundException;
import com.journalmicroservice.JournalMicroService.entities.JournalEntry;
import com.journalmicroservice.JournalMicroService.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;

    public JournalEntry createEntry(List<MultipartFile> imageFiles, String userName, int userId, String content) {
        JournalEntry journalEntry = JournalEntry.builder().
                userName(userName)
                .userId(userId)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        //calling the imageControllers createImages end-point


        return journalRepository.save(journalEntry);
    }

    public JournalEntry getEntry(int journalEntryId) {
        Optional<JournalEntry> journalEntry = journalRepository.findById(journalEntryId);
        if (journalEntry.isEmpty()) {
            throw new EntryNotFoundException(journalEntryId);
        }
        return journalEntry.get();
    }


    @Transactional
    public List<JournalEntry> getEntriesByUserId(int userId) {
        return journalRepository.findByUserId(userId);
    }
}
