package com.journalmicroservice.JournalMicroService.services;

import com.journalmicroservice.JournalMicroService.Exceptions.EntryNotFoundException;
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
import java.util.Optional;
import java.util.zip.DataFormatException;

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
                byte[] compressedImage = ImageUtil.compress(file.getBytes());
                journalEntry.getImages().add(Image.builder().
                        image(compressedImage).
                        sizeInBytes(compressedImage.length).
                        journalEntry(journalEntry).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return journalRepository.save(journalEntry);
    }

    public JournalEntry getEntry(int journalEntryId) {
        Optional<JournalEntry> journalEntry = journalRepository.findById(journalEntryId);
        if (journalEntry.isEmpty()) {
            throw new EntryNotFoundException(journalEntryId);
        }
        journalEntry.get().getImages().forEach(image -> {
            try {
                byte[] uncompressed = ImageUtil.uncompress(image.getImage());
                image.setImage(uncompressed);
                image.setSizeInBytes(uncompressed.length);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
        });
        return journalEntry.get();
    }


}
