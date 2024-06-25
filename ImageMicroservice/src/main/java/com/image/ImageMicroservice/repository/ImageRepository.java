package com.image.ImageMicroservice.repository;

import com.image.ImageMicroservice.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByJournalEntryId(long journalEntryId);

    Image findByUserId(long userId);
}
