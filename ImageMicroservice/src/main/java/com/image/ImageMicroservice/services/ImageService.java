package com.image.ImageMicroservice.services;

import com.image.ImageMicroservice.entities.Image;
import com.image.ImageMicroservice.entities.ImageCat;
import com.image.ImageMicroservice.repository.ImageRepository;
import com.image.ImageMicroservice.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> createJournalEntryImages(List<MultipartFile> images,
                                                long journalEntryId
    ) {
        List<Image> storedImages = new ArrayList<>();
        images.forEach(imageFile -> {
            //compress each image and get the image[] , then store it with appropriate category
            try {
                byte[] compressedImage = ImageUtil.compressImage(imageFile.getBytes());
                Image image = Image.builder()
                        .imageCategory(ImageCat.JOURNAL_ENTRY)
                        .image(compressedImage)
                        .sizeInBytes(compressedImage.length)
                        .journalEntryId(journalEntryId)
                        .build();
                imageRepository.save(image);
                storedImages.add(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return storedImages;
    }

    public Image createUserProfileImage(MultipartFile userProfileImage, long userId) {
        try {
            byte[] compressedImage = ImageUtil.compressImage(userProfileImage.getBytes());
            Image image = Image.builder()
                    .imageCategory(ImageCat.PROFILE_PIC)
                    .userId(userId)
                    .image(compressedImage)
                    .sizeInBytes(compressedImage.length)
                    .build();
            imageRepository.save(image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<Image> getAllImagesForJournalEntry(long journalEntryId) {
        List<Image> images = imageRepository.findByJournalEntryId(journalEntryId);
        images.forEach(image -> {
            try {
                byte[] uncompressedImage = ImageUtil.unCompressImage(image.getImage());
                image.setImage(uncompressedImage);
                image.setSizeInBytes(uncompressedImage.length);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
        });
        return images;
    }

    @Transactional
    public Image getUserProfilePicture(long userId) {
        //TODO :handle the case when the user doesn't have a profile pic
        Image userProfilePic = imageRepository.findByUserId(userId);
        try {
            byte[] uncompressedImage = ImageUtil.unCompressImage(userProfilePic.getImage());
            userProfilePic.setImage(uncompressedImage);
            userProfilePic.setSizeInBytes(uncompressedImage.length);
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        }
        return userProfilePic;
    }

}
