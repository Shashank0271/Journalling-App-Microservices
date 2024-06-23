package com.journalmicroservice.JournalMicroService.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntry {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "journalEntry")
    @JsonManagedReference
    List<Image> images = new ArrayList<>();
    @Id
    @GeneratedValue(generator = "journal_entry_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "journal_entry_gen", sequenceName = "journal_entry_seq", allocationSize = 20)
    private int entryId;
    @Column(length = 1000)
    private String content;
    private String userName;
    private LocalDateTime createdAt;
}
