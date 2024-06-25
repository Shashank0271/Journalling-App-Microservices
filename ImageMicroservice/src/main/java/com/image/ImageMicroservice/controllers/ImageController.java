package com.image.ImageMicroservice.controllers;

import com.image.ImageMicroservice.entities.Image;
import com.image.ImageMicroservice.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/image")
@RestController
public class ImageController {
    final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @PostMapping("/journal")
    public ResponseEntity<List<Image>> createJournalEntryImages(@RequestPart("files") List<MultipartFile> images,
                                                                @RequestParam("journalId") String journalEntryId
    ) {
        logger.debug("JournalEntryId received in createJournalEntryImages controller : {}", journalEntryId);
        return new ResponseEntity<>(imageService.createJournalEntryImages(images, journalEntryId), HttpStatus.CREATED);
    }

    @GetMapping("/journal/{journalId}")
    public ResponseEntity<List<Image>> getImagesForJournalEntry(@PathVariable String journalId) {
        logger.debug("entered getImagesForJournalEntry controller");
        return new ResponseEntity<>(imageService.getAllImagesForJournalEntry(journalId), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Image> createUserProfileImage(@RequestParam("file") MultipartFile userProfilePic, @Param("userId") long userId) {
        return new ResponseEntity<>(imageService.createUserProfileImage(userProfilePic, userId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Image> getUsersProfileImage(@PathVariable long userId) {
        return new ResponseEntity<>(imageService.getUserProfilePicture(userId), HttpStatus.OK);
    }

}
