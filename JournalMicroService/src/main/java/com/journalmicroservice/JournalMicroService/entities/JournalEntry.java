package com.journalmicroservice.JournalMicroService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntry {
    @Id
//    @GeneratedValue(generator = "journal_entry_gen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "journal_entry_gen", sequenceName = "journal_entry_seq", allocationSize = 20)
    private String entryId;
    @Column(length = 1000)
    private String content;
    private String userName;
    private int userId;
    private LocalDateTime createdAt;
    @Transient
    private List<Image> images;
}
