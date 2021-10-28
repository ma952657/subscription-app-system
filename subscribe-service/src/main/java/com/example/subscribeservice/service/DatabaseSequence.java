package com.example.subscribeservice.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Document(collection = "database_sequences")
@Service
public class DatabaseSequence {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Id
    private String id;


    private int seq;

    //getters and setters omitted
}
