package com.journalmicroservice.JournalMicroService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntry {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "journalEntry")
    List<Image> images;
    @Id
    @GeneratedValue(generator = "journal_entry_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "journal_entry_gen", sequenceName = "journal_entry_seq", allocationSize = 20)
    private int entryId;
    @Column(length = 1000)
    private String content;
    private String userName;
}
