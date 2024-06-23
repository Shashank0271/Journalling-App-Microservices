package com.journalmicroservice.JournalMicroService.repository;

import com.journalmicroservice.JournalMicroService.entities.Image;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Id> {
}
