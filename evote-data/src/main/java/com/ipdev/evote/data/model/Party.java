package com.ipdev.evote.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import java.util.Arrays;
@Entity
public class Party {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String leader;
    private String party;
    @Lob
    private byte [] img;
    private int orderId;
    @Version
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Party{" +
                "id=" + id +
                ", leader='" + leader + '\'' +
                ", party='" + party + '\'' +
                ", img=" + Arrays.toString(img) +
                ", order=" + orderId +
                ", version=" + version +
                '}';
    }
}
