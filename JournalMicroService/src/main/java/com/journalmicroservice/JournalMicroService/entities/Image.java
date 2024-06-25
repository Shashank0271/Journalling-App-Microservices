package com.journalmicroservice.JournalMicroService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum ImageCat {
    PROFILE_PIC, JOURNAL_ENTRY
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private long imageId;
    private long sizeInBytes;
    private byte[] image;
    private ImageCat imageCategory;
    private Long userId;
    private Long journalEntryId;
}