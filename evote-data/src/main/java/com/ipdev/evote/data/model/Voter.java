package com.ipdev.evote.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Voter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String fiscalId;
    private boolean voted;
    @Version
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFiscalId() {
        return fiscalId;
    }

    public void setFiscalId(String fiscalId) {
        this.fiscalId = fiscalId;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "id=" + id +
                ", fiscalId='" + fiscalId + '\'' +
                ", voted=" + voted +
                ", version=" + version +
                '}';
    }
}
