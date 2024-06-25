package com.journalmicroservice.JournalMicroService.services;

import com.journalmicroservice.JournalMicroService.Exceptions.JournalEntryNotFoundException;
import com.journalmicroservice.JournalMicroService.entities.JournalEntry;
import com.journalmicroservice.JournalMicroService.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalService {
    final static Logger logger = LoggerFactory.getLogger(JournalService.class);
    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public JournalEntry createEntry(List<MultipartFile> imageFiles, String userName, int userId, String content) {
        String journalEntryId = UUID.randomUUID()
                .toString();

        JournalEntry journalEntry = JournalEntry.builder().
                entryId(journalEntryId).
                userName(userName)
                .userId(userId)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        if (imageFiles != null && !imageFiles.isEmpty()) {
            String url = "http://localhost:9000/image/journal";
            //forming the body
            MultiValueMap<String, Object> body
                    = new LinkedMultiValueMap<>();

            body.add("journalId", journalEntryId);

            imageFiles.forEach(imageFile -> {
                try {
                    body.add("files", new FileSystemResource(convertMultipartFileToFile(imageFile)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            //set the headers
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            //http entity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
            //make the request
            List response = restTemplate.postForObject(url, requestEntity, List.class);
            
            logger.debug("Images saved after saving journal entry : {}", response.size());
            journalEntry.setImages(response);
        } else {
            journalEntry.setImages(new ArrayList<>());
        }
        journalRepository.save(journalEntry);
        return journalEntry;
    }

    public JournalEntry getEntry(String journalEntryId) {
        Optional<JournalEntry> journalEntry = journalRepository.findByEntryId(journalEntryId);
        if (journalEntry.isEmpty()) {
            throw new JournalEntryNotFoundException(journalEntryId);
        }
        String url = "http://localhost:9000/image/journal/" + journalEntryId;
        List images = restTemplate.getForObject(url, List.class);
        journalEntry.get()
                .setImages(images);
        return journalEntry.get();
    }

    @Transactional
    public List<JournalEntry> getEntriesByUserId(int userId) {
        return journalRepository.findByUserId(userId);
    }

}
