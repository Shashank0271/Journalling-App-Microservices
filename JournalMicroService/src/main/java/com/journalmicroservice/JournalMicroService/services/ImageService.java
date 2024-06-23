package com.journalmicroservice.JournalMicroService.services;

import com.journalmicroservice.JournalMicroService.entities.Image;
import com.journalmicroservice.JournalMicroService.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
