package com.example.speechtotext;

import java.sql.Timestamp;

public class VoiceData {

    public String Label;
    public String Sentence;
    public Timestamp timestamp;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getSentence() {
        return Sentence;
    }

    public void setSentence(String sentence) {
        Sentence = sentence;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
