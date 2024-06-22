package com.journalmicroservice.JournalMicroService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @ManyToOne
    @JoinColumn(name = "entry_id")
    JournalEntry journalEntry;
    @Id
    @GeneratedValue(generator = "image_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "image_seq_gen", sequenceName = "image_seq")
    @Column(name = "image_id")
    private long imageId;
    private long size;
    @Column(name = "image_url")
    private byte[] image;
}
