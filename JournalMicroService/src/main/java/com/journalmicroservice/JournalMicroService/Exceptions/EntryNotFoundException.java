package com.journalmicroservice.JournalMicroService.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryNotFoundException extends RuntimeException {
    private long entryId;
    private String message;

    public EntryNotFoundException(long entryId) {
        this.entryId = entryId;
        this.message = "Entry with Id = " + entryId + " not found";
    }
}
