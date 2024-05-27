package com.example.myZeitplan;

import java.util.Date;

public class Note {
    private long id;
    private String title;
    private String content;
    private long timestamp;

    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id > 0) {
            this.id = id;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDateInMillis() {
        return timestamp;
    }
}
