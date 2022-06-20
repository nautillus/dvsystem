package com.ipdev.evote.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Entity
public class Blank {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Party voted;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;
    @Version
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Party getVoted() {
        return voted;
    }

    public void setVoted(Party voted) {
        this.voted = voted;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Blank{" +
                "id=" + id +
                ", voted=" + voted +
                ", insert=" + timestamp +
                ", version=" + version +
                '}';
    }
}
