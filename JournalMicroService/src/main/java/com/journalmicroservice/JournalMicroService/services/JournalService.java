package com.journalmicroservice.JournalMicroService.services;

import com.journalmicroservice.JournalMicroService.entities.Image;
import com.journalmicroservice.JournalMicroService.entities.JournalEntry;
import com.journalmicroservice.JournalMicroService.repository.JournalRepository;
import com.journalmicroservice.JournalMicroService.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private ImageService imageService;

    public JournalEntry createEntry(List<MultipartFile> imageFiles, String userName, String content) {
        JournalEntry journalEntry = JournalEntry.builder().
                userName(userName)
                .content(content)
                .images(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        imageFiles.forEach(file -> {
            try {
                journalEntry.getImages().add(Image.builder().
                        image(ImageUtil.compress(file.getBytes())).
                        size(file.getSize()).
                        journalEntry(journalEntry).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return journalRepository.save(journalEntry);
    }


}
