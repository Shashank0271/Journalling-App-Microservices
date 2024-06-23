package com.journalmicroservice.JournalMicroService.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    JournalEntry journalEntry;
    @Id
    @GeneratedValue(generator = "image_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "image_seq_gen", sequenceName = "image_seq")
    @Column(name = "image_id")
    private long imageId;
    private long size; // in bytes
    @Lob
    @Column(name = "image")
    private byte[] image;
}
